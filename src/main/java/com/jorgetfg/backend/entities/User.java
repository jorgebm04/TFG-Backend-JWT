package com.jorgetfg.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false, length = 255)
    private String name;

    @Column(name = "user_surname", length = 255)
    private String surname;

    @Column(name = "user_email", nullable = false, length = 255)
    private String email;

    @Column(name = "user_password", nullable = false, length = 255)
    @JsonIgnore
    private String password;

    @Column(name = "user_messages", nullable = false)
    private boolean messages;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Folder> folders;
}
