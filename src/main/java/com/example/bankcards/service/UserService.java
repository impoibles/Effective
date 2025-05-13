package com.example.bankcards.service;


import com.example.bankcards.entity.User;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepo.save(user);
    }

    // Read (by ID)
    @Transactional()
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // Read (all)
    @Transactional()
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Update
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setFirst_name(updatedUser.getFirst_name());
        existingUser.setSecond_name(updatedUser.getSecond_name());
        existingUser.setMiddle_name(updatedUser.getMiddle_name());

        // Обновление роли (если нужно)
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        // Обновление карт (удаление старых и добавление новых)
        existingUser.getCards().clear();
        existingUser.getCards().addAll(updatedUser.getCards());

        return userRepo.save(existingUser);
    }

    // Delete
    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
