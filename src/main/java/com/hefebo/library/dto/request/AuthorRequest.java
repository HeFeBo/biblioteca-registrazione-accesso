package com.hefebo.library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequest {
    @NotBlank(message = "Il nome dell'autore è obbligatorio")
    @Size(min=2, max = 20, message = "Il nome dell'autore deve essere compreso tra 2 e 20 caratteri")
    private String name;

}
