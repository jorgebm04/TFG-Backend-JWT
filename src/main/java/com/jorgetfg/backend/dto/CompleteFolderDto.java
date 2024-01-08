package com.jorgetfg.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jorgetfg.backend.Entity.Folder;
import com.jorgetfg.backend.Entity.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompleteFolderDto {
    private Long id;
    private String name;
    private List<Folder> subfolders;
    private List<Subscription> subscriptions;
}
