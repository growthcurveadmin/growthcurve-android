package com.borstvoeding.growthcurve;

/**
 * @author Xxx
 * 
 */
public class DataNotLoadedException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * C'tor.
	 * 
	 * @param msg
	 *            the message
	 * @param cause
	 *            the cause of the problem
	 */
	public DataNotLoadedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
