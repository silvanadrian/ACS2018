package com.acertainbookstore.utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import com.acertainbookstore.client.BookStoreClientConstants;
import com.acertainbookstore.interfaces.BookStoreSerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * {@link BookStoreUtility} implements utility methods used by the bookstore
 * server and client.
 */
public final class BookStoreUtility {

	/** The Constant XmlStreams. */
	// We use pooling because creating an XStream object is expensive.
	public static final ThreadLocal<XStream> XML_STREAMS = new ThreadLocal<XStream>() {

		@Override
		protected XStream initialValue() {
			return new XStream(new StaxDriver());
		}
	};

	/**
	 * Prevents the instantiation of a new {@link BookStoreUtility}.
	 */
	private BookStoreUtility() {
		// Prevent instantiation.
	}

	/**
	 * Checks if is invalid ISBN.
	 *
	 * @param isbn
	 *            the ISBN
	 * @return true, if is invalid ISBN
	 */
	public static boolean isInvalidISBN(int isbn) {
		return isbn < 1;
	}

	/**
	 * Checks if is invalid rating.
	 *
	 * @param rating
	 *            the rating
	 * @return true, if is invalid rating
	 */
	public static boolean isInvalidRating(int rating) {
		return rating < 0 || rating > 5;
	}

	/**
	 * Checks if is invalid no copies.
	 *
	 * @param copies
	 *            the copies
	 * @return true, if is invalid no copies
	 */
	public static boolean isInvalidNoCopies(int copies) {
		return copies < 1;
	}

	/**
	 * Checks if a string is empty or null.
	 *
	 * @param str
	 *            the string
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * Converts a string to a float if possible else it returns the signal value
	 * for failure passed as parameter.
	 *
	 * @param str
	 *            the string
	 * @param failureSignal
	 *            the failure signal
	 * @return the float
	 */
	public static float convertStringToFloat(String str, float failureSignal) {
		float returnValue = failureSignal;

		try {
			returnValue = Float.parseFloat(str);

		} catch (NumberFormatException | NullPointerException ex) {
			System.err.println(ex.getStackTrace());
		}

		return returnValue;
	}

	/**
	 * Converts a string to an integer if possible else it returns the signal
	 * value for failure passed as parameter.
	 *
	 * @param str
	 *            the string
	 * @return the integer
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public static int convertStringToInt(String str) throws BookStoreException {
		int returnValue = 0;

		try {
			returnValue = Integer.parseInt(str);
		} catch (Exception ex) {
			throw new BookStoreException(ex);
		}

		return returnValue;
	}

	/**
	 * Convert a request URI to the message tags supported in CertainBookStore.
	 *
	 * @param requestURI
	 *            the request URI
	 * @return the book store message tag
	 */
	public static BookStoreMessageTag convertURItoMessageTag(String requestURI) {

		try {
			return BookStoreMessageTag.valueOf(requestURI.substring(1).toUpperCase());
		} catch (IllegalArgumentException | NullPointerException ex) {
			// Enumeration type matching failed so non supported message or the
			// request URI was empty.
			System.err.println(ex.getStackTrace());
		}

		return null;
	}

	/**
	 * Perform HTTP exchange.
	 *
	 * @param client
	 *            the client
	 * @param bookStoreRequest
	 *            the book store request
	 * @param serializer
	 *            the serializer
	 * @return the book store response
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public static BookStoreResponse performHttpExchange(HttpClient client, BookStoreRequest bookStoreRequest,
			BookStoreSerializer serializer) throws BookStoreException {
		Request request;

		switch (bookStoreRequest.getMethod()) {
		case GET:
			request = client.newRequest(bookStoreRequest.getURLString()).method(HttpMethod.GET);
			break;

		case POST:
			try {
				byte[] serializedValue = serializer.serialize(bookStoreRequest.getInputValue());
				ContentProvider contentProvider = new BytesContentProvider(serializedValue);
				request = client.POST(bookStoreRequest.getURLString()).content(contentProvider);
			} catch (IOException ex) {
				throw new BookStoreException("Serialization error", ex);
			}

			break;

		default:
			throw new IllegalArgumentException("HTTP Method not supported.");
		}

		ContentResponse response;

		try {
			response = request.send();
		} catch (InterruptedException ex) {
			throw new BookStoreException(BookStoreClientConstants.STR_ERR_CLIENT_REQUEST_SENDING, ex);
		} catch (TimeoutException ex) {
			throw new BookStoreException(BookStoreClientConstants.STR_ERR_CLIENT_REQUEST_TIMEOUT, ex);
		} catch (ExecutionException ex) {
			throw new BookStoreException(BookStoreClientConstants.STR_ERR_CLIENT_REQUEST_EXCEPTION, ex);
		}

		BookStoreResponse bookStoreResponse;

		try {
			bookStoreResponse = (BookStoreResponse) serializer.deserialize(response.getContent());
		} catch (IOException ex) {
			throw new BookStoreException("Deserialization error", ex);
		}

		BookStoreException exception = bookStoreResponse.getException();

		if (exception != null) {
			throw exception;
		}

		return bookStoreResponse;
	}
}
