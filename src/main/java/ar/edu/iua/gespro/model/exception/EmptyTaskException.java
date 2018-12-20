package ar.edu.iua.gespro.model.exception;

public class EmptyTaskException extends Exception{
	public EmptyTaskException() {
		super();
	}

	public EmptyTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmptyTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyTaskException(String message) {
		super(message);
	}

	public EmptyTaskException(Throwable cause) {
		super(cause);
	}
}
