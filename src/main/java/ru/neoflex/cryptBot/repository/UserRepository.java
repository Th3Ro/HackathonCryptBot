package ru.neoflex.cryptBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.cryptBot.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    List<User> findAll();
}
