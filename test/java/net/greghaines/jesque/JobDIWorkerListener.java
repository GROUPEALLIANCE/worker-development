/*
 * Copyright 2011 Greg Haines
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.greghaines.jesque;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.greghaines.jesque.utils.ReflectionUtils;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This WorkerListener injects dependencies into realized jobs before they are executed by a Worker.
 * Dependencies are identified as setters marked with the @Resource annotation.
 * <p/>
 * This allows for Worker-specific objects to be used by a job.
 * 
 * @author Greg Haines
 * @see javax.annotation.Resource
 */
public class JobDIWorkerListener implements WorkerListener
{
	private static final Logger log = LoggerFactory.getLogger(JobDIWorkerListener.class);
	
	private final Map<String,Object> diMap = new HashMap<String,Object>();
	
	public void put(final String propName, final Object arg)
	{
		if (propName == null || "".equals(propName))
		{
			throw new IllegalArgumentException("propName must not be null or empty: " + propName);
		}
		if (arg == null)
		{
			throw new IllegalArgumentException("arg must not be null");
		}
		this.diMap.put(propName, arg);
	}
	
	public void onEvent(final WorkerEvent event, final Worker worker, 
			final String queue, final Job job, final Object runner, 
			final Object result, final Exception ex)
	{
		if (WorkerEvent.JOB_EXECUTE.equals(event))
		{
			final Map<String,Method> setterMap = getResourceSetters(runner.getClass());
			for (final Entry<String,Method> e : setterMap.entrySet())
			{
				invokeSetter(runner, e.getKey(), e.getValue());
			}
		}
	}

	private void invokeSetter(final Object runner, final String propName, final Method method)
	{
		final Object arg = this.diMap.get(propName);
		final Class<?> paramType = method.getParameterTypes()[0]; // OK, since we already make sure there is 1 param
		if (ReflectionUtils.isAssignableValue(paramType, arg))
		{
			try
			{
				method.invoke(runner, arg);
			}
			catch (Exception mex)
			{
				log.error("Error injecting " + arg + " (" + arg.getClass().getName() + ") as resource at " + 
					runner.getClass().getName() + "." + propName + " (" + paramType.getName() + ")", mex);
			}
		}
		else
		{
			log.warn("Unable to inject {} ({}) as resource at {}.{} ({}) because types are not assignable", 
				new Object[]{arg, arg.getClass().getName(), runner.getClass().getName(), propName, paramType.getName()});
		}
	}

	private Map<String,Method> getResourceSetters(final Class<?> clazz)
	{
		final Map<String,Method> setterMap = new HashMap<String,Method>();
		final Method[] allMethods = clazz.getMethods();
		for (final Method method : allMethods)
		{
			if (method.getName().startsWith("set") && // Method is a setter 
					void.class.equals(method.getReturnType()) && // No return value
					method.getName().length() > 3 && // Makes sure the method isn't just "set()"
					method.getAnnotation(Resource.class) != null && // Method is annotated as a resource
					method.getParameterTypes().length == 1) // Method accepts 1 argument
			{
				final String noSet = method.getName().substring(3); // Remove "set" prefix
				final String propName = noSet.substring(0, 1).toLowerCase() + noSet.substring(1); // Decapitalize first letter
				if (this.diMap.containsKey(propName))
				{ // Only keep methods we have objects for
					setterMap.put(propName, method);
				}
			}
		}
		return setterMap;
	}
}
