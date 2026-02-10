package com.webgis.user;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Create a new user service instance
     *
     * @param userRepository Jpa repository linked to the database
     * @param passwordEncoder Tool used to cypher the password
     */
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Search for a user in db using its identifier
     *
     * @param id identifier of the user you want to retrieve from the db
     * @return User which identifier equals to id, empty otherwise
     */
    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }

    /**
     * Search for a user in db using its username
     *
     * @param username username of the user you want to retrieve from the db
     * @return User which username equals to username, empty otherwise
     */
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Search for a user in db using its email
     *
     * @param email email of the user you want to retrieve from the db
     * @return  User which email equals to username, empty otherwise
     */
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieve all users
     *
     * @return All users in the db
     */
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Add a user to the db
     *
     * @param username username of the new user
     * @param firstName firstName of the new user
     * @param lastName lastName of the new user
     * @param email email of the new user
     * @param password password of the new user
     * @return Saved user
     */
    public User saveUser(String username, String firstName, String lastName, String email, String password){

        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        final String hashedPassword = passwordEncoder.encode(password);

        final User user = new User(username, firstName, lastName, email, hashedPassword);

        return userRepository.save(user);
    }

    /**
     * Update user which identifier equals id
     *
     * @param id id of the user you want to update
     * @param username New username of the user
     * @param firstName New firstName of the user
     * @param lastName New lastName of the user
     * @param email New email of the user
     * @param password New password of the user
     * @return Updated user
     */
    public User updateUser(long id, String username, String firstName, String lastName, String email, String password){
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        final User user = findById(id).get();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        final String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    /**
     * Delete the user which identifier equals id
     *
     * @param id The id of the user you want to delete
     */
    public void deleteUser(long id){
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        final User user = findById(id).get();
        userRepository.delete(user);
    }


}