package com.marcykiewicz.mateusz.joba_application_portal.service;

import com.marcykiewicz.mateusz.joba_application_portal.entity.User;
import com.marcykiewicz.mateusz.joba_application_portal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void addUser(User user) {
        user.setActive(true);

        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String validatePasswords(String password, String passwordRepeat) {
        String errorMessage = null;
        if (!(passwordRepeat != null && passwordRepeat.equals(password))) {
            errorMessage = "Given passwords are different";
        }
        return errorMessage;
    }
}
