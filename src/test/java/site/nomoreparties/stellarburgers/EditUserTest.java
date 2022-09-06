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
public class EditUserTest {

    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        user = new User();
        user.setRandom();
        userClient = new UserClient();
        userClient.createUser(user);
        System.out.println("The user for the test is registered");
    }

    @After
    public void deleteUser() {
        userClient.deleteUser();
        System.out.println("The created user was deleted after the test");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is authorized, name change")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void editUserWithAuthChangeNameTest(){
        User editedUser = user;
        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
        editedUser.setName("John");
        boolean isEdited = userClient.editUser(editedUser, bearerToken)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isEdited);
        System.out.println("The user has been successfully edited");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is authorized, email change")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void editUserWithAuthChangeEmailTest(){
        User editedUser = user;
        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
        editedUser.setEmail("johnsmith@topsecret.com");
        boolean isEdited = userClient.editUser(editedUser, bearerToken)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isEdited);
        System.out.println("The user has been successfully edited");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is authorized, password change")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void editUserWithAuthChangePasswordTest(){
        User editedUser = user;
        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
        editedUser.setPassword("SeCuRiTyLeVeL100");
        boolean isEdited = userClient.editUser(editedUser, bearerToken)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isEdited);
        System.out.println("The user has been successfully edited");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is unauthorized, name change")
    @Description("A test for a negative scenario, for a request without auth token, the system responds with a 401 code and an error message")
    public void editUserWithoutAuthChangeNameTest(){
        User editedUser = user;
        editedUser.setName("John");
        boolean isEdited = userClient.editUser(editedUser, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
        Assert.assertFalse(isEdited);
        System.out.println("The system did not allow editing the user");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is unauthorized, email change")
    @Description("A test for a negative scenario, for a request without auth token, the system responds with a 401 code and an error message")
    public void editUserWithoutAuthChangeEmailTest(){
        User editedUser = user;
        editedUser.setEmail("johnsmith@topsecret.com");
        boolean isEdited = userClient.editUser(editedUser, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
        Assert.assertFalse(isEdited);
        System.out.println("The system did not allow editing the user");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is unauthorized, password change")
    @Description("A test for a negative scenario, for a request without auth token, the system responds with a 401 code and an error message")
    public void editUserWithoutAuthChangePasswordTest(){
        User editedUser = user;
        editedUser.setPassword("SeCuRiTyLeVeL100");
        boolean isEdited = userClient.editUser(editedUser, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
        Assert.assertFalse(isEdited);
        System.out.println("The system did not allow editing the user");
    }
}
