package com.webgis.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRespositoryTest {

    @Autowired
    private UserRepository userRepository;


    private void assertUserEquals(User actual, User expected) {
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getRole()).isEqualTo(expected.getRole());
    }


    @Test
    void testSaveAndFindByUsernameAndDeletedFalse() {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");

        //Act
        userRepository.save(user);
        Optional<User> found = userRepository.findByUsernameAndDeletedFalse(user.getUsername());

        // Assert
        assertThat(found).isPresent();
        assertUserEquals(found.get(),user);
    }

    @Test
    void testDeletedUserNotFoundByUsername() {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");
        user.setDeleted(true);

        //Act
        userRepository.save(user);
        Optional<User> found = userRepository.findByUsernameAndDeletedFalse(user.getUsername());

        //Assert
        assertThat(found).isEmpty();
    }

    @Test
    void testSaveAndFindByEmailAndDeletedFalse() {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");

        //Act
        userRepository.save(user);
        Optional<User> found = userRepository.findByEmailAndDeletedFalse(user.getEmail());

        //Assert
        assertThat(found).isPresent();
        assertUserEquals(found.get(),user);
    }

    @Test
    void testDeletedUserNotFoundByEmail() {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");
        user.setDeleted(true);

        //Act
        userRepository.save(user);
        Optional<User> found = userRepository.findByEmailAndDeletedFalse(user.getEmail());

        //Assert
        assertThat(found).isEmpty();
    }

    @Test
    void testSaveAndFindByIdAndDeletedFalse() {
        //Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");

        //Act
        userRepository.save(user);
        Optional<User> found = userRepository.findByIdAndDeletedFalse(user.getId());

        // Assert
        assertThat(found).isPresent();
        assertUserEquals(found.get(),user);
    }

    @Test
    void testSaveAndFindByDeletedFalseOneDeleteUser() {
        //Arrange
        User firstUser = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "ADMIN");

        User secondUser = new User(
                "tomibout",
                "Thomas",
                "Boutton",
                "thomas.boutton@outlook.be",
                "private",
                "User");
        secondUser.setDeleted(true);

        //Act
        userRepository.save(firstUser);
        userRepository.save(secondUser);
        List<User> userList = userRepository.findAllByDeletedFalse();

        // Assert
        assertThat(userList.size()).isEqualTo(1);
        assertUserEquals(userList.get(0),firstUser);
    }




}
