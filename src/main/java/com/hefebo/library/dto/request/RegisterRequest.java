package com.hefebo.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "L'email é obbligatorio")
    @Email(message = "L'email non ha un formato valido")
    private String email;

    @NotBlank(message = "Il password é obbligatorio")
    @Size(min=8, message = "Il password deve contenere almeno 8 caratteri")
    private String password;

}
