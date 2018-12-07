package com.acertainbookstore.client.tests;

import com.acertainbookstore.business.Book;
import com.acertainbookstore.business.StockBook;
import com.acertainbookstore.client.workloads.BookSetGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class BookSetGeneratorTest {

    private BookSetGenerator bookSetGenerator;

    @Before
    public void setUp() {
        bookSetGenerator = new BookSetGenerator();
    }

    @Test
    public void shouldReturnRandomISBNS() {
        Set<Integer> isbns = IntStream.rangeClosed(1, 9000).boxed().collect(Collectors.toSet());
        assertEquals(30,bookSetGenerator.sampleFromSetOfISBNs(isbns,30).size());
    }

    @Test
    public void shouldReturnRandomBooks() {
        Set<StockBook> bookSet = bookSetGenerator.nextSetOfStockBooks(100);
        assertEquals(100, bookSet.size());
    }
}
