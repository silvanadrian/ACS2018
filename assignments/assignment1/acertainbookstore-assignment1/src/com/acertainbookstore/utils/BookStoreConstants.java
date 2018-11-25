package com.acertainbookstore.utils;

/**
 * BookStoreConstants declares the constants used in the CertainBookStore (by
 * both servers and clients).
 */
public final class BookStoreConstants {

	/**
	 * The Constant BINARY_SERIALIZATION decides whether we use Kryo or XStream.
	 */
	public static final boolean BINARY_SERIALIZATION = false;

	// Constants used when creating URLs

	/** The Constant BOOKISBN_PARAMs. */
	public static final String BOOKISBN_PARAM = "ISBN";

	/** The Constant BOOK_NUM_PARAM. */
	public static final String BOOK_NUM_PARAM = "number_of_books";

	/** The Constant XMLSTRINGLEN_PARAM. */
	public static final String XMLSTRINGLEN_PARAM = "len";

	/**
	 * The Constant INVALID_PARAMS used as error code when converting numbers to
	 * integer.
	 */
	public static final int INVALID_PARAMS = -1;

	// Constants used when creating exception messages

	/** The Constant INVALID when the book has an invalid ISBN. */
	public static final String INVALID = " is invalid";

	/** The Constant DUPLICATED when the book is already in the store. */
	public static final String DUPLICATED = " is duplicated";

	/** The Constant NOT_AVAILABLE when the book is not in the store. */
	public static final String NOT_AVAILABLE = " is not available";

	/** The Constant BOOK. */
	public static final String BOOK = "The Book: ";

	/** The Constant ISBN. */
	public static final String ISBN = "The ISBN: ";

	/** The Constant NUM_COPIES. */
	public static final String NUM_COPIES = "The Number of copies: ";

	/** The Constant RATING. */
	public static final String RATING = "The rating: ";

	/** The Constant NULL_INPUT. */
	public static final String NULL_INPUT = "null input parameters";

	/** The Constant PROPERTY_KEY_LOCAL_TEST. */
	public static final String PROPERTY_KEY_LOCAL_TEST = "localtest";

	/** The Constant PROPERTY_KEY_SERVER_PORT. */
	public static final String PROPERTY_KEY_SERVER_PORT = "port";

	/** The Constant EPSILON used for floating point number comparison */
	public static final float EPSILON = 0.000001F;

	/**
	 * Prevents the instantiation of a new {@link BookStoreConstants}.
	 */
	private BookStoreConstants() {
		// Prevent instantiation.
	}
}
