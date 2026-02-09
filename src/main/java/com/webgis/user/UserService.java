package com.webgis.user;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(String username, String firstName, String lastName, String email, String password){

        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User(username, firstName, lastName, email, hashedPassword);

        return userRepository.save(user);
    }
    public User updateUser(int Id, String username, String firstName, String lastName, String email, String password){
        if (findById(Id).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = findById(Id).get();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public void deleteUser(int Id){
        if (findById(Id).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        User user = findById(Id).get();
        userRepository.delete(user);
    }


}