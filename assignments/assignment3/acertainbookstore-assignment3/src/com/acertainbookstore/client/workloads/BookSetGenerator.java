package com.acertainbookstore.client.workloads;

import com.acertainbookstore.business.Book;
import com.acertainbookstore.business.ImmutableStockBook;
import com.acertainbookstore.business.StockBook;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Helper class to generate stockbooks and isbns modelled similar to Random
 * class
 */
public class BookSetGenerator {

    private static Integer ISBN = 1;
    private Faker faker;
    private Random random;

	public BookSetGenerator() {
		// TODO Auto-generated constructor stub
        faker = new Faker();
        random = new Random();
	}

	/**
	 * Returns num randomly selected isbns from the input set
	 * 
	 * @param num
	 * @return
	 */
	public Set<Integer> sampleFromSetOfISBNs(Set<Integer> isbns, int num) {
	    final List<Integer> temp = new ArrayList<>(isbns);
		Collections.shuffle(temp, new Random());
		return temp.stream().limit(num).collect(Collectors.toSet());
	}

	/**
	 * Return num stock books. For now return an ImmutableStockBook
	 * 
	 * @param num
	 * @return
	 */
	public Set<StockBook> nextSetOfStockBooks(int num) {
	    Set<StockBook> result = new HashSet<>();
        IntStream.rangeClosed(1, num).forEach(i -> result.add(createRandomBook()));
		return result;
	}

    private ImmutableStockBook createRandomBook() {
	    final String title = faker.book().title();
	    final String author = faker.book().author();
        float price = random.nextFloat() * (300f - 5f) + 5f;
        int numCopies = random.ints(1,0,9999).findAny().getAsInt();
        long numSaleMisses = random.longs(1,0L,1000L).findAny().getAsLong();
        long numTimesRated = random.longs(1, 0L, 1000L).findAny().getAsLong();
        long totalRating = random.longs(1,0L,5L).findAny().getAsLong();
        boolean editorPick = random.nextBoolean();

        return new ImmutableStockBook(getISBN(),title,author, price, numCopies,numSaleMisses,numTimesRated,totalRating,editorPick);
    }

    private Integer getISBN() {
	    return ISBN++;
    }

}
