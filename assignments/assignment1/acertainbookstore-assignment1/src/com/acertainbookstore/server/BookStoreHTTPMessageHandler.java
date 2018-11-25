package com.acertainbookstore.server;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.BookEditorPick;
import com.acertainbookstore.business.CertainBookStore;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.utils.BookStoreKryoSerializer;
import com.acertainbookstore.interfaces.BookStoreSerializer;
import com.acertainbookstore.utils.BookStoreXStreamSerializer;
import com.acertainbookstore.utils.BookStoreConstants;
import com.acertainbookstore.utils.BookStoreException;
import com.acertainbookstore.utils.BookStoreMessageTag;
import com.acertainbookstore.utils.BookStoreResponse;
import com.acertainbookstore.utils.BookStoreUtility;
import com.esotericsoftware.kryo.io.Input;

/**
 * {@link BookStoreHTTPMessageHandler} implements the message handler class
 * which is invoked to handle messages received by the
 * {@link BookStoreHTTPServerUtility}. It decodes the HTTP message and invokes
 * the {@link CertainBookStore} server API.
 * 
 * @see AbstractHandler
 * @see BookStoreHTTPServerUtility
 * @see CertainBookStore
 */
public class BookStoreHTTPMessageHandler extends AbstractHandler {

	/** The book store. */
	private CertainBookStore myBookStore = null;

	/** The serializer. */
	private static ThreadLocal<BookStoreSerializer> serializer;

	/**
	 * Instantiates a new {@link BookStoreHTTPMessageHandler}.
	 *
	 * @param bookStore
	 *            the book store
	 */
	public BookStoreHTTPMessageHandler(CertainBookStore bookStore) {
		myBookStore = bookStore;

		// Setup the type of serializer.
		if (BookStoreConstants.BINARY_SERIALIZATION) {
			serializer = ThreadLocal.withInitial(BookStoreKryoSerializer::new);
		} else {
			serializer = ThreadLocal.withInitial(BookStoreXStreamSerializer::new);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		BookStoreMessageTag messageTag;
		String requestURI;

		response.setStatus(HttpServletResponse.SC_OK);
		requestURI = request.getRequestURI();

		// Need to do request multiplexing
		if (!BookStoreUtility.isEmpty(requestURI) && requestURI.toLowerCase().startsWith("/stock")) {
			// The request is from the store manager; more sophisticated.
			// security features could be added here.
			messageTag = BookStoreUtility.convertURItoMessageTag(requestURI.substring(6));
		} else {
			messageTag = BookStoreUtility.convertURItoMessageTag(requestURI);
		}

		// The RequestURI before the switch.
		if (messageTag == null) {
			System.err.println("No message tag.");
		} else {
			switch (messageTag) {
			case REMOVEBOOKS:
				removeBooks(request, response);
				break;

			case REMOVEALLBOOKS:
				removeAllBooks(response);
				break;

			case ADDBOOKS:
				addBooks(request, response);
				break;

			case ADDCOPIES:
				addCopies(request, response);
				break;

			case LISTBOOKS:
				listBooks(response);
				break;

			case UPDATEEDITORPICKS:
				updateEditorPicks(request, response);
				break;

			case BUYBOOKS:
				buyBooks(request, response);
				break;

			case GETBOOKS:
				getBooks(request, response);
				break;

			case GETEDITORPICKS:
				getEditorPicks(request, response);
				break;

			case GETSTOCKBOOKSBYISBN:
				getStockBooksByISBN(request, response);
				break;

			default:
				System.err.println("Unsupported message tag.");
				break;
			}
		}

		// Mark the request as handled so that the HTTP response can be sent
		baseRequest.setHandled(true);
	}

	/**
	 * Gets the stock books by ISBN.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void getStockBooksByISBN(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<Integer> isbnSet = (Set<Integer>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			bookStoreResponse.setList(myBookStore.getBooksByISBN(isbnSet));
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Gets the editor picks.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void getEditorPicks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String numBooksString = URLDecoder.decode(request.getParameter(BookStoreConstants.BOOK_NUM_PARAM), StandardCharsets.UTF_8);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			int numBooks = BookStoreUtility.convertStringToInt(numBooksString);
			bookStoreResponse.setList(myBookStore.getEditorPicks(numBooks));
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Gets the books.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void getBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<Integer> isbnSet = (Set<Integer>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			bookStoreResponse.setList(myBookStore.getBooks(isbnSet));
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Buys books.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void buyBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<BookCopy> bookCopiesToBuy = (Set<BookCopy>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.buyBooks(bookCopiesToBuy);
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Updates editor picks.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void updateEditorPicks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<BookEditorPick> mapEditorPicksValues = (Set<BookEditorPick>) serializer.get()
				.deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.updateEditorPicks(mapEditorPicksValues);
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Lists the books.
	 *
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void listBooks(HttpServletResponse response) throws IOException {
		BookStoreResponse bookStoreResponse = new BookStoreResponse();
		bookStoreResponse.setList(myBookStore.getBooks());

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Adds the copies.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void addCopies(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<BookCopy> listBookCopies = (Set<BookCopy>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.addCopies(listBookCopies);
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Adds the books.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void addBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<StockBook> newBooks = (Set<StockBook>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.addBooks(newBooks);
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Removes all books.
	 *
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void removeAllBooks(HttpServletResponse response) throws IOException {
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.removeAllBooks();
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Removes the books.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unchecked")
	private void removeBooks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] serializedRequestContent = getSerializedRequestContent(request);

		Set<Integer> bookSet = (Set<Integer>) serializer.get().deserialize(serializedRequestContent);
		BookStoreResponse bookStoreResponse = new BookStoreResponse();

		try {
			myBookStore.removeBooks(bookSet);
		} catch (BookStoreException ex) {
			bookStoreResponse.setException(ex);
		}

		byte[] serializedResponseContent = serializer.get().serialize(bookStoreResponse);
		response.getOutputStream().write(serializedResponseContent);
	}

	/**
	 * Gets the serialized request content.
	 *
	 * @param request the request
	 * @return the serialized request content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private byte[] getSerializedRequestContent(HttpServletRequest request) throws IOException {
		Input in = new Input(request.getInputStream());
		byte[] serializedRequestContent = in.readBytes(request.getContentLength());
		in.close();
		return serializedRequestContent;
	}
}
