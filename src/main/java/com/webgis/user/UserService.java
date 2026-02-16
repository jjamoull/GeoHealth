package com.webgis.user;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;
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
     * Registers a user to the db
     *
     * @param username username of the new user
     * @param firstName firstName of the new user
     * @param lastName lastName of the new user
     * @param email email of the new user
     * @param password password of the new user
     * @return Saved user
     */
    public User register(String username, String firstName, String lastName, String email, String password, String role){

        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        final String hashedPassword = passwordEncoder.encode(password);

        final User user = new User(username, firstName, lastName, email, hashedPassword, role);

        return userRepository.save(user);
    }


    public User login(String username, String password) {
        if (findByUsername(username).isEmpty()) {
            throw new IllegalArgumentException("Username does not exist");
        }
        final User user = findByUsername(username).get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }


    /**
     * Update user which identifier equals id
     *
     * @param currentUsername current username of the user you want to update
     * @param newUsername New username of the user
     * @param firstName New firstName of the user
     * @param lastName New lastName of the user
     * @param email New email of the user
     * @return Updated user
     */
    public User updateUserInfo(String currentUsername, String newUsername, String firstName, String lastName, String email){

        Optional<User> optionalUser = findByUsername(currentUsername);
        if (optionalUser.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }
        User user = optionalUser.get();

        if (!newUsername.equals(currentUsername) && findByUsername(newUsername).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (!email.equals(user.getEmail()) && findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setUsername(newUsername);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

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

    /**
     * Check if the user is admin
     *
     * @param id The id of the user you want to check if is an admin
     * @return true if the user is admin, false otherwise
    * */
    public Boolean isAdmin(long id){
        if (findById(id).isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }
        final User user = findById(id).get();
        final String userRole = user.getRole();
        return userRole.equals("Admin");
    }
}