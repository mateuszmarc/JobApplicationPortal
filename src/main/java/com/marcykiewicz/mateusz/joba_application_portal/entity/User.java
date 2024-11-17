package com.marcykiewicz.mateusz.joba_application_portal.entity;

import com.marcykiewicz.mateusz.joba_application_portal.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @UniqueEmail
    @NotNull(message = "Email is required")
    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "is_active")
    private boolean isActive;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)_*.{6,}", message = "Given password does not contain all needed characters")
    @Column(name = "password")
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @NotNull(message = "User type is required")
    @ManyToOne(targetEntity = UsersType.class,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            })
    @JoinColumn(name = "user_type_id")
    private UsersType usersType;


    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }
}
