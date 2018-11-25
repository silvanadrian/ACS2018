package com.acertainbookstore.utils;

import java.util.List;

import com.acertainbookstore.business.Book;

/**
 * {@link BookStoreResponse} is the data structure that encapsulates a HTTP
 * response from the bookstore server to the client. The data structure contains
 * error messages from the server if an error occurred.
 */
public class BookStoreResponse {

	/** The exception. */
	private BookStoreException exception;

	/** The list. */
	private List<?> list;

	/**
	 * Instantiates a new {@link BookStoreResponse}.
	 *
	 * @param exception
	 *            the exception
	 * @param list
	 *            the list
	 */
	public BookStoreResponse(BookStoreException exception, List<Book> list) {
		this.setException(exception);
		this.setList(list);
	}

	/**
	 * Instantiates a new book store response.
	 */
	public BookStoreResponse() {
		this.setException(null);
		this.setList(null);
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list
	 *            the new list
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public BookStoreException getException() {
		return exception;
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception
	 *            the new exception
	 */
	public void setException(BookStoreException exception) {
		this.exception = exception;
	}
}
