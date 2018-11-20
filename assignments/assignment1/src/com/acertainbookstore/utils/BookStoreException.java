package com.acertainbookstore.utils;

/**
 * {@link BookStoreException} signals a book store error.
 */
public class BookStoreException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new {@link BookStoreException}.
	 */
	public BookStoreException() {
		super();
	}

	/**
	 * Instantiates a new {@link BookStoreException}.
	 *
	 * @param message
	 *            the message
	 */
	public BookStoreException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new {@link BookStoreException}.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public BookStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new {@link BookStoreException}.
	 *
	 * @param ex
	 *            the exception
	 */
	public BookStoreException(Throwable ex) {
		super(ex);
	}
}
