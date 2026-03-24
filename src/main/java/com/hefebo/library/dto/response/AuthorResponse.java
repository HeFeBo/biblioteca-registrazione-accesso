package com.hefebo.library.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorResponse {
    private Long authorId;
    private String name;
    private List<Long> booksId;

}
