package com.acertainbookstore.server;

import org.eclipse.jetty.util.thread.QueuedThreadPool;

import com.acertainbookstore.business.CertainBookStore;
import com.acertainbookstore.utils.BookStoreConstants;

/**
 * Starts the {@link BookStoreHTTPServer} that the clients will communicate
 * with.
 */
public class BookStoreHTTPServer {

	/** The Constant defaultListenOnPort. */
	private static final int DEFAULT_PORT = 8081;
	private static final int MIN_THREADPOOL_SIZE = 10;
	private static final int MAX_THREADPOOL_SIZE = 100;

	/**
	 * Prevents the instantiation of a new {@link BookStoreHTTPServer}.
	 */
	private BookStoreHTTPServer() {
		// Prevent instances from being created.
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		CertainBookStore bookStore = new CertainBookStore();
		int listenOnPort = DEFAULT_PORT;

		BookStoreHTTPMessageHandler handler = new BookStoreHTTPMessageHandler(bookStore);
		String serverPortString = System.getProperty(BookStoreConstants.PROPERTY_KEY_SERVER_PORT);

		if (serverPortString != null) {
			try {
				listenOnPort = Integer.parseInt(serverPortString);
			} catch (NumberFormatException ex) {
				System.err.println("Unsupported message tag");
			}
		}

		QueuedThreadPool threadpool = new QueuedThreadPool(MAX_THREADPOOL_SIZE, MIN_THREADPOOL_SIZE);
		BookStoreHTTPServerUtility.createServer(listenOnPort, handler, threadpool);
	}
}
