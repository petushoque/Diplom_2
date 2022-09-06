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
public class GetOrdersTest {

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
    @DisplayName("Check status code and body of /orders/all: the user is logged in, the list of orders has been received")
    @Description("A test for a positive scenario, a successful server response is 200, the response body contains success: true")
    public void getOrdersWithAuthTest(){
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

        order = new Order();
        order.setCorrectIngredientsList();
        orderClient.createOrder(order, "");
        System.out.println("The order for the test has been created");

        boolean isOrdersReceived = orderClient.getOrders()
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isOrdersReceived);
        System.out.println("The list of orders has been received");

        userClient.deleteUser();
        System.out.println("The created user was deleted after the test");
    }
}
