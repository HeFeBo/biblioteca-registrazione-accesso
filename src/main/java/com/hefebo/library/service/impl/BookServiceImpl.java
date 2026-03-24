package com.hefebo.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hefebo.library.dto.request.BookRequest;
import com.hefebo.library.dto.response.BookResponse;
import com.hefebo.library.exception.AuthorNotFoundException;
import com.hefebo.library.exception.BookNotFoundException;
import com.hefebo.library.mapper.BookMapper;
import com.hefebo.library.model.Author;
import com.hefebo.library.model.Book;
import com.hefebo.library.repository.AuthorRepository;
import com.hefebo.library.repository.BookRepository;
import com.hefebo.library.service.BookService;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookServiceImpl(BookRepository bookRepo, AuthorRepository authorRepo){
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    @Override
    public List<BookResponse> showBooks() {
        return bookRepo.findAll().stream().map(b -> BookMapper.toDto(b)).toList();
    }

    @Override
    public BookResponse searchBook(long idBook) {
        Book book = bookRepo.findById(idBook).orElseThrow(() -> new BookNotFoundException("The book with ID " + idBook + " was not found."));
        return BookMapper.toDto(book);
    }

    @Override
    public BookResponse addBook(BookRequest dto) {
        Author author = authorRepo.findById(dto.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException("The author with ID " + dto.getAuthorId() + " was not found.")) ;
        Book book = BookMapper.toEntity(dto, author);
        Book savedBook = bookRepo.save(book);
        return BookMapper.toDto(savedBook);
    }

    @Override
    public BookResponse updateBook(BookRequest dto, long idBook) {
        Book book = bookRepo.findById(idBook).orElseThrow(() -> new BookNotFoundException("The book with ID " + idBook + " was not found."));
        Author author = authorRepo.findById(dto.getAuthorId()).orElseThrow(() -> new AuthorNotFoundException("The author with ID " + dto.getAuthorId() + " was not found."));
        book.setAuthor(author);
        book.setTitle(dto.getTitle());
        bookRepo.save(book);
        return BookMapper.toDto(book);
    }

    @Override
    public void deleteBook(long idBook) {
        if(!bookRepo.existsById(idBook)) throw new BookNotFoundException("The book with ID " + idBook + " was not found.");
        bookRepo.deleteById(idBook);
    }

}
