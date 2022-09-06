package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.clients.OrderClient;
import site.nomoreparties.stellarburgers.clients.RestAssuredClient;
import site.nomoreparties.stellarburgers.clients.UserClient;
import site.nomoreparties.stellarburgers.models.Order;
import site.nomoreparties.stellarburgers.models.User;
import static org.hamcrest.CoreMatchers.*;
import static org.apache.http.HttpStatus.*;

@Data
public class CreateOrderTest {

    private Order order;
    private OrderClient orderClient;
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
    //    user = new User();
    //    user.setRandom();
    //    userClient = new UserClient();
    //    userClient.createUser(user);
        orderClient = new OrderClient();
    //    System.out.println("The user for the test is registered");
    }

    @Test
    @DisplayName("///")
    @Description("///")
    public void createOrderWithAuthAndCorrectIngredientsTest(){
        String bearerToken = userClient.loginUser(user)
                .then()
                .extract()
                .path("accessToken");
        bearerToken = bearerToken.split(" ")[1];
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
        System.out.println("///");
    }

    @Test
    @DisplayName("///")
    @Description("///")
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
        System.out.println("///");
    }

    @Test
    @DisplayName("///")
    @Description("///")
    public void createOrderWithoutAuthAndEmptyIngredientsListTest(){
        Order order = new Order();
        order.setEmptyIngredientsList();
        boolean isCreated = orderClient.createOrder(order, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isCreated);
        System.out.println("///");
    }

    @Test
    @DisplayName("///")
    @Description("///")
    public void createOrderWithoutAuthAndIncorrectIngredientsListTest(){
        Order order = new Order();
        order.setIncorrectIngredientsList();
        boolean isCreated = orderClient.createOrder(order, "")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("success");
        Assert.assertTrue(isCreated);
        System.out.println("///");
    }
}
