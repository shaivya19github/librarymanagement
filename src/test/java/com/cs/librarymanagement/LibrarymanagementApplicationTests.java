package com.cs.librarymanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
//import org.junit.Test;
import com.cs.librarymanagement.dto.Book;
import com.cs.librarymanagement.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootTest
class LibrarymanagementApplicationTests {

	@Autowired
	BookService bookService;

	@Test
	public void testAddBook() throws JsonProcessingException {
		Book book = new Book();
		book.setBookName("Book 3");
		book.setIsbn("987654322");

		Book addedBook = bookService.add(book);

		assertNotNull(addedBook);
		assertEquals("Book 3", addedBook.getBookName());
		assertEquals("987654322", addedBook.getIsbn());
	}

//	@Test
//	public void testGetBookByIsbn() throws JsonProcessingException {
//		String isbn = "987654321";
//
//		Book book = bookService.get(isbn);
//
//		assertNotNull(book);
//		assertEquals(isbn, book.getIsbn());
//	}

	@Test
	public void testDeleteBook() throws JsonProcessingException {
		String isbn = "764827427";

		bookService.delete(isbn);
		Book book = bookService.get(isbn);

		assertNull(book);
	}

	@Test
	public void testUpdateBook() throws JsonProcessingException {
		String isbn = "987654322";
		String newName = "Updated Book";

		Book updatedBook = bookService.update(isbn, newName, null, null);

		assertNotNull(updatedBook);
		assertEquals(isbn, updatedBook.getIsbn());
		assertEquals(newName, updatedBook.getBookName());
	}


}
