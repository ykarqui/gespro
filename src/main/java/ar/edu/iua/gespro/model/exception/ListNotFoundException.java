package ar.edu.iua.gespro.model.exception;

public class ListNotFoundException extends Exception{
	public ListNotFoundException() {
		super();
	}

	public ListNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ListNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ListNotFoundException(String message) {
		super(message);
	}

	public ListNotFoundException(Throwable cause) {
		super(cause);
	}
}
