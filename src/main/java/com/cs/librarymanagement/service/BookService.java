package com.cs.librarymanagement.service;

import com.cs.librarymanagement.dto.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public interface BookService {
    Book add(Book book) throws JsonProcessingException;
    void delete(String isbn);
    Book get(String isbn) throws JsonProcessingException;
    Book update(String isbn, String newName, String newAuthor, Date newPublicationDate) throws JsonProcessingException;
    }


