package com.dev.book.service;

import com.dev.book.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    Book addBook(Book book);

    List<Book> getAllBooks();

    Book getBookById(UUID id);

    Book updateBook(UUID id, Book book);

    void deleteBook(UUID id);
}
