package com.acertainbookstore.business;

/**
 * {@link StockBook} implements the {@link Book} data-structure that a
 * {@link StockManager} works with.
 * 
 * @see Book
 * @see StockBook
 * @see StockManager
 */
public interface StockBook extends Book {

	/**
	 * Returns to accumulated rating.
	 *
	 * @return the total rating
	 */
	public long getTotalRating();

	/**
	 * Gets the number of times a book has been rated.
	 *
	 * @return the times rated
	 */
	public long getNumTimesRated();

	/**
	 * Gets the number of book copies.
	 *
	 * @return the number of copies
	 */
	public int getNumCopies();

	/**
	 * Gets the number of times that a client wanted to buy a book when it was
	 * not in stock.
	 *
	 * @return the sale misses
	 */
	public long getNumSaleMisses();

	/**
	 * Gets the rating of the book.
	 *
	 * @return the average rating
	 */
	public float getAverageRating();

	/**
	 * Checks if the book is editor picked.
	 *
	 * @return true, if it is editor picked
	 */
	public boolean isEditorPick();
}
