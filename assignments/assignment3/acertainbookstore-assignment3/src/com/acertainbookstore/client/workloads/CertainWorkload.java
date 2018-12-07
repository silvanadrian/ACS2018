/**
 * 
 */
package com.acertainbookstore.client.workloads;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.acertainbookstore.business.CertainBookStore;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.client.BookStoreHTTPProxy;
import com.acertainbookstore.client.StockManagerHTTPProxy;
import com.acertainbookstore.interfaces.BookStore;
import com.acertainbookstore.interfaces.StockManager;
import com.acertainbookstore.utils.BookStoreConstants;
import com.acertainbookstore.utils.BookStoreException;

/**
 * 
 * CertainWorkload class runs the workloads by different workers concurrently.
 * It configures the environment for the workers using WorkloadConfiguration
 * objects and reports the metrics
 * 
 */
public class CertainWorkload {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		int numConcurrentWorkloadThreads = 10;
		String serverAddress = "http://localhost:8081";
		boolean localTest = true;
		List<WorkerRunResult> workerRunResults = new ArrayList<WorkerRunResult>();
		List<Future<WorkerRunResult>> runResults = new ArrayList<Future<WorkerRunResult>>();

		// Initialize the RPC interfaces if its not a localTest, the variable is
		// overriden if the property is set
		String localTestProperty = System
				.getProperty(BookStoreConstants.PROPERTY_KEY_LOCAL_TEST);
		localTest = (localTestProperty != null) ? Boolean
				.parseBoolean(localTestProperty) : localTest;

		BookStore bookStore = null;
		StockManager stockManager = null;
		if (localTest) {
			CertainBookStore store = new CertainBookStore();
			bookStore = store;
			stockManager = store;
		} else {
			stockManager = new StockManagerHTTPProxy(serverAddress + "/stock");
			bookStore = new BookStoreHTTPProxy(serverAddress);
		}

		// Generate data in the bookstore before running the workload
		initializeBookStoreData(stockManager);

		ExecutorService exec = Executors
				.newFixedThreadPool(numConcurrentWorkloadThreads);

		for (int i = 0; i < numConcurrentWorkloadThreads; i++) {
			WorkloadConfiguration config = new WorkloadConfiguration(bookStore,
					stockManager);
			Worker workerTask = new Worker(config);
			// Keep the futures to wait for the result from the thread
			runResults.add(exec.submit(workerTask));
		}

		// Get the results from the threads using the futures returned
		for (Future<WorkerRunResult> futureRunResult : runResults) {
			WorkerRunResult runResult = futureRunResult.get(); // blocking call
			workerRunResults.add(runResult);
		}

		exec.shutdownNow(); // shutdown the executor

		// Finished initialization, stop the clients if not localTest
		if (!localTest) {
			((BookStoreHTTPProxy) bookStore).stop();
			((StockManagerHTTPProxy) stockManager).stop();
		}

		reportMetric(workerRunResults);
	}

	/**
	 * Computes the metrics and prints them
	 * 
	 * @param workerRunResults
	 */
	public static void reportMetric(List<WorkerRunResult> workerRunResults) {

    int successfulInteractions = 0;
    long elapsedTimeInNanoSecs = 0;
    int totalRuns = 0;
    int successfulFrequentBookStoreInteractionRuns = 0;
    int totalFrequentBookStoreInteractionRuns = 0;

    double aggregatedThroughput = 0;
    double averageLatency = 0;
    double throughPut;

    for (WorkerRunResult workerRunResult : workerRunResults) {
      successfulInteractions += workerRunResult.getSuccessfulInteractions();
      totalRuns += workerRunResult.getTotalRuns();
      elapsedTimeInNanoSecs += workerRunResult.getElapsedTimeInNanoSecs();
      successfulFrequentBookStoreInteractionRuns += workerRunResult.getSuccessfulFrequentBookStoreInteractionRuns();
      totalFrequentBookStoreInteractionRuns += workerRunResult.getTotalFrequentBookStoreInteractionRuns();

      throughPut = workerRunResult.getSuccessfulFrequentBookStoreInteractionRuns() / (double) workerRunResult.getElapsedTimeInNanoSecs();
      aggregatedThroughput += throughPut;
      averageLatency += 1f / throughPut;
    }

    System.out.println("Successful Interactions: " + successfulInteractions);
    System.out.println("Successful Frequent Bookstore Interaction Runs: " + successfulFrequentBookStoreInteractionRuns);

    System.out.println("Total runs: " + totalRuns);
    System.out.println("Total Frequent Bookstore Interaction Runs: " + totalFrequentBookStoreInteractionRuns);

    System.out.println("Elapsed Time: " + elapsedTimeInNanoSecs + "ns");

    System.out.println("Aggregated Throughput: " + aggregatedThroughput);
    System.out.println("Average Latency: " + averageLatency);
	}

	/**
	 * Generate the data in bookstore before the workload interactions are run
	 * 
	 * Ignores the serverAddress if its a localTest
	 * 
	 */
	public static void initializeBookStoreData(StockManager stockManager) throws BookStoreException {

        // use the BookSet generator for generating random books (1000)
		BookSetGenerator bookSetGenerator = new BookSetGenerator(false);
		Set<StockBook> stockBookSet = bookSetGenerator.nextSetOfStockBooks(1000);

		//remove all books before, to be sure only the generated books are included
		stockManager.removeAllBooks();
		stockManager.addBooks(stockBookSet);
	}
}
