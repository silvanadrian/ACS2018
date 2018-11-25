package com.acertainbookstore.business;

/**
 * {@link BookCopy} is used to represent the book and its number of copies.
 */
public class BookCopy {

	/** The ISBN. */
	private int isbn;

	/** The number of copies. */
	private int numCopies;

	/**
	 * Instantiates a new {@link BookCopy} with <code>numCopies</code> book
	 * copies of <code>ISBN</code>.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param numCopies
	 *            the number of copies
	 */
	public BookCopy(int isbn, int numCopies) {
		this.setISBN(isbn);
		this.setNumCopies(numCopies);
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
	 * Gets the number of book copies.
	 *
	 * @return the number of copies
	 */
	public int getNumCopies() {
		return numCopies;
	}

	/**
	 * Sets the ISBN of the book.
	 *
	 * @param isbn
	 *            the new ISBN
	 */
	public void setISBN(int isbn) {
		this.isbn = isbn;
	}

	/**
	 * Sets the number of book copies.
	 *
	 * @param numCopies
	 *            the new number of copies
	 */
	public void setNumCopies(int numCopies) {
		this.numCopies = numCopies;
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

		return this.getISBN() == ((BookCopy) obj).getISBN();
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
