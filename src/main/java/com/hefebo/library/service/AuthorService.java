package com.hefebo.library.service;

import java.util.List;

import com.hefebo.library.dto.request.AuthorRequest;
import com.hefebo.library.dto.response.AuthorResponse;

public interface AuthorService {
    List<AuthorResponse> showAuthors();
    AuthorResponse searchAuthor(long idAuthor);
    AuthorResponse addAuthor(AuthorRequest dto);
    AuthorResponse updateAuthor(AuthorRequest dto, long idAuthor);
    void deleteAuthor(long idAuthor);

}
