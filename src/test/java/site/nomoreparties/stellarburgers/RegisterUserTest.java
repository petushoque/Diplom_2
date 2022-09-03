package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.clients.UserClient;
import site.nomoreparties.stellarburgers.models.User;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.http.HttpStatus.*;

@Data
public class RegisterUserTest {

    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void deleteCourier(){
        if((user.getName() != "")&(user.getPassword() != "")&(user.getEmail() != "")) {
            userClient.deleteUser();
            System.out.println("The created user was deleted after the test");
        }
    }

    @Test
    @DisplayName("Check status code and body of /auth/register: Correct data")
    @Description("A test for a positive scenario, a successful server response is 200, " +
            "the response body contains success: true, accessToken and refreshToken")
    public void createNewUserTest() {
        user = new User();
        user.setRandom();
        boolean isCreated = userClient.createUser(user)
                .then()
                .log()
                .all()
                .statusCode(SC_OK)
                .body("refreshToken", notNullValue())
                .body("accessToken", notNullValue())
                .extract()
                .path("success");
        Assert.assertTrue(isCreated);
        System.out.println("The new user has been successfully registered");
    }

    @Test
    @DisplayName("Check status code and error message of /auth/register: Creating an existing user")
    @Description("A test for a negative scenario, for a request with existing data, the system responds with a 403 code and an error message")
    public void userAlreadyExistsTest(){
        User existedUser = new User();
        existedUser.setRandom();
        userClient.createUser(existedUser);
        user = existedUser;
        boolean isCreated = userClient.createUser(user)
                .then()
                .log()
                .all()
                .statusCode(SC_FORBIDDEN)
                .assertThat()
                .body("message", equalTo("User already exists"))
                .extract()
                .path("success");
        Assert.assertFalse(isCreated);
        System.out.println("The system did not allow to create a user with already existing credentials");
    }

    @Test
    @DisplayName("Check status code and error message of /user/register: Empty email")
    @Description("A test for a negative scenario, for a request with empty email, the system responds with a 403 code and an error message")
    public void createNewUserWithoutEmailTest(){
        user = new User();
        user.setRandom();
        user.setEmail("");
        boolean isCreated = userClient.createUser(user)
                .then()
                .log()
                .all()
                .statusCode(SC_FORBIDDEN)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
        Assert.assertFalse(isCreated);
        System.out.println("The system did not allow to create a user with empty email");
    }

    @Test
    @DisplayName("Check status code and error message of /user/register: Empty name")
    @Description("A test for a negative scenario, for a request with empty name, the system responds with a 403 code and an error message")
    public void createNewUserWithoutNameTest(){
        user = new User();
        user.setRandom();
        user.setName("");
        boolean isCreated = userClient.createUser(user)
                .then()
                .log()
                .all()
                .statusCode(SC_FORBIDDEN)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
        Assert.assertFalse(isCreated);
        System.out.println("The system did not allow to create a user with empty name");
    }

    @Test
    @DisplayName("Check status code and error message of /user/register: Empty password")
    @Description("A test for a negative scenario, for a request with empty password, the system responds with a 403 code and an error message")
    public void createNewUserWithoutPasswordTest(){
        user = new User();
        user.setRandom();
        user.setPassword("");
        boolean isCreated = userClient.createUser(user)
                .then()
                .log()
                .all()
                .statusCode(SC_FORBIDDEN)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
        Assert.assertFalse(isCreated);
        System.out.println("The system did not allow to create a user with empty password");
    }
}
