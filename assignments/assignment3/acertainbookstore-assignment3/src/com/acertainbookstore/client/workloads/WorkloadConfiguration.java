package com.acertainbookstore.client.workloads;

import com.acertainbookstore.interfaces.BookStore;
import com.acertainbookstore.interfaces.StockManager;

/**
 * 
 * WorkloadConfiguration represents the configuration parameters to be used by
 * Workers class for running the workloads
 * 
 */
public class WorkloadConfiguration {
	private int numBooksToBuy = 5;
	private int numBookCopiesToBuy = 1;
	private int numEditorPicksToGet = 10;
	private int numAddCopies = 10;
	private int numBooksToAdd = 5;
	private int numBooksWithLeastCopies = 5;
	private int warmUpRuns = 100;
	private int numActualRuns = 500;
	private float percentRareStockManagerInteraction = 10f;
	private float percentFrequentStockManagerInteraction = 30f;
	private BookSetGenerator bookSetGenerator = null;
	private StockManager stockManager = null;
	private BookStore bookStore = null;

	public WorkloadConfiguration(BookStore bookStore, StockManager stockManager) throws Exception {
		// Create a new one so that it is not shared
		bookSetGenerator = new BookSetGenerator();
		this.bookStore = bookStore;
		this.stockManager = stockManager;
	}

	public int getNumBooksToBuy() {
		return numBooksToBuy;
	}

	public void setNumBooksToBuy(int numBooksToBuy) {
		this.numBooksToBuy = numBooksToBuy;
	}

	public int getNumBookCopiesToBuy() {
		return numBookCopiesToBuy;
	}

	public void setNumBookCopiesToBuy(int numBookCopiesToBuy) {
		this.numBookCopiesToBuy = numBookCopiesToBuy;
	}

	public int getNumBooksToAdd() {
		return numBooksToAdd;
	}

	public void setNumBooksToAdd(int numBooksToAdd) {
		this.numBooksToAdd = numBooksToAdd;
	}

	public int getNumBooksWithLeastCopies() {
		return numBooksWithLeastCopies;
	}

	public void setNumBooksWithLeastCopies(int numBooksWithLeastCopies) {
		this.numBooksWithLeastCopies = numBooksWithLeastCopies;
	}
	
	public StockManager getStockManager() {
		return stockManager;
	}

	public BookStore getBookStore() {
		return bookStore;
	}

	public void setStockManager(StockManager stockManager) {
		this.stockManager = stockManager;
	}

	public void setBookStore(BookStore bookStore) {
		this.bookStore = bookStore;
	}

	public float getPercentRareStockManagerInteraction() {
		return percentRareStockManagerInteraction;
	}

	public void setPercentRareStockManagerInteraction(
			float percentRareStockManagerInteraction) {
		this.percentRareStockManagerInteraction = percentRareStockManagerInteraction;
	}

	public float getPercentFrequentStockManagerInteraction() {
		return percentFrequentStockManagerInteraction;
	}

	public void setPercentFrequentStockManagerInteraction(
			float percentFrequentStockManagerInteraction) {
		this.percentFrequentStockManagerInteraction = percentFrequentStockManagerInteraction;
	}

	public int getWarmUpRuns() {
		return warmUpRuns;
	}

	public void setWarmUpRuns(int warmUpRuns) {
		this.warmUpRuns = warmUpRuns;
	}

	public int getNumActualRuns() {
		return numActualRuns;
	}

	public void setNumActualRuns(int numActualRuns) {
		this.numActualRuns = numActualRuns;
	}

	public int getNumEditorPicksToGet() {
		return numEditorPicksToGet;
	}

	public void setNumEditorPicksToGet(int numEditorPicksToGet) {
		this.numEditorPicksToGet = numEditorPicksToGet;
	}

	public int getNumAddCopies() {
		return numAddCopies;
	}

	public void setNumAddCopies(int numAddCopies) {
		this.numAddCopies = numAddCopies;
	}

	public BookSetGenerator getBookSetGenerator() {
		return bookSetGenerator;
	}

	public void setBookSetGenerator(BookSetGenerator bookSetGenerator) {
		this.bookSetGenerator = bookSetGenerator;
	}

}
