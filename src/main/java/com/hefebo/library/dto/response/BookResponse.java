package com.hefebo.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookResponse {
    private Long bookId;
    private String name;
    private Long authorId;

}
