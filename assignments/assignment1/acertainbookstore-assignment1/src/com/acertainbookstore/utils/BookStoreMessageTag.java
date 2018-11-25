package com.acertainbookstore.utils;

/**
 * {@link BookStoreMessageTag} implements the messages supported in the
 * bookstore.
 */
public enum BookStoreMessageTag {

	/** The tag for the add book message. */
	ADDBOOKS,

	/** The tag for the list books message. */
	LISTBOOKS,

	/** The tag for the add copies message. */
	ADDCOPIES,

	/** The tag for the get books message. */
	GETBOOKS,

	/** The tag for the buy books message. */
	BUYBOOKS,

	/** The tag for the update editor picks message. */
	UPDATEEDITORPICKS,

	/** The tag for the editor picks message. */
	GETEDITORPICKS,

	/** The tag for the remove all books message. */
	REMOVEALLBOOKS,

	/** The tag for the remove books message. */
	REMOVEBOOKS,

	/** The tag for the get stock books by ISBN message. */
	GETSTOCKBOOKSBYISBN;
}
