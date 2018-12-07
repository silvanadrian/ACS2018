/**
 *
 */
package com.acertainbookstore.client.workloads;

import com.acertainbookstore.business.Book;
import com.acertainbookstore.business.BookCopy;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.interfaces.BookStore;
import com.acertainbookstore.interfaces.StockManager;
import com.acertainbookstore.utils.BookStoreException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Worker represents the workload runner which runs the workloads with parameters using
 * WorkloadConfiguration and then reports the results
 */
public class Worker implements Callable<WorkerRunResult> {

  private WorkloadConfiguration configuration = null;
  private int numSuccessfulFrequentBookStoreInteraction = 0;
  private int numTotalFrequentBookStoreInteraction = 0;

  public Worker(WorkloadConfiguration config) {
    configuration = config;
  }

  /**
   * Run the appropriate interaction while trying to maintain the configured distributions
   * <p>
   * Updates the counts of total runs and successful runs for customer interaction
   */
  private boolean runInteraction(float chooseInteraction) {
    try {
      float percentRareStockManagerInteraction = configuration
          .getPercentRareStockManagerInteraction();
      float percentFrequentStockManagerInteraction = configuration
          .getPercentFrequentStockManagerInteraction();

      if (chooseInteraction < percentRareStockManagerInteraction) {
        runRareStockManagerInteraction();
      } else if (chooseInteraction < percentRareStockManagerInteraction
          + percentFrequentStockManagerInteraction) {
        runFrequentStockManagerInteraction();
      } else {
        numTotalFrequentBookStoreInteraction++;
        runFrequentBookStoreInteraction();
        numSuccessfulFrequentBookStoreInteraction++;
      }
    } catch (BookStoreException ex) {
      return false;
    }
    return true;
  }

  /**
   * Run the workloads trying to respect the distributions of the interactions and return result in
   * the end
   */
  public WorkerRunResult call() throws Exception {
    int count = 1;
    long startTimeInNanoSecs = 0;
    long endTimeInNanoSecs = 0;
    int successfulInteractions = 0;
    long timeForRunsInNanoSecs = 0;

    Random rand = new Random();
    float chooseInteraction;

    // Perform the warmup runs
    while (count++ <= configuration.getWarmUpRuns()) {
      chooseInteraction = rand.nextFloat() * 100f;
      runInteraction(chooseInteraction);
    }

    count = 1;
    numTotalFrequentBookStoreInteraction = 0;
    numSuccessfulFrequentBookStoreInteraction = 0;

    // Perform the actual runs
    startTimeInNanoSecs = System.nanoTime();
    while (count++ <= configuration.getNumActualRuns()) {
      chooseInteraction = rand.nextFloat() * 100f;
      if (runInteraction(chooseInteraction)) {
        successfulInteractions++;
      }
    }
    endTimeInNanoSecs = System.nanoTime();
    timeForRunsInNanoSecs += (endTimeInNanoSecs - startTimeInNanoSecs);
    return new WorkerRunResult(successfulInteractions, timeForRunsInNanoSecs,
        configuration.getNumActualRuns(),
        numSuccessfulFrequentBookStoreInteraction, numTotalFrequentBookStoreInteraction);
  }

  /**
   * Runs the new stock acquisition interaction
   */
  private void runRareStockManagerInteraction() throws BookStoreException {
    StockManager stockManager = configuration.getStockManager();
    BookSetGenerator bookSetGenerator = configuration.getBookSetGenerator();

    List<StockBook> stockBookList = stockManager.getBooks();

    List<StockBook> bookList = new ArrayList<>(
        bookSetGenerator.nextSetOfStockBooks(configuration.getNumBooksToAdd()));

    final List<Integer> isbns = stockBookList.stream().map(Book::getISBN)
        .collect(Collectors.toList());

    final Set<StockBook> booksMissing = bookList.stream()
        .filter(book -> !isbns.contains(book.getISBN())).collect(Collectors.toSet());

    stockManager.addBooks(booksMissing);
  }

  /**
   * Runs the stock replenishment interaction
   */
  private void runFrequentStockManagerInteraction() throws BookStoreException {
    StockManager stockManager = configuration.getStockManager();

    final Set<BookCopy> bookCopies = new HashSet<>();
    stockManager.getBooks().stream()
        .sorted(Comparator.comparing(StockBook::getNumCopies).reversed())
        .limit(configuration.getNumBooksWithLeastCopies())
        .forEach(stockBook -> bookCopies
            .add(new BookCopy(stockBook.getISBN(), configuration.getNumAddCopies())));

    stockManager.addCopies(bookCopies);

  }

  /**
   * Runs the customer interaction
   */
  private void runFrequentBookStoreInteraction() throws BookStoreException {
    BookStore bookStore = configuration.getBookStore();
    BookSetGenerator bookSetGenerator = configuration.getBookSetGenerator();

    Set<Integer> booksISBN = bookStore.getEditorPicks(configuration.getNumEditorPicksToGet())
        .stream()
        .map(Book::getISBN).collect(Collectors.toSet());

    Set<BookCopy> booksToBuy = new HashSet<>();
    bookSetGenerator.sampleFromSetOfISBNs(booksISBN, configuration.getNumBooksToBuy())
        .forEach(isbn -> booksToBuy.add(new BookCopy(isbn, configuration.getNumBookCopiesToBuy())));

    bookStore.buyBooks(booksToBuy);

  }

}
