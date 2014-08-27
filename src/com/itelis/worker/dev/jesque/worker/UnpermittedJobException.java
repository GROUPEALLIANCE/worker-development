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
package com.itelis.worker.dev.jesque.worker;

/**
 * Thrown by a Worker when it receives a Job that it is not allowed to run.
 * 
 * @author Greg Haines
 */
public class UnpermittedJobException extends Exception
{
	private static final long serialVersionUID = -5360734802682205116L;
	
	private final Class<?> type;

	public UnpermittedJobException(final Class<?> type)
	{
		super(type.getName());
		this.type = type;
	}

	/**
	 * @return the type of Job that was not permitted
	 */
	public Class<?> getType()
	{
		return this.type;
	}
}
