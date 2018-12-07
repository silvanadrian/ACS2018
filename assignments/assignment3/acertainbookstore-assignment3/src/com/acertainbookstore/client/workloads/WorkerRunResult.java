package com.acertainbookstore.client.workloads;

/**
 * 
 * WorkerRunResult class represents the result returned by a worker class after
 * running the workload interactions
 * 
 */
public class WorkerRunResult {
	private int successfulInteractions; // total number of successful interactions
	private int totalRuns; // total number of interactions run 
	private long elapsedTimeInNanoSecs; // total time taken to run all
										// interactions
	private int successfulFrequentBookStoreInteractionRuns; // number of
															// successful
															// frequent book
															// store interaction
															// runs
	private int totalFrequentBookStoreInteractionRuns; // total number of
														// bookstore interaction
														// runs

	public WorkerRunResult(int successfulInteractions, long elapsedTimeInNanoSecs,
			int totalRuns, int successfulFrequentBookStoreInteractionRuns,
			int totalFrequentBookStoreInteractionRuns) {
		this.setSuccessfulInteractions(successfulInteractions);
		this.setElapsedTimeInNanoSecs(elapsedTimeInNanoSecs);
		this.setTotalRuns(totalRuns);
		this.setSuccessfulFrequentBookStoreInteractionRuns(successfulFrequentBookStoreInteractionRuns);
		this.setTotalFrequentBookStoreInteractionRuns(totalFrequentBookStoreInteractionRuns);
	}

	public int getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(int totalRuns) {
		this.totalRuns = totalRuns;
	}

	public int getSuccessfulInteractions() {
		return successfulInteractions;
	}

	public void setSuccessfulInteractions(int successfulInteractions) {
		this.successfulInteractions = successfulInteractions;
	}

	public long getElapsedTimeInNanoSecs() {
		return elapsedTimeInNanoSecs;
	}

	public void setElapsedTimeInNanoSecs(long elapsedTimeInNanoSecs) {
		this.elapsedTimeInNanoSecs = elapsedTimeInNanoSecs;
	}

	public int getSuccessfulFrequentBookStoreInteractionRuns() {
		return successfulFrequentBookStoreInteractionRuns;
	}

	public void setSuccessfulFrequentBookStoreInteractionRuns(
			int successfulFrequentBookStoreInteractionRuns) {
		this.successfulFrequentBookStoreInteractionRuns = successfulFrequentBookStoreInteractionRuns;
	}

	public int getTotalFrequentBookStoreInteractionRuns() {
		return totalFrequentBookStoreInteractionRuns;
	}

	public void setTotalFrequentBookStoreInteractionRuns(
			int totalFrequentBookStoreInteractionRuns) {
		this.totalFrequentBookStoreInteractionRuns = totalFrequentBookStoreInteractionRuns;
	}

}
