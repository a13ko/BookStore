package com.dev.book.service.impl;

import com.dev.book.entity.Book;
import com.dev.book.repository.BookRepository;
import com.dev.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateBook(UUID id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
