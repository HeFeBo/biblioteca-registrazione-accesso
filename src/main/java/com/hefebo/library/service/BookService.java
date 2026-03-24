package com.hefebo.library.service;

import java.util.List;

import com.hefebo.library.dto.request.BookRequest;
import com.hefebo.library.dto.response.BookResponse;

public interface BookService {
    List<BookResponse> showBooks();
    BookResponse searchBook(long idBook);
    BookResponse addBook(BookRequest dto);
    BookResponse updateBook(BookRequest dto, long idBook);
    void deleteBook(long idBook);

}
