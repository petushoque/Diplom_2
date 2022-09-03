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
    public void deleteCourier() {
        userClient.deleteUser();
        System.out.println("The created user was deleted after the test");
    }

    @Test
    @DisplayName("Check status code and body of /user: The user is authorized")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void editUserWithAuthTest(){
        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
        User editedUser = user;
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
}
