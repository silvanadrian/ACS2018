package com.acertainbookstore.business;

/**
 * {@link BookEditorPick} is used to represent the book with its editor picked
 * status.
 */
public class BookEditorPick {

	/** The ISBN. */
	private int isbn;

	/** Whether the book is editor picked. */
	private boolean editorPick;

	/**
	 * Instantiates a new {@link BookEditorPick} with the
	 * <code>editorPick</code> status and <code>ISBN</code>.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param editorPick
	 *            whether the book is editor picked
	 */
	public BookEditorPick(int isbn, boolean editorPick) {
		this.setISBN(isbn);
		this.setEditorPick(editorPick);
	}

	/**
	 * Gets the ISBN.
	 *
	 * @return the ISBN
	 */
	public int getISBN() {
		return isbn;
	}

	/**
	 * Sets the ISBN.
	 *
	 * @param isbn
	 *            the new ISBN
	 */
	public void setISBN(int isbn) {
		this.isbn = isbn;
	}

	/**
	 * Checks whether the book is editor picked.
	 *
	 * @return true, if the book is editor picked
	 */
	public boolean isEditorPick() {
		return editorPick;
	}

	/**
	 * Sets whether the book is editor picked.
	 *
	 * @param editorPick
	 *            whether the book is editor picked
	 */
	public void setEditorPick(boolean editorPick) {
		this.editorPick = editorPick;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		return this.getISBN() == ((BookEditorPick) obj).getISBN();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getISBN();
	}
}
