// Modified or written by Luca Marrocco for inclusion with airbrake.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package com.itelis.worker.dev.airbrake;

import static java.text.MessageFormat.*;

public class BacktraceLine {

	private final String className;

	private final String fileName;

	private final int lineNumber;

	private final String methodName;

	public BacktraceLine(String line) {
		String classAndMethodName = line.replaceAll("\\(.*", "").replaceAll("^at ", "");

		int periodSepIndex = classAndMethodName.lastIndexOf(".");
		if (periodSepIndex > 0) {
			className = classAndMethodName.substring(0, classAndMethodName.lastIndexOf("."));
		} else {
			className = classAndMethodName;
		}
		fileName = fileName(line);
		lineNumber = lineNumber(line);
		methodName = methodName(classAndMethodName);
	}

	private String fileName(String line) {
		return NoticeXml.escapeXml(line.replaceAll("^.*\\(", "").replaceAll(":.*", ""));
	}

	private String methodName(String classAndMethodName) {
		return classAndMethodName.substring(classAndMethodName.lastIndexOf(".") + 1);
	}

	public BacktraceLine(String className, String fileName, int lineNumber, String methodName) {
		this.className = className;
		this.fileName = fileName;
		this.lineNumber = lineNumber;
		this.methodName = methodName;
	}

	String className() {
		return className;
	}

	String fileName() {
		return fileName;
	}

	int lineNumber() {
		return lineNumber;
	}

	private int lineNumber(String line) {
		try {
			return Integer.parseInt(line.replaceAll("^.*:", "").replaceAll("\\)", "").replaceAll(":.*", ""));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public String methodName() {
		return methodName;
	}

	private String toBacktrace(final String className, final String fileName, final int lineNumber, final String methodName) {
		return format("at {0}.{1}({2}:{3})", className, methodName, fileName, lineNumber);
	}

	@Override
	public String toString() {
		return toBacktrace(className, fileName, lineNumber, methodName);
	}

	public String toXml() {
		return format("<line method=\"{0}.{1}\" file=\"{2}\" number=\"{3}\"/>", className, methodName, fileName, lineNumber);
	}
}