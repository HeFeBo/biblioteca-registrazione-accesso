package com.hefebo.library.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hefebo.library.dto.request.BookRequest;
import com.hefebo.library.dto.response.BookResponse;
import com.hefebo.library.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> showBooks() {
        return ResponseEntity.ok(bookService.showBooks());
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<BookResponse> searchBook(@PathVariable long idBook) {
        return ResponseEntity.ok(bookService.searchBook(idBook));
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest dto) {
        return ResponseEntity.status(201).body(bookService.addBook(dto));
    }

    @PutMapping("/{idBook}")
    public ResponseEntity<BookResponse> updateBook(@Valid @RequestBody BookRequest dto, @PathVariable long idBook) {
        return ResponseEntity.ok(bookService.updateBook(dto, idBook));
    }

    @DeleteMapping("/{idBook}")
    public ResponseEntity<Void> deleteBook(@PathVariable long idBook) {
        bookService.deleteBook(idBook);
        return ResponseEntity.noContent().build();
    }
    
}
