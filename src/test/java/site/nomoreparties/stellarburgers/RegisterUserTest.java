package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.clients.UserClient;
import site.nomoreparties.stellarburgers.models.User;

import java.util.UUID;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.http.HttpStatus.*;

@Data
public class RegisterUserTest {

    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        user = new User();
        user.setRandom();
        userClient = new UserClient();
    }

    /*@After
    public void deleteCourier(){
        if((courier.getLogin() != null)&(courier.getPassword() != null)) {
            CourierCredentials creds = CourierCredentials.from(courier);
            courierId = courierClient.loginCourier(creds)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .path("id");
            courierClient.deleteCourier(courierId);
        }
    }*/

    @Test
    @DisplayName("Check status code and body of /courier: Correct data")
    @Description("A test for a positive scenario, a successful server response is 200, " +
            "the response body contains success: true, accessToken and refreshToken")
    public void createNewUserTest(){
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

    /*
    @Test
    @DisplayName("Check status code and error message of /courier: Creating an existing courier")
    @Description("A test for a negative scenario, for a request with existing data, the system responds with a 409 code and an error message")
    public void courierAlreadyExistsTest(){
        Courier existedCourier = Courier.getRandom();
        courierClient.createCourier(existedCourier);
        courier = existedCourier;
        Response response = courierClient.createCourier(courier);
        response.then()
                .log()
                .all()
                .statusCode(SC_CONFLICT)
                .assertThat()
                .body("message", notNullValue())
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Check status code and error message of /courier: Empty login")
    @Description("A test for a negative scenario, for a request with empty login, the system responds with a 400 code and an error message")
    public void createNewCourierWithoutLoginTest(){
        courier = new Courier(null, "password1", "helloworld");
        Response response = courierClient.createCourier(courier);
        response.then()
                .log()
                .all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", notNullValue())
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and error message of /courier: Empty password")
    @Description("A test for a negative scenario, for a request with empty password, the system responds with a 400 code and an error message")
    public void createNewCourierWithoutPasswordTest(){
        courier = new Courier(UUID.randomUUID().toString(), null, "harrypotter");
        Response response = courierClient.createCourier(courier);
        response.then()
                .log()
                .all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", notNullValue())
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Check status code and error message of /courier: Empty first name")
    @Description("A test for a negative scenario, for a request with empty first name, the system responds with a 400 code and an error message")
    public void createNewCourierWithoutFirstNameTest(){
        courier = new Courier(UUID.randomUUID().toString(), "qwerty", null);
        Response response = courierClient.createCourier(courier);
        response.then()
                .log()
                .all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", notNullValue())
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }*/
}
