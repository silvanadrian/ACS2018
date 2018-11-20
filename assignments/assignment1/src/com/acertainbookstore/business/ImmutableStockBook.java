package com.acertainbookstore.business;

/**
 * {@link ImmutableStockBook} gets sent back to the {@link StockManager}. We do
 * not allow the {@link StockManager} to make any changes to the data structure.
 * 
 * @see StockManager
 * @see ImmutableBook
 * @see StockBook
 */
public final class ImmutableStockBook extends ImmutableBook implements StockBook {

	/** The total rating. */
	private final long totalRating;

	/** The number of times rated. */
	private final long numTimesRated;

	/** The number of copies. */
	private final int numCopies;

	/** The number of sale misses. */
	private final long numSaleMisses;

	/** Whether the book is editor picked. */
	private final boolean editorPick;

	/**
	 * Instantiates a new {@link ImmutableStockBook}. This constructor is
	 * necessary for serialization and has no other purpose.
	 */
	public ImmutableStockBook() {
		this.totalRating = 0;
		this.numTimesRated = 0;
		this.numCopies = 0;
		this.numSaleMisses = 0;
		this.editorPick = false;
	}

	/**
	 * Instantiates a new {@link ImmutableStockBook}.
	 *
	 * @param isbn
	 *            the ISBN
	 * @param title
	 *            the title
	 * @param author
	 *            the author
	 * @param price
	 *            the price
	 * @param numCopies
	 *            the number of copies
	 * @param numSaleMisses
	 *            the number of sale misses
	 * @param numTimesRated
	 *            the number of times rated
	 * @param totalRating
	 *            the total rating
	 * @param editorPick
	 *            whether the book is editor picked
	 */
	public ImmutableStockBook(int isbn, String title, String author, float price, int numCopies, long numSaleMisses,
			long numTimesRated, long totalRating, boolean editorPick) {
		super(isbn, title, author, price);

		this.totalRating = totalRating;
		this.numTimesRated = numTimesRated;
		this.numCopies = numCopies;
		this.numSaleMisses = numSaleMisses;
		this.editorPick = editorPick;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#getTotalRating()
	 */
	public long getTotalRating() {
		return totalRating;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#getTimesRated()
	 */
	public long getNumTimesRated() {
		return numTimesRated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#getNumCopies()
	 */
	public int getNumCopies() {
		return numCopies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#getSaleMisses()
	 */
	public long getNumSaleMisses() {
		return numSaleMisses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#getAverageRating()
	 */
	public float getAverageRating() {
		return (numTimesRated == 0 ? -1.0f : (float) totalRating / numTimesRated);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.acertainbookstore.business.StockBook#isEditorPick()
	 */
	public boolean isEditorPick() {
		return editorPick;
	}
}
