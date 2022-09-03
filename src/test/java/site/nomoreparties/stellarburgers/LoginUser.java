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
public class LoginUser {

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
    public void deleteCourier() {
        userClient.deleteUser();
        System.out.println("The created user was deleted after the test");
    }

    @Test
    @DisplayName("Check status code and body of /user/login: Correct data")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void loginUserWithCorrectDataTest(){
        boolean isLoggedIn = userClient.loginUser(user)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isLoggedIn);
        System.out.println("The user is successfully logged in to the system");
    }
}
