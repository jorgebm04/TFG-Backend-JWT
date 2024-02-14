package com.jorgetfg.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto {
    @NotNull
    private String name;
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private char[] password;
    @NotNull
    private Boolean messages;
}
