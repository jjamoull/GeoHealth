package unitest.usertest;

import com.webgis.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {

    public UserEntityTest(){}

    @Test
    public void GetterTest(){
        // Arrange
        User user = new User("pseudo","Julien","Jamal", "julien.jamal@outlook.be","password", "Admin");

        //Act
        assertEquals("pseudo", user.getUsername());
        assertEquals("Julien", user.getFirstName());
        assertEquals("Jamal", user.getLastName());
        assertEquals("julien.jamal@outlook.be", user.getEmail());
        assertEquals("password", user.getPassword());
    }
    @Test
    public void SetterTest(){
        //Arrange
        User user = new User();

        //Act
        user.setUsername("pseudo");
        user.setFirstName("Julien");
        user.setLastName("Jamal");
        user.setEmail("julien.jamal@outlook.be");
        user.setPassword("password");
        user.setRole("Admin");

        //Assert
        assertEquals("pseudo", user.getUsername());
        assertEquals("Julien", user.getFirstName());
        assertEquals("Jamal", user.getLastName());
        assertEquals("julien.jamal@outlook.be", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("Admin", user.getRole());

    }


}
