package com.acertainbookstore.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.acertainbookstore.business.Book;
import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.BookRating;
import com.acertainbookstore.interfaces.BookStore;
import com.acertainbookstore.utils.BookStoreKryoSerializer;
import com.acertainbookstore.interfaces.BookStoreSerializer;
import com.acertainbookstore.utils.BookStoreXStreamSerializer;
import com.acertainbookstore.utils.BookStoreConstants;
import com.acertainbookstore.utils.BookStoreException;
import com.acertainbookstore.utils.BookStoreMessageTag;
import com.acertainbookstore.utils.BookStoreRequest;
import com.acertainbookstore.utils.BookStoreResponse;
import com.acertainbookstore.utils.BookStoreUtility;

/**
 * {@link BookStoreHTTPProxy} implements the client level synchronous
 * {@link CertainBookStore} API declared in the {@link BookStore} class.
 * 
 * @see BookStore
 * @see CertainBookStore
 */
public class BookStoreHTTPProxy implements BookStore {

	/** The client. */
	protected HttpClient client;

	/** The server address. */
	protected String serverAddress;

	/** The serializer. */
	private static ThreadLocal<BookStoreSerializer> serializer;

	/**
	 * Initializes a new {@link BookStoreHTTPProxy}.
	 *
	 * @param serverAddress
	 *            the server address
	 * @throws Exception
	 *             the exception
	 */
	public BookStoreHTTPProxy(String serverAddress) throws Exception {

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
	 * @see com.acertainbookstore.interfaces.BookStore#buyBooks(java.util.Set)
	 */
	public void buyBooks(Set<BookCopy> isbnSet) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.BUYBOOKS;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, isbnSet);
		BookStoreUtility.performHttpExchange(client, bookStoreRequest, serializer.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.BookStore#getBooks(java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<Book> getBooks(Set<Integer> isbnSet) throws BookStoreException {
		String urlString = serverAddress + "/" + BookStoreMessageTag.GETBOOKS;
		BookStoreRequest bookStoreRequest = BookStoreRequest.newPostRequest(urlString, isbnSet);
		BookStoreResponse bookStoreResponse = BookStoreUtility.performHttpExchange(client, bookStoreRequest,
				serializer.get());
		return (List<Book>) bookStoreResponse.getList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.BookStore#getEditorPicks(int)
	 */
	@SuppressWarnings("unchecked")
	public List<Book> getEditorPicks(int numBooks) throws BookStoreException {
		String urlEncodedNumBooks = null;

		urlEncodedNumBooks = URLEncoder.encode(Integer.toString(numBooks), StandardCharsets.UTF_8);

		String urlString = serverAddress + "/" + BookStoreMessageTag.GETEDITORPICKS + "?"
				+ BookStoreConstants.BOOK_NUM_PARAM + "=" + urlEncodedNumBooks;

		BookStoreRequest bookStoreRequest = BookStoreRequest.newGetRequest(urlString);
		BookStoreResponse bookStoreResponse = BookStoreUtility.performHttpExchange(client, bookStoreRequest,
				serializer.get());
		return (List<Book>) bookStoreResponse.getList();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.BookStore#rateBooks(java.util.Set)
	 */
	@Override
	public void rateBooks(Set<BookRating> bookRating) throws BookStoreException {
		throw new BookStoreException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.interfaces.BookStore#getTopRatedBooks(int)
	 */
	@Override
	public List<Book> getTopRatedBooks(int numBooks) throws BookStoreException {
		throw new BookStoreException();
	}
}
