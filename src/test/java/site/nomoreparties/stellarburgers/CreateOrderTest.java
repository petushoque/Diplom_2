package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.clients.OrderClient;
import site.nomoreparties.stellarburgers.clients.UserClient;
import site.nomoreparties.stellarburgers.models.Order;
import site.nomoreparties.stellarburgers.models.User;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

@Data
public class CreateOrderTest {

    private Order order;
    private OrderClient orderClient;
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Check status code and body of /orders: the user is logged in, the list of ingredients is correct, the order is created")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void createOrderWithAuthAndCorrectIngredientsTest(){
        user = new User();
        user.setRandom();
        userClient.createUser(user);
        System.out.println("The user for the test is registered");

        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
        System.out.println("The user for the test is logged in");

        Order order = new Order();
        order.setCorrectIngredientsList();

        boolean isCreated = orderClient.createOrder(order, bearerToken)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isCreated);
        System.out.println("A new order has been successfully created");

        userClient.deleteUser();
        System.out.println("The created user was deleted after the test");
    }

    @Test
    @DisplayName("Check status code and body of /orders: the user is not logged in, the list of ingredients is correct, the order is created")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void createOrderWithoutAuthAndCorrectIngredientsTest(){
        Order order = new Order();
        order.setCorrectIngredientsList();

        boolean isCreated = orderClient.createOrder(order, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isCreated);
        System.out.println("A new order has been successfully created");
    }

    @Test
    @DisplayName("Check status code and body of /orders: the user is not logged in, the list of ingredients is empty, the order is not created")
    @Description("A test for a negative scenario, for a request with an empty ingredients list, the system responds with a 400 code and an error message")
    public void createOrderWithoutAuthAndEmptyIngredientsListTest(){
        Order order = new Order();
        order.setEmptyIngredientsList();
        boolean isCreated = orderClient.createOrder(order, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .extract()
                .path("success");
        Assert.assertFalse(isCreated);
        System.out.println("The system gave an error, the order was not created");
    }

    @Test
    @DisplayName("Check status code of /orders: the user is not logged in, the list of ingredients is incorrect, the order is not created")
    @Description("A test for a negative scenario, for a request with an incorrect ingredients list, the system responds with a 500 code")
    public void createOrderWithoutAuthAndIncorrectIngredientsListTest(){
        Order order = new Order();
        order.setIncorrectIngredientsList();
        orderClient.createOrder(order, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
        System.out.println("The system gave an error, the order was not created");
    }
}
