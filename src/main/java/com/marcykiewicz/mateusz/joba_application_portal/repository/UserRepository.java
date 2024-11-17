package com.marcykiewicz.mateusz.joba_application_portal.repository;

import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@NotNull(message = "Email is required") String email);
}
