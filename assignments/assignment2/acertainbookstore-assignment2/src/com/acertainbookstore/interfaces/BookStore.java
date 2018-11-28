package com.acertainbookstore.interfaces;

import java.util.List;
import java.util.Set;

import com.acertainbookstore.business.Book;
import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.BookRating;
import com.acertainbookstore.utils.BookStoreException;

/**
 * {@link BookStore} declares the methods exposed by the bookstore to the
 * clients. These methods need to be implemented by both server and client ends.
 */
public interface BookStore {

	/**
	 * Buys the sets of books specified.
	 *
	 * @param booksToBuy
	 *            the books to buy
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void buyBooks(Set<BookCopy> booksToBuy) throws BookStoreException;

	/**
	 * Applies the BookRatings in the set, i.e. rates each book with their
	 * respective rating.
	 *
	 * @param bookRating
	 *            the book rating
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void rateBooks(Set<BookRating> bookRating) throws BookStoreException;

	/**
	 * Gets the list of books corresponding to the set of ISBNs.
	 *
	 * @param ISBNList
	 *            the ISBN list
	 * @return the books
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<Book> getBooks(Set<Integer> ISBNList) throws BookStoreException;

	/**
	 * Gets a list of top rated numBooks books.
	 *
	 * @param numBooks
	 *            the number of books
	 * @return the top rated books
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<Book> getTopRatedBooks(int numBooks) throws BookStoreException;

	/**
	 * Gets the list of books containing numBooks editor picks.
	 *
	 * @param numBooks
	 *            the number of books
	 * @return the editor picks
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<Book> getEditorPicks(int numBooks) throws BookStoreException;
}
