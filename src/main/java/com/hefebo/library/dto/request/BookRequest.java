package com.hefebo.library.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {
    @NotBlank(message = "Il titolo del libro è obbligatorio")
    @Size(min=2, max = 20, message = "Il titolo del libro deve essere compreso tra 2 e 20 caratteri")
    private String title;

    @Min(1)
    @NotNull(message = "I'l ID dell'autore non puo essere nullo")
    private Long authorId;
}
