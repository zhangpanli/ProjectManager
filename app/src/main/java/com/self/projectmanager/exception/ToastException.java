package com.self.projectmanager.exception;

public class ToastException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ToastException() {
		super();
	}

	public ToastException(String message, Throwable cause) {
		super(message, cause);
	}

	public ToastException(String message) {
		super(message);
	}

	public ToastException(Throwable cause) {
		super(cause);
	}

}
