package com.acertainbookstore.interfaces;

import java.util.List;
import java.util.Set;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.BookEditorPick;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.utils.BookStoreException;

/**
 * {@link StockManager} declares the methods exposed to be implemented by
 * clients who sell items in the book store.
 */
public interface StockManager {

	/**
	 * Adds the books in bookSet to the stock.
	 *
	 * @param bookSet
	 *            the book set
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void addBooks(Set<StockBook> bookSet) throws BookStoreException;

	/**
	 * Adds copies of the existing books to the bookstore.
	 *
	 * @param bookCopiesSet
	 *            the book copies set
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void addCopies(Set<BookCopy> bookCopiesSet) throws BookStoreException;

	/**
	 * Gets the list of books in the bookstore.
	 *
	 * @return the books
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<StockBook> getBooks() throws BookStoreException;

	/**
	 * Gets the books matching the set of ISBNs given, is different to getBooks
	 * in the BookStore interface because of the return type of the books.
	 *
	 * @param isbns
	 *            the ISBNs
	 * @return the books by ISBN
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<StockBook> getBooksByISBN(Set<Integer> isbns) throws BookStoreException;

	/**
	 * Gets the list of books which has sale miss.
	 *
	 * @return the books in demand
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public List<StockBook> getBooksInDemand() throws BookStoreException;

	/**
	 * Updates the books by mark/unmark them as editor pick.
	 *
	 * @param editorPicks
	 *            the editor picks
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void updateEditorPicks(Set<BookEditorPick> editorPicks) throws BookStoreException;

	/**
	 * Cleans up the bookstore - remove all the books and the associated data.
	 *
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void removeAllBooks() throws BookStoreException;

	/**
	 * Cleans up the bookstore selectively for the list of provided ISBNs.
	 *
	 * @param isbnSet
	 *            the ISBN set
	 * @throws BookStoreException
	 *             the book store exception
	 */
	public void removeBooks(Set<Integer> isbnSet) throws BookStoreException;
}