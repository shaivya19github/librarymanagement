package com.cs.librarymanagement.controller;

import com.cs.librarymanagement.dto.Book;
import com.cs.librarymanagement.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Add a book
    @PostMapping
    public Book addBook(@RequestBody Book book) throws JsonProcessingException {
        return bookService.add(book);
    }

//     Update a book
//    @PutMapping("/{isbn}")
//    public Book updateBook(@PathVariable String isbn, @RequestBody Book book) {
//        return bookService.update(isbn, book);
//    }

    // Fetch a book by ISBN
    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) throws JsonProcessingException {
        return bookService.get(isbn);
    }

    // Delete a book by ISBN
    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable String isbn) {
        bookService.delete(isbn);
    }
}


