package com.webgis.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndDeletedFalse(String username);
    Optional<User> findByEmailAndDeletedFalse(String email);
    Optional<User> findByIdAndDeletedFalse(Long id);
    List<User> findAllByDeletedFalse();
}