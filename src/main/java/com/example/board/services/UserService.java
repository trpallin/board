package com.example.board.services;

import com.example.board.models.User;
import com.example.board.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean createUser(User user) {
        if (userRepository.existByUsername(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean authenticateUser(
            String username,
            String password
    ) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
