package com.acertainbookstore.utils;

import com.acertainbookstore.interfaces.BookStoreSerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * {@link BookStoreXStreamSerializer} serializes objects to arrays of bytes
 * representing XML trees using the XStream library.
 * 
 * @see BookStoreSerializer
 */
public final class BookStoreXStreamSerializer implements BookStoreSerializer {

	/** The XML stream. */
	private final XStream xmlStream = new XStream(new StaxDriver());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.BookStoreSerializer#serialize(java.lang.
	 * Object)
	 */
	@Override
	public byte[] serialize(Object object) {
		String xml = xmlStream.toXML(object);
		return xml.getBytes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.acertainbookstore.interfaces.BookStoreSerializer#deserialize(byte[])
	 */
	@Override
	public Object deserialize(byte[] bytes) {
		String xml = new String(bytes);
		return xmlStream.fromXML(xml);
	}
}
