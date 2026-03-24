package com.hefebo.library.mapper;

import java.util.List;

import com.hefebo.library.dto.request.AuthorRequest;
import com.hefebo.library.dto.response.AuthorResponse;
import com.hefebo.library.model.Author;
import com.hefebo.library.model.Book;

import jakarta.annotation.Nonnull;

public class AuthorMapper {
    public static @Nonnull AuthorResponse toDto (Author author){
        List<Book> books = author.getBooks();
        List<Long> booksId = books.stream().map(b -> b.getId()).toList();

        return new AuthorResponse(author.getId(), author.getName(), booksId);
    }

    public static @Nonnull Author toEntity(AuthorRequest dto){
        return new Author(dto.getName());
    }
}
