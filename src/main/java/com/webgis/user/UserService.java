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
        return userRepository.findByIdAndDeletedFalse(id);
    }

    /**
     * Search for a user in db using its username
     *
     * @param username username of the user you want to retrieve from the db
     * @return User which username equals to username, empty otherwise
     */
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsernameAndDeletedFalse(username);
    }

    /**
     * Search for a user in db using its email
     *
     * @param email email of the user you want to retrieve from the db
     * @return  User which email equals to username, empty otherwise
     */
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmailAndDeletedFalse(email);
    }

    /**
     * Retrieve all users
     *
     * @return All users in the db
     */
    public List<User> findAllUsers(){
        return userRepository.findAllByDeletedFalse();
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
     * @throws IllegalArgumentException if username already exists
     * @throws IllegalArgumentException if email already exists
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

    /**
     * Registers a user to the db
     *
     * @param username username of the user
     * @param password password of the user
     * @return Saved user
     * @throws IllegalArgumentException if username does not exist
     * @throws IllegalArgumentException if the password is wrong
     */
    public User login(String username, String password) {
        final Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Username does not exist");
        }
        final User user = optionalUser.get();
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
     * @param newFirstName New firstName of the user
     * @param newLastName New lastName of the user
     * @param newEmail New email of the user
     * @return Updated user
     * @throws IllegalArgumentException if the user associated with the username does not exist
     * @throws IllegalArgumentException if the new username already exist
     * @throws IllegalArgumentException if email already exist
     */
    public User updateUserInfo(String currentUsername, String newUsername, String newFirstName, String newLastName, String newEmail){

        final Optional<User> optionalUser = findByUsername(currentUsername);
        if (optionalUser.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }
        final User user = optionalUser.get();

        if (!newUsername.equals(currentUsername) && findByUsername(newUsername).isPresent()) {
            throw new IllegalArgumentException("Username already exist");
        }
        if (!newEmail.equals(user.getEmail()) && findByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException("Email already exist");
        }

        user.setUsername(newUsername);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setEmail(newEmail);

        return userRepository.save(user);
    }

    /**
     * Delete the user which identifier equals id
     *
     * @param username The username of the user you want to delete
     * @param password the password needed for confirmation
     * @throws IllegalArgumentException if the user associated with the username does not exist
     * @throws IllegalArgumentException if the password is wrong
     */
    public void deleteUser(String username, String password){
        final Optional<User> userOptional = findByUsername(username);

        if (userOptional.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }
        final User user = userOptional.get();

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Wrong password");
        }
        user.setDeleted(true);
        userRepository.save(user);
    }
  
     /* changes the password of a given user
     *
     * @param username The username of the user
     * @param oldPassword the password the user wants to change
     * @param newPassword the new password
     * @return the user after changing the password
     * @throws IllegalArgumentException if the user associated with the username does not exist
     * @throws IllegalArgumentException if the old password is wrong
     * */
    public User changePassword(String username, String oldPassword, String newPassword){
        final Optional<User> useroptional = findByUsername(username);
        if (useroptional.isEmpty()){
            throw new IllegalArgumentException("User does not exist");
        }
        final User user = useroptional.get();
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new IllegalArgumentException("wrong old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * Bans a user by username
     *
     * @param username the username of the user to ban
     * @throws IllegalArgumentException if user does not exist
     * @throws IllegalArgumentException if user is already banned
     */
    public User banUser(String username) {
        final Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new IllegalArgumentException("Username does not exist");
        }
        User user = optionalUser.get();
        if (user.isBanned()){
            throw new IllegalArgumentException("User is already banned");
        }
        user.setBanned(true);
        return userRepository.save(user);
    }

    /**
     * Unbans a user by username
     *
     * @param username the username of the user to unban
     * @throws IllegalArgumentException if user does not exist
     * @throws IllegalArgumentException if user is not banned
     */
    public User unbanUser(String username){
        final Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isEmpty()){
            throw new IllegalArgumentException("Username does not exist");
        }
        User user = optionalUser.get();
        if (!user.isBanned()){
            throw new IllegalArgumentException("User is not banned");
        }
        user.setBanned(false);
        return userRepository.save(user);
    }

    /**
     * Check if the user is admin
     *
     * @param username The username of the user
     * @return true if the user is admin, false otherwise
     * @throws IllegalArgumentException if the user associated with the username does not exist
     * */
    public Boolean isAdmin(String username){
        final Optional<User> optionalUser = findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Username does not exist");
        }
        final User user = optionalUser.get();
        final String userRole = user.getRole();
        return userRole.equals("Admin");
    }
}