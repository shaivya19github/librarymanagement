package com.cs.librarymanagement;

import com.cs.librarymanagement.dto.Book;
import com.cs.librarymanagement.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

@SpringBootApplication
@ComponentScan("com.cs")
public class LibrarymanagementApplication implements CommandLineRunner {

	@Autowired
	BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagementApplication.class, args);
	}



	@Override
	public void run(String... args) throws Exception
	{

	}

}
