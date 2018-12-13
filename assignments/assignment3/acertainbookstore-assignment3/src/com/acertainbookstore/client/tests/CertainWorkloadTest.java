package com.acertainbookstore.client.tests;

import com.acertainbookstore.business.CertainBookStore;
import com.acertainbookstore.client.workloads.CertainWorkload;
import com.acertainbookstore.utils.BookStoreException;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CertainWorkloadTest {

  private CertainBookStore bookStore;
  private CertainBookStore stockManager;

  @BeforeClass
  public void setUp() {
    CertainBookStore store = new CertainBookStore();
    bookStore = store;
    stockManager = store;
  }


  @Test
  public void shouldInitializeBookStore() throws BookStoreException {
    CertainWorkload.initializeBookStoreData(stockManager);
    // initialize 1000 books
    assertEquals(1000, bookStore.getBooks().size());
  }

}
