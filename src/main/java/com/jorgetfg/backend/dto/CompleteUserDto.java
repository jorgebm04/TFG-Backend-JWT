package com.jorgetfg.backend.dto;

import com.jorgetfg.backend.entities.Folder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompleteUserDto {
    private String name;
    private String surname;
    private String email;
    private boolean messages;
    private List<Folder> folders;
}
