// Modified or written by Luca Marrocco for inclusion with airbrake.
// Copyright (c) 2009 Luca Marrocco.
// Licensed under the Apache License, Version 2.0 (the "License")

package com.itelis.worker.dev.airbrake;

public class SwitchBacktrace extends Backtrace {

	private static final QuietRubyBacktrace QuietRubyBacktrace = new QuietRubyBacktrace();

	private static final Backtrace Backtrace = new Backtrace();

	private Backtrace backtrace = Backtrace;

	@Override
	public com.itelis.worker.dev.airbrake.Backtrace newBacktrace(final Throwable throwable) {
		return backtrace.newBacktrace(throwable);
	}

	public void quiet() {
		backtrace = QuietRubyBacktrace;
	}

	public void verbose() {
		backtrace = Backtrace;
	}

}
