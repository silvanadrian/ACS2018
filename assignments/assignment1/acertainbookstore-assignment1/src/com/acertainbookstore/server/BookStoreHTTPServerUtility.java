package com.acertainbookstore.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * {@link BookStoreHTTPServerUtility} creates Jetty server instances.
 */
public class BookStoreHTTPServerUtility {

	/**
	 * Prevents the instantiation of a new {@link BookStoreHTTPServerUtility}.
	 */
	private BookStoreHTTPServerUtility() {
		// Prevent instances from being created.
	}

	/**
	 * Creates a server on the port and blocks the calling thread.
	 *
	 * @param port
	 *            the port
	 * @param handler
	 *            the handler
	 * @return true, if successful
	 */
	public static void createServer(int port, AbstractHandler handler, QueuedThreadPool threadpool) {
		Server server = null;
		if (threadpool != null) {
			server = new Server(threadpool);
			ServerConnector myConnector = new ServerConnector(server);
			myConnector.setPort(port);
			server.addConnector(myConnector);
		} else {
			server = new Server(port);
		}

		if (handler != null) {
			server.setHandler(handler);
		}

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			System.err.println(ex.getStackTrace());
		}

	}

	/**
	 * Creates a server on the InetAddress and blocks the calling thread.
	 *
	 * @param ipAddress
	 *            the IP address
	 * @param port
	 *            the port
	 * @param handler
	 *            the handler
	 * @return true, if successful
	 */
	public static boolean createServer(String ipAddress, int port, AbstractHandler handler) {
		InetAddress inetIpAddress;
		InetSocketAddress address;
		Server server;

		if (ipAddress == null) {
			return false;
		}

		try {
			inetIpAddress = InetAddress.getByName(ipAddress);
			address = new InetSocketAddress(inetIpAddress, port);
		} catch (UnknownHostException ex) {
			System.err.println(ex.getStackTrace());
			return false;
		}

		server = new Server(address);

		if (handler != null) {
			server.setHandler(handler);
		}

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			System.err.println(ex.getStackTrace());
		}

		return true;
	}
}
