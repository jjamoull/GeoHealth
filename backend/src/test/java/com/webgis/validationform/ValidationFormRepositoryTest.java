package com.webgis.validationform;

import com.webgis.map.finalmap.FinalMap;
import com.webgis.map.finalmap.FinalMapRepository;
import com.webgis.user.User;
import com.webgis.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class ValidationFormRepositoryTest {

    @Autowired
    private ValidationFormRepository validationFormRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinalMapRepository finalMapRepository;

    private void assertValidationFormEquals(ValidationForm actual, ValidationForm expected) {
        assertThat(actual.getDivision()).isEqualTo(expected.getDivision());
        assertThat(actual.getAgreementLevel()).isEqualTo(expected.getAgreementLevel());
        assertThat(actual.getPerceivedRisk()).isEqualTo(expected.getPerceivedRisk());
        assertThat(actual.getCertaintyLevel()).isEqualTo(expected.getCertaintyLevel());
        assertThat(actual.getComment()).isEqualTo(expected.getComment());
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
        assertThat(actual.getFinalMap()).isEqualTo(expected.getFinalMap());
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

        byte[] dataZip ={66};
        FinalMap finalMap = new FinalMap(
                "title",
                "risk map",
                dataZip,
                "file");

        ValidationForm validationForm=new ValidationForm(
                "Wouri",
                2,
                "low",
                4,
                "comment",
                user,
                finalMap,
                true
        );

        //Act
        userRepository.save(user);
        finalMapRepository.save(finalMap);
        validationFormRepository.save(validationForm);
        Optional<ValidationForm> found= validationFormRepository.findById(validationForm.getId());

        //Assert
        assertThat(found).isPresent();
        assertValidationFormEquals(found.get(),validationForm);
    }
}
