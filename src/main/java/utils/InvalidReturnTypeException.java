package utils;

/**
 * The Class InvalidReturnTypeException.
 */
public class InvalidReturnTypeException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4701542697704086960L;

	/**
	 * Instantiates a new invalid return type exception.
	 */
	public InvalidReturnTypeException() {
		super();
	}

	/**
	 * Instantiates a new invalid return type exception.
	 *
	 * @param s the s
	 */
	public InvalidReturnTypeException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new invalid return type exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public InvalidReturnTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new invalid return type exception.
	 *
	 * @param cause the cause
	 */
	public InvalidReturnTypeException(Throwable cause) {
		super(cause);
	}

}