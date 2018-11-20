package com.acertainbookstore.client;

/**
 * {@link BookStoreClientConstants} stores the constants used by the
 * {@link CertainBookStore} proxy and client classes.
 * 
 * @see CertainBookStore
 */
public final class BookStoreClientConstants {
	
	/** The Constant CLIENT_MAX_CONNECTION_ADDRESS. */
	public static final int CLIENT_MAX_CONNECTION_ADDRESS = 200;

	/** The Constant CLIENT_MAX_THREADSPOOL_THREADS. */
	public static final int CLIENT_MAX_THREADSPOOL_THREADS = 250;

	/** The Constant CLIENT_MAX_TIMEOUT_MILLISECS. */
	public static final int CLIENT_MAX_TIMEOUT_MILLISECS = 30000;

	/** The Constant strERR_CLIENT_REQUEST_SENDING. */
	public static final String STR_ERR_CLIENT_REQUEST_SENDING = "ERR_CLIENT_REQUEST_SENDING";

	/** The Constant strERR_CLIENT_REQUEST_EXCEPTION. */
	public static final String STR_ERR_CLIENT_REQUEST_EXCEPTION = "ERR_CLIENT_REQUEST_EXCEPTION";

	/** The Constant strERR_CLIENT_REQUEST_TIMEOUT. */
	public static final String STR_ERR_CLIENT_REQUEST_TIMEOUT = "CLIENT_REQUEST_TIMEOUT";

	/** The Constant strERR_CLIENT_RESPONSE_DECODING. */
	public static final String STR_ERR_CLIENT_RESPONSE_DECODING = "CLIENT_RESPONSE_DECODING";

	/** The Constant strERR_CLIENT_UNKNOWN. */
	public static final String STR_ERR_CLIENT_UNKNOWN = "CLIENT_UNKNOWN";

	/** The Constant strERR_CLIENT_ENCODING. */
	public static final String STR_ERR_CLIENT_ENCODING = "CLIENT_ENCODING";

	/**
	 * Prevents the instantiation of a new {@link BookStoreClientConstants}.
	 */
	private BookStoreClientConstants() {
		// Added a private constructor to hide the default one.
	}
}
