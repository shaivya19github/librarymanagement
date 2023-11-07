package com.cs.librarymanagement.repository;

import com.cs.librarymanagement.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // we'll define custom query methods here if needed
}
