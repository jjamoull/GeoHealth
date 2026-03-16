package com.webgis.validationform;

import com.webgis.user.User;
import com.webgis.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class ValidationFormRepositoryTest {

    @Autowired
    private ValidationFormRepository validationFormRepository;

    @Autowired
    private UserRepository userRepository;

    private void assertValidationFormEquals(ValidationForm actual, ValidationForm expected) {
        assertThat(actual.getDepartment()).isEqualTo(expected.getDepartment());
        assertThat(actual.getAgreementLevel()).isEqualTo(expected.getAgreementLevel());
        assertThat(actual.getPerceivedRisk()).isEqualTo(expected.getPerceivedRisk());
        assertThat(actual.getCertaintyLevel()).isEqualTo(expected.getCertaintyLevel());
        assertThat(actual.getComment()).isEqualTo(expected.getComment());
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
        assertThat(actual.isPublic()).isEqualTo(expected.isPublic());
    }

    @Test
    void saveAndFindByIdTest(){
        // Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        ValidationForm validationForm=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                true
        );

        //Act
        userRepository.save(user);
        validationFormRepository.save(validationForm);
        Optional<ValidationForm> found= validationFormRepository.findById(validationForm.getId());

        //Assert
        assertThat(found).isPresent();
        assertValidationFormEquals(found.get(),validationForm);
    }

    @Test
    void saveAndFindByUserAndByDepartmentTest(){
        // Arrange
        User user1 = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        User user2 = new User(
                "jdp",
                "Jean",
                "Dupont",
                "jean.dupont@test.com",
                "password2",
                "User"
        );


        ValidationForm validationForm1=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user1,
                true
        );

        ValidationForm validationForm2=new ValidationForm(
                "Wouri",
                3,
                "medium",
                4,
                "comment",
                user2,
                false
        );

        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        validationFormRepository.save(validationForm1);
        validationFormRepository.save(validationForm2);
        Optional<ValidationForm> found= validationFormRepository.findByUserAndDepartment(user1,"Wouri");

        //Assert
        assertThat(found).isPresent();
        assertValidationFormEquals(found.get(),validationForm1);

    }

    @Test
    void findByDepartmentMutipleFormTest(){
        //Arrange
        User user1 = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        User user2 = new User(
                "jdp",
                "Jean",
                "Dupont",
                "jean.dupont@test.com",
                "password2",
                "User"
        );


        ValidationForm validationForm1=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user1,
                true
        );

        ValidationForm validationForm2=new ValidationForm(
                "Wouri",
                3,
                "medium",
                4,
                "comment",
                user2,
                false
        );

        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        validationFormRepository.save(validationForm1);
        validationFormRepository.save(validationForm2);
        List<ValidationForm> found= validationFormRepository.findByDepartment("Wouri");

        //Assert
        assertThat(found).hasSize(2);
        assertValidationFormEquals(found.get(0),validationForm1);
        assertValidationFormEquals(found.get(1),validationForm2);

    }

    @Test
    void existsByUserAndDepartmentNotExistTest(){

        // Arrange
        User user = new User(
                "pseudo",
                "Julien",
                "Jamal",
                "julien.jamal@outlook.be",
                "password",
                "Admin"
        );

        ValidationForm validationForm=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                true
        );

        //Act
        userRepository.save(user);
        validationFormRepository.save(validationForm);
        boolean exist= validationFormRepository.existsByUserAndDepartment(user,"Mfoundi");

        //Assert
        assertFalse(exist);
    }
}
