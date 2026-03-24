package com.hefebo.library.mapper;

import com.hefebo.library.dto.request.BookRequest;
import com.hefebo.library.dto.response.BookResponse;
import com.hefebo.library.model.Author;
import com.hefebo.library.model.Book;

import jakarta.annotation.Nonnull;

public class BookMapper {
    public static @Nonnull BookResponse toDto (Book book){
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor().getId());
    }

    public static @Nonnull Book toEntity (BookRequest dto, Author author){
        return new Book(dto.getTitle(), author);
    }

}
