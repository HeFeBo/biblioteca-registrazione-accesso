package com.hefebo.library.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "L'email é obbligatorio")
    @Email(message = "L'email non ha un formato valido")
    private String email;

    @NotBlank(message = "Il password é obbligatorio")
    private String password;

}
