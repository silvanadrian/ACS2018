package com.acertainbookstore.business;

import com.acertainbookstore.utils.BookStoreUtility;

/**
 * {@link BookStoreBook} implements all parts of the book. Only parts of it are
 * available in the bookstore client and stock manager, cf. the {@link Book} and
 * {@link StockBook} interfaces.
 * 
 * @see Book
 * @see StockBook
 * @see ImmutableBook
 */
public class BookStoreBook extends ImmutableBook {

	/** The number of copies. */
	private int numCopies;

	/** The total rating. */
	private long totalRating;

	/** The number of times rated. */
	private long numTimesRated;

	/** The number of sale misses. */
	private long numSaleMisses;

	/** Whether the book is editor picked. */
	private boolean editorPick;

	/**
	 * Instantiates a new {@link BookStoreBook}.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param title
	 *            the title
	 * @param author
	 *            the author
	 * @param price
	 *            the price
	 * @param numCopies
	 *            the number of copies
	 */
	public BookStoreBook(int isbn, String title, String author, float price, int numCopies) {
		super(isbn, title, author, price);

		this.setNumSaleMisses(0);
		this.setNumTimesRated(0);
		this.setNumCopies(numCopies);
		this.setTotalRating(0);
		this.setEditorPick(false);
	}

	/**
	 * Instantiates a new {@link BookStoreBook} from a {@link StockBook}
	 * instance.
	 *
	 * @param bookToCopy
	 *            the book to copy
	 */
	public BookStoreBook(StockBook bookToCopy) {
		super(bookToCopy.getISBN(), bookToCopy.getTitle(), bookToCopy.getAuthor(), bookToCopy.getPrice());

		this.setNumSaleMisses(bookToCopy.getNumSaleMisses());
		this.setNumTimesRated(bookToCopy.getNumTimesRated());
		this.setNumCopies(bookToCopy.getNumCopies());
		this.setTotalRating(bookToCopy.getTotalRating());
		this.setEditorPick(bookToCopy.isEditorPick());
	}

	/**
	 * Gets the total rating.
	 *
	 * @return the total rating
	 */
	public long getTotalRating() {
		return totalRating;
	}

	/**
	 * Gets the number of times rated.
	 *
	 * @return the number of times rated
	 */
	public long getNumTimesRated() {
		return numTimesRated;
	}

	/**
	 * Gets the number of copies.
	 *
	 * @return the number of copies
	 */
	public int getNumCopies() {
		return numCopies;
	}

	/**
	 * Gets the number of sale misses.
	 *
	 * @return the number of sale misses
	 */
	public long getNumSaleMisses() {
		return numSaleMisses;
	}

	/**
	 * Gets the average rating.
	 *
	 * @return the average rating
	 */
	public float getAverageRating() {
		return (numTimesRated == 0 ? -1.0f : (float) totalRating / numTimesRated);
	}

	/**
	 * Checks if the book is editor picked.
	 *
	 * @return true, if it is editor picked
	 */
	public boolean isEditorPick() {
		return editorPick;
	}

	/**
	 * Sets the total rating of the book.
	 *
	 * @param totalRating
	 *            the new total rating
	 */
	private void setTotalRating(long totalRating) {
		this.totalRating = totalRating;
	}

	/**
	 * Sets the number of times that the book was rated.
	 *
	 * @param numTimesRated
	 *            the new number of times rated
	 */
	private void setNumTimesRated(long numTimesRated) {
		this.numTimesRated = numTimesRated;
	}

	/**
	 * Sets the number of copies of a book in stock.
	 *
	 * @param numCopies
	 *            the new number of copies
	 */
	private void setNumCopies(int numCopies) {
		this.numCopies = numCopies;
	}

	/**
	 * Sets the number of times that a client wanted to buy a book when it was
	 * not in stock, also known as the number of sale misses.
	 *
	 * @param saleMisses
	 *            the new sale misses
	 */
	private void setNumSaleMisses(long numSaleMisses) {
		this.numSaleMisses = numSaleMisses;
	}

	/**
	 * Sets the book to be an editor pick if <code>editorPick</code> is true,
	 * otherwise the book is not an editor pick.
	 *
	 * @param editorPick
	 *            the new editor pick
	 */
	public void setEditorPick(boolean editorPick) {
		this.editorPick = editorPick;
	}

	/**
	 * Checks if at least <code>numCopies</code> of the book are available.
	 *
	 * @param numCopies
	 *            the number of copies
	 * @return true, if successful
	 */
	public boolean areCopiesInStore(int numCopies) {
		return this.numCopies >= numCopies;
	}

	/**
	 * Reduces the number of copies of the books.
	 *
	 * @param numCopies
	 *            the number of copies
	 * @return true, if successful
	 */
	public boolean buyCopies(int numCopies) {
		if (!BookStoreUtility.isInvalidNoCopies(numCopies) && areCopiesInStore(numCopies)) {
			this.numCopies -= numCopies;
			return true;
		}

		return false;
	}

	/**
	 * Adds <code>newCopies</code> to the total number of copies of the book.
	 *
	 * @param numNewCopies
	 *            the number of new copies
	 */
	public void addCopies(int numNewCopies) {
		if (!BookStoreUtility.isInvalidNoCopies(numNewCopies)) {
			this.numCopies += numNewCopies;
			this.numSaleMisses = 0;
		}
	}

	/**
	 * Increments the amount of missed sales of the book.
	 * 
	 * @param numSaleMisses
	 *            the number of sales misses encountered
	 */
	public void addSaleMiss(int numSaleMisses) {
		this.numSaleMisses += numSaleMisses;
	}

	/**
	 * Adds the rating to the total rating of the book.
	 *
	 * @param rating
	 *            the rating
	 */
	public void addRating(int rating) {
		if (!BookStoreUtility.isInvalidRating(rating)) {
			this.totalRating += rating;
			this.numTimesRated++;
		}
	}

	/**
	 * Checks if someone tried to buy the book, while the book was not in stock,
	 * also known as having sale misses.
	 *
	 * @return true, if successful
	 */
	public boolean hadSaleMiss() {
		return this.numSaleMisses > 0;
	}

	/**
	 * Returns a string representation of the book.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ISBN = " + this.getISBN() + ", Title = " + this.getTitle() + ", Author = " + this.getAuthor()
				+ ", Price = " + this.getPrice();
	}

	/**
	 * Returns a {@link ImmutableBook} copy of the book.
	 *
	 * @return the immutable book
	 */
	public ImmutableBook immutableBook() {
		return new ImmutableBook(this.getISBN(), new String(this.getTitle()), new String(this.getAuthor()),
				this.getPrice());
	}

	/**
	 * Returns a {@link StockBook} copy of the book.
	 *
	 * @return the stock book
	 */
	public StockBook immutableStockBook() {
		return new ImmutableStockBook(this.getISBN(), new String(this.getTitle()), new String(this.getAuthor()),
				this.getPrice(), this.numCopies, this.numSaleMisses, this.numTimesRated, this.totalRating,
				this.editorPick);
	}

	/**
	 * Returns a {@link BookStoreBook} copy of the book.
	 *
	 * @return the book store book
	 */
	public BookStoreBook copy() {
		return new BookStoreBook(this.getISBN(), new String(this.getTitle()), new String(this.getAuthor()),
				this.getPrice(), this.numCopies);
	}
}
