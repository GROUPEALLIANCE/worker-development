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
package com.itelis.worker.dev.jesque.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Miscellaneous utilities.
 * 
 * @author Greg Haines
 */
public final class JesqueUtils
{
	public static final String DATE_FORMAT = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)";
	private static final String bTracePrefix = "\tat ";
	private static final String btCausedByPrefix = "Caused by: ";
	private static final String btUnknownSource = "Unknown Source";
	private static final String btNativeMethod = "Native Method";
	private static final Pattern btPattern = Pattern.compile("[\\(\\):]");
	private static final Pattern colonSpacePattern = Pattern.compile(":\\s");
	
	/**
	 * Join the given strings, separated by the given separator.
	 * 
	 * @param sep the separator
	 * @param strs the strings to join
	 * @return the joined string
	 */
	public static String join(final String sep, final String... strs)
	{
		final StringBuilder sb = new StringBuilder();
		String s = "";
		for (final String str : strs)
		{
			sb.append(s).append(str);
			s = sep;
		}
		return sb.toString();
	}
	
	/**
	 * Join the given strings, separated by the given separator.
	 * 
	 * @param sep the separator
	 * @param strs the strings to join
	 * @return the joined string
	 */
	public static String join(final String sep, final Collection<String> strs)
	{
		final StringBuilder sb = new StringBuilder();
		String s = "";
		for (final String str : strs)
		{
			sb.append(s).append(str);
			s = sep;
		}
		return sb.toString();
	}
	
	/**
	 * Builds a namespaced Redis key with the given arguments.
	 * 
	 * @param namespace the namespace to use
	 * @param parts the key parts to be joined
	 * @return an assembled String key
	 */
	public static String createKey(final String namespace, final String... parts)
	{
		final List<String> list = new ArrayList<String>(parts.length + 1);
		list.add(namespace);
		list.addAll(Arrays.asList(parts));
		return join(":", list);
	}
	
	/**
	 * @param ex the Exception to use
	 * @return a list of strings that represent how the exception's stacktrace appears.
	 */
	public static List<String> createBacktrace(final Throwable ex)
	{
		final List<String> bTrace = new LinkedList<String>();
		for (final StackTraceElement ste : ex.getStackTrace())
		{
			bTrace.add(bTracePrefix + ste.toString());
		}
		if (ex.getCause() != null)
		{
			addCauseToBacktrace(ex.getCause(), bTrace);
		}
		return bTrace;
	}

	private static void addCauseToBacktrace(final Throwable ex, final List<String> bTrace)
	{
		if (ex.getMessage() != null)
		{
			bTrace.add(btCausedByPrefix + ex.getClass().getName() + ": " + ex.getMessage());
		}
		else
		{
			bTrace.add(btCausedByPrefix + ex.getClass().getName());
		}
		for (final StackTraceElement ste : ex.getStackTrace())
		{
			bTrace.add(bTracePrefix + ste.toString());
		}
		if (ex.getCause() != null)
		{
			addCauseToBacktrace(ex.getCause(), bTrace);
		}
	}
	
	/**
	 * Recreate an exception from a type name, a message and a backtrace 
	 * (created from <code>JesqueUtils.createBacktrace(Throwable)</code>).
	 * 
	 * @param type the String name of the Throwable type
	 * @param message the message of the exception
	 * @param backtrace the backtrace of the exception
	 * @return a new Throwable of the given type with the given message and given backtrace/causes
	 * @throws ParseException if there is a problem parsing the given backtrace
	 * @throws ClassNotFoundException if the given type is not available
	 * @throws NoSuchConstructorException if there is not a common constructor available for the given type
	 * @throws AmbiguousConstructorException if there is more than one constructor that is viable
	 * @throws InstantiationException if there is a problem instantiating the given type
	 * @throws IllegalAccessException if the common constructor is not visible
	 * @throws InvocationTargetException if the constructor threw an exception
	 * @throws ClassCastException is the given type is not a Throwable
	 * @see JesqueUtils#createBacktrace(Throwable)
	 */
	public static Throwable recreateException(final String type, final String message, 
			final List<String> backtrace)
	throws ParseException, ClassNotFoundException, NoSuchConstructorException, 
			AmbiguousConstructorException, InstantiationException, 
			IllegalAccessException, InvocationTargetException, ClassCastException
	{
		Throwable t = null;
		final LinkedList<String> bTrace = new LinkedList<String>(backtrace);
		Throwable cause = null;
		StackTraceElement[] stes = null;
		while (!bTrace.isEmpty())
		{
			stes = recreateStackTrace(bTrace);
			if (!bTrace.isEmpty())
			{
				final String line = bTrace.removeLast().substring(btCausedByPrefix.length());
				final String[] classNameAndMsg = colonSpacePattern.split(line, 2);
				final Class<?> throwableType = ReflectionUtils.forName(classNameAndMsg[0]);
				final Throwable newEx = (classNameAndMsg.length == 2 && classNameAndMsg[1] != null)
					? (Throwable) ReflectionUtils.createObject(throwableType, classNameAndMsg[1]) 
					: (Throwable) ReflectionUtils.createObject(throwableType);
				newEx.setStackTrace(stes);
				if (cause != null)
				{
					newEx.initCause(cause);
				}
				cause = newEx;
			}
		}
		final Class<?> throwableType = ReflectionUtils.forName(type);
		if (message != null)
		{
			t = (Throwable) ReflectionUtils.createObject(throwableType, message);
		}
		else
		{
			try
			{
				t = (Throwable) ReflectionUtils.createObject(throwableType);
			}
			catch (NoSuchConstructorException nsce)
			{
				t = (Throwable) ReflectionUtils.createObject(throwableType, (String) null);
			}
		}
		t.setStackTrace(stes);
		if (cause != null)
		{
			t.initCause(cause);
		}
		return t;
	}
	
	private static StackTraceElement[] recreateStackTrace(final List<String> bTrace)
	throws ParseException
	{
		final List<StackTraceElement> stes = new LinkedList<StackTraceElement>();
		final ListIterator<String> iter = bTrace.listIterator(bTrace.size());
		while (iter.hasPrevious())
		{
			final String prev = iter.previous();
			if (prev.startsWith(bTracePrefix))
			{ // All stack trace elements start with bTracePrefix
				iter.remove();
				final String[] stParts = btPattern.split(prev.substring(bTracePrefix.length()));
				if (stParts.length < 2 || stParts.length > 3)
				{
					throw new ParseException("Malformed stack trace element string: " + prev, 0);
				}
				final int periodPos = stParts[0].lastIndexOf('.');
				final String className = stParts[0].substring(0, periodPos);
				final String methodName = stParts[0].substring(periodPos + 1);
				final String fileName;
				final int lineNumber;
				if (btUnknownSource.equals(stParts[1]))
				{
					fileName = null;
					lineNumber = -1;
				}
				else if (btNativeMethod.equals(stParts[1]))
				{
					fileName = null;
					lineNumber = -2;
				}
				else
				{
					fileName = stParts[1];
					lineNumber = (stParts.length == 3) ? Integer.parseInt(stParts[2]) : -1;
				}
				stes.add(0, new StackTraceElement(className, methodName, fileName, lineNumber));
			}
			else
			{ // Stop if it is not a stack trace element
				break;
			}
		}
		return stes.toArray(new StackTraceElement[stes.size()]);
	}
	
	/**
	 * This is needed because Throwable doesn't override equals() 
	 * and object equality is not what we want to test here.
	 * 
	 * @param ex original Throwable
	 * @param newEx other Throwable
	 * @return true if the two arguments are equal, as we define it.
	 */
	public static boolean equal(final Throwable ex, final Throwable newEx)
	{
		if (ex == newEx)
		{
			return true;
		}
		if (ex == null)
		{
			if (newEx != null)
			{
				return false;
			}
		}
		else
		{
			if (ex.getClass() != newEx.getClass())
			{
				return false;
			}
			if (ex.getMessage() == null)
			{
				if (newEx.getMessage() != null)
				{
					return false;
				}
			}
			else if (!ex.getMessage().equals(newEx.getMessage()))
			{
				return false;
			}
			if (ex.getCause() == null)
			{
				if (newEx.getCause() != null)
				{
					return false;
				}
			}
			else if (!equal(ex.getCause(), newEx.getCause()))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Sleep the current thread, ignoring any Exception that might occur.
	 * 
	 * @param millis the time in milliseconds to sleep for
	 */
	public static void sleepTight(final long millis)
	{
		try { Thread.sleep(millis); } catch (Exception e){} // Ignore
	}

	private JesqueUtils(){} // Utility class
}
