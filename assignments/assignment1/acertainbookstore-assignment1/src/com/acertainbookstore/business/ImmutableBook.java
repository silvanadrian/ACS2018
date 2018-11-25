package com.acertainbookstore.business;

import com.acertainbookstore.utils.BookStoreConstants;

/**
 * {@link ImmutableBook} is the object that gets sent back to the bookstore
 * client as a {@link Book}. We do not allow the bookstore client to make any
 * changes to this data structure.
 * 
 * @see Book
 */
public class ImmutableBook implements Book {

	/** The ISBN. */
	private final int isbn;

	/** The title. */
	private final String title;

	/** The author. */
	private final String author;

	/** The price. */
	private final float price;

	/**
	 * Instantiates a new {@link ImmutableBook}. This constructor is necessary
	 * for serialization and has no other purpose.
	 */
	public ImmutableBook() {
		this.isbn = 0;
		this.title = "";
		this.author = "";
		this.price = 0;
	}

	/**
	 * Instantiates a new {@link ImmutableBook}.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param title
	 *            the title
	 * @param author
	 *            the author
	 * @param price
	 *            the price
	 */
	public ImmutableBook(int isbn, String title, String author, float price) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.price = price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.Book#getISBN()
	 */
	public int getISBN() {
		return isbn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.Book#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.Book#getAuthor()
	 */
	public String getAuthor() {
		return author;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.Book#getPrice()
	 */
	public float getPrice() {
		return price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Book)) {
			return false;
		}

		Book book = (Book) obj;
		return this.isbn == book.getISBN() && this.getTitle().equals(book.getTitle())
				&& this.getAuthor().equals(book.getAuthor())
				&& Math.abs(this.getPrice() - book.getPrice()) < BookStoreConstants.EPSILON;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ISBN = " + this.getISBN() + " Title = " + this.getTitle() + " Author = " + this.getAuthor()
				+ " Price = " + this.getPrice();
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
