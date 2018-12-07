package com.acertainbookstore.business;

/**
 * {@link BookRating} is used to represent the book with its rating.
 */
public class BookRating {

	/** The ISBN. */
	private int isbn;

	/** The rating. */
	private int rating;

	/**
	 * Instantiates a new {@link BookRating} representing the
	 * <code>rating</code> of the book with the given <code>ISBN</code>.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param rating
	 *            the rating
	 */
	public BookRating(int isbn, int rating) {
		this.setISBN(isbn);
		this.setRating(rating);
	}

	/**
	 * Gets the ISBN of the book.
	 *
	 * @return the ISBN
	 */
	public int getISBN() {
		return isbn;
	}

	/**
	 * Sets the ISBN of the book.
	 *
	 * @param iSBN
	 *            the new ISBN
	 */
	public void setISBN(int isbn) {
		this.isbn = isbn;
	}

	/**
	 * Gets the rating of the book.
	 *
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating of the book.
	 *
	 * @param rating
	 *            the new rating
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return this.getISBN() == ((BookRating) obj).getISBN();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getISBN();
	}
}
