package com.dev.client;

import com.dev.entity.Book;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "book-service", url = "http://localhost:8080")
public interface BookClient {

    @GetMapping("/books/{id}")
    ResponseEntity<Book> getBookById(@PathVariable UUID id);
}
