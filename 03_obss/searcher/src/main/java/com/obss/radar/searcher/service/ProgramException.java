package com.obss.radar.searcher.service;

public class ProgramException extends RuntimeException {
	private static final long serialVersionUID = 1913263321312485172L;

	/**
	 * @param cause
	 */
	public ProgramException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

	/**
	 * @param message
	 */
	public ProgramException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ProgramException(String message, Throwable cause) {
		super(message, cause);
	}

}
