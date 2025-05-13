package com.example.bankcards.repository;


import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<User, Long> {
    public void createUser(User user);
}
