package com.acertainbookstore.client;

import java.util.List;
import java.util.Set;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.BookEditorPick;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.utils.BookStoreKryoSerializer;
import com.acertainbookstore.interfaces.BookStoreSerializer;
import com.acertainbookstore.utils.BookStoreXStreamSerializer;
import com.acertainbookstore.interfaces.StockManager;
import com.acertainbookstore.utils.BookStoreConstants;
import com.acertainbookstore.utils.BookStoreException;
import com.acertainbookstore.utils.BookStoreMessageTag;
import com.acertainbookstore.utils.BookStoreRequest;
import com.acertainbookstore.utils.BookStoreResponse;
import com.acertainbookstore.utils.BookStoreUtility;

/**
 * {@link StockManagerHTTPProxy} implements the client level synchronous
 * {@link CertainBookStore} API declared in the {@link StockManager} class. Uses
 * the HTTP protocol for communication with the server.
 * 
 * @see CertainBookStore
 * @see StockManager
 */
public class StockManagerHTTPProxy implements StockManager {

	/** The client. */
	protected HttpClient client;

	/** The server address. */
	protected String serverAddress;

	/** The serializer. */
	private static ThreadLocal<BookStoreSerializer> serializer;

	/**
	 * Initializes a new {@link StockManagerHTTPProxy}.
	 *
	 * @param serverAddress
	 *            the server address
	 * @throws Exception
	 *             the exception
	 */
	public StockManagerHTTPProxy(String serverAddress) throws Exception {

		// Setup the type of serializer.
		if (BookStoreConstants.BINARY_SERIALIZATION) {
			serializer = ThreadLocal.withInitial(BookStoreKryoSerializer::new);
		} else {
			serializer = ThreadLocal.withInitial(BookStoreXStreamSerializer::new);
		}

		setServerAddress(serverAddress);
		client = new HttpClient();

		// Max concurrent connections to every address.
		client.setMaxConnectionsPerDestination(BookStoreClientConstants.CLIENT_MAX_CONNECTION_ADDRESS);

		// Max number of threads.
		client.setExecutor(new QueuedThreadPool(BookStoreClientConstants.CLIENT_MAX_THREADSPOOL_THREADS));

		// Seconds timeout; if no server reply, the request expires.
		client.setConnectTimeout(BookStoreClientConstants.CLIENT_MAX_TIMEOUT_MILLISECS);

		client.start();
	}

	/**
	 * Gets the server address.
	 *
	 * @return the server address
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * Sets the server address.
	 *
	 * @param serverAddress
	 *            the new server address
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.StockManager#addBooks(java.util.Set)
	 */
	public void addBooks(Set<StockBook> bookSet) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.ADDBOOKS;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, bookSet);
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.StockManager#addCopies(java.util.Set)
	 */
	public void addCopies(Set<BookCopy> bookCopiesSet) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.ADDCOPIES;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, bookCopiesSet);
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.StockManager#getBooks()
	 */
	@SuppressWarnings("unchecked")
	public List<StockBook> getBooks() throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.LISTBOOKS;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newGetRequest(urlString);
		BookStoreResponse bookStoreResponse = BookStoreUtility.performHttpExchange(client, bookStoreRequest,
				serializer.get());
		return (List<StockBook>) bookStoreResponse.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.StockManager#updateEditorPicks(java.util
	 * .Set)
	 */
	public void updateEditorPicks(Set<BookEditorPick> editorPicksValues) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.UPDATEEDITORPICKS + "?";
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, editorPicksValues);
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.StockManager#getBooksInDemand()
	 */
	@Override
	public List<StockBook> getBooksInDemand() throws BookStoreException {
		throw new BookStoreException("Not implemented");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.StockManager#removeAllBooks()
	 */
	public void removeAllBooks() throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.REMOVEALLBOOKS;

		// Creating zero-length buffer for POST request body, because we don't
		// need to send any data; this request is just a signal to remove all
		// books.
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, "");
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.StockManager#removeBooks(java.util.Set)
	 */
	public void removeBooks(Set<Integer> isbnSet) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.REMOVEBOOKS;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, isbnSet);
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.StockManager#getBooksByISBN(java.util.
	 * Set)
	 */
	@SuppressWarnings("unchecked")
	public List<StockBook> getBooksByISBN(Set<Integer> isbns) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.GETSTOCKBOOKSBYISBN;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, isbns);
		BookStoreResponse bookStoreResponse = BookStoreUtility.performHttpExchange(client, bookStoreRequest,
				serializer.get());
		return (List<StockBook>) bookStoreResponse.getList();
	}

	/**
	 * Stops the proxy.
	 */
	public void stop() {
		try {
			client.stop();
		} catch (Exception ex) {
			System.err.println(ex.getStackTrace());
		}
	}
}
