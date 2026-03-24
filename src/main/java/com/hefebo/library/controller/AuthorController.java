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

import com.hefebo.library.dto.request.AuthorRequest;
import com.hefebo.library.dto.response.AuthorResponse;
import com.hefebo.library.service.AuthorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> showAuthors() {
        return ResponseEntity.ok(authorService.showAuthors());
    }

    @GetMapping("/{idAuthor}")
    public ResponseEntity<AuthorResponse> searchAuthor(@PathVariable long idAuthor) {
        return ResponseEntity.ok(authorService.searchAuthor(idAuthor));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> addAuthor(@Valid @RequestBody AuthorRequest dto) {
        return ResponseEntity.status(201).body(authorService.addAuthor(dto));
    }

    @PutMapping("/{idAuthor}")
    public ResponseEntity<AuthorResponse> updateAuthor(@Valid @RequestBody AuthorRequest dto, @PathVariable long idAuthor) {
        return ResponseEntity.ok(authorService.updateAuthor(dto, idAuthor));
    }

    @DeleteMapping("/{idAuthor}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable long idAuthor) {
        authorService.deleteAuthor(idAuthor);
        return ResponseEntity.noContent().build();
    }
    
}
