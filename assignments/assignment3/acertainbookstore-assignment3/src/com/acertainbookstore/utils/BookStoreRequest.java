package com.acertainbookstore.utils;

import org.eclipse.jetty.http.HttpMethod;

/**
 * {@link BookStoreRequest} is the data structure that encapsulates a HTTP
 * request from the bookstore client to the server.
 */
public final class BookStoreRequest {

	/** The method. */
	private final HttpMethod method;

	/** The URL string. */
	private final String urlString;

	/** The input value. */
	private final Object inputValue;

	/**
	 * Instantiates a new {@link BookStoreRequest}.
	 *
	 * @param method
	 *            the method
	 * @param urlString
	 *            the URL string
	 * @param inputValue
	 *            the input value
	 */
	private BookStoreRequest(HttpMethod method, String urlString, Object inputValue) {
		this.method = method;
		this.urlString = urlString;
		this.inputValue = inputValue;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public HttpMethod getMethod() {
		return method;
	}

	/**
	 * Gets the URL string.
	 *
	 * @return the URL string
	 */
	public String getURLString() {
		return urlString;
	}

	/**
	 * Gets the input value.
	 *
	 * @return the input value
	 */
	public Object getInputValue() {
		return inputValue;
	}

	/**
	 * Gets a new GET request.
	 *
	 * @param urlString
	 *            the URL string
	 * @return the book store request
	 */
	public static BookStoreRequest newGetRequest(String urlString) {
		return new BookStoreRequest(HttpMethod.GET, urlString, null);
	}

	/**
	 * Gets a new POST request.
	 *
	 * @param urlString
	 *            the URL string
	 * @param inputValue
	 *            the input value
	 * @return the book store request
	 */
	public static BookStoreRequest newPostRequest(String urlString, Object inputValue) {
		return new BookStoreRequest(HttpMethod.POST, urlString, inputValue);
	}
}
