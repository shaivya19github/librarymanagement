package com.cs.librarymanagement.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    private String isbn;
    private String bookName;
    private String authorName;
    private Date publishDate;
    private String reviewerName;
    public Book(String isbn, String bookName, String authorName, Date publishDate, String s) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publishDate = publishDate;
        this.reviewerName = reviewerName;
    }

    public Book() {

    }
}



