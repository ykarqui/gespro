package ar.edu.iua.gespro.model.exception;

public class TaskNameAlreadyExist extends Exception{
	public TaskNameAlreadyExist() {
		super();
	}

	public TaskNameAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TaskNameAlreadyExist(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskNameAlreadyExist(String message) {
		super(message);
	}

	public TaskNameAlreadyExist(Throwable cause) {
		super(cause);
	}
}
