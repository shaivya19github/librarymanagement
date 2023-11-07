package com.cs.librarymanagement.service;

import com.amazonaws.services.sqs.model.Message;
import com.cs.librarymanagement.dto.Book;
import com.cs.librarymanagement.repository.BookRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Date;
import java.util.List;

@EnableScheduling // Enable Spring's scheduled task execution
@Service
public class BookServiceImpl implements  BookService {

    private final AmazonSQS sqs;
    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookRepository bookRepository;

    public BookServiceImpl() {
        sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion("eu-west-1")
                .build();
    }

//  Method to receive and process messages
    @Scheduled(fixedRate = 2000) // Fetch messages every 2 seconds
    public void receiveAndProcessMessages() {
//        System.out.println("H********** receiveAndProcessMessages ******");
        logger.info("H********** receiveAndProcessMessages ******");

        // SQS queue URL
        String queueUrl = "https://sqs.eu-west-1.amazonaws.com/961043014301/MyQueue.fifo";

        // Receive messages from the SQS queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        for (Message message : messages) {
            // Process the message
            String messageBody = message.getBody();
//            System.out.println(messageBody);
            logger.info("messageBody");


            try {
                // Parse the message
                Book book = objectMapper.readValue(messageBody, Book.class);

                // Save the book to the database
                bookRepository.save(book);

                // Delete the processed message from the queue
                sqs.deleteMessage(queueUrl, message.getReceiptHandle());
            }
            catch (JsonParseException e)
            {
                // Handle JSON parsing errors
                System.err.println("Error parsing JSON message: " + e.getMessage());
            }
            catch (Exception e)
            {
                // Handle other exceptions
                System.err.println("Error processing message: " + e.getMessage());

            }
        }
    }

    // Method to send message to SQS Queue
    public void sendMessageToSQS(String message) throws JsonProcessingException {
        String queueUrl = "https://sqs.eu-west-1.amazonaws.com/961043014301/MyQueue.fifo"; // SQS queue URL

        // Create an SQS client
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion("eu-west-1")
                .build();

        // Sample Book object
        Book book = new Book("123456789", "Book 1", "Author 1", new Date(), "reviewer 1");

        // Generate a JSON message from the Book object
        String jsonMessage = generateJsonMessage(book);

        // Create a request to send the message
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(jsonMessage);
        sendMsgRequest.setMessageGroupId("foo");
        sendMsgRequest.setMessageDeduplicationId("1");

        // Send the message
        sqs.sendMessage(sendMsgRequest);

        System.out.println("Message sent to the queue."+ message);
    }

        public static String generateJsonMessage(Book book) throws JsonProcessingException {
            // Create an ObjectMapper instance to convert the Book object to JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Book object to a JSON string
            return objectMapper.writeValueAsString(book);
        }



    @Override
    public Book add(Book book) throws JsonProcessingException {
        System.out.println("Inserting the book: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(book));
        logger.info("Inserting the book: {}", book);
        bookRepository.save(book);
        return book;
    }

    @Override
    public void delete(String isbn) {
        Book bookToDelete =bookRepository.findById(isbn).orElse(null);
        if (bookToDelete != null) {
            bookRepository.delete(bookToDelete);
        }

    }

    @Override
    public Book get(String isbn) throws JsonProcessingException
    {
        Book fetchBook = bookRepository.findById(isbn).orElse(null);
        if (fetchBook != null)
        {
            bookRepository.delete(fetchBook);
            return fetchBook;
        }
        else
        {
            logger.debug("Book not found", fetchBook);
            return null; // Book not found
        }
    }


    @Override
    public Book update(String isbn, String newName, String newAuthor, Date newPublicationDate) throws JsonProcessingException
    {
        // Find the book by ISBN and update its properties
        Book existingBook = bookRepository.findById(isbn).orElse(null);;
        if (existingBook != null) {
            existingBook.setBookName(newName);
            existingBook.setAuthorName(newAuthor);
            existingBook.setPublishDate(newPublicationDate);
            return bookRepository.save(existingBook);
        }
        else
        {
            logger.debug("Book not found", existingBook);
            return null; // Book not found
        }
    }
}
