package site.nomoreparties.stellarburgers.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.models.Order;

public class OrderClient extends RestAssuredClient {

    private final String ORDERS = "/orders";

    @Step("Get orders")
    public Response getOrders(String token) {
        if (token.length() > 0) {
            return reqSpec
                    .auth()
                    .oauth2(token)
                    .when()
                    .get(ORDERS);
        }
        else {
            return reqSpec
                    .when()
                    .get(ORDERS);
        }
    }

    @Step("Create order")
    public Response createOrder(Order order, String token) {
        if (token.length() > 0) {
            return reqSpec
                    .auth()
                    .oauth2(token)
                    .body(order)
                    .when()
                    .post(ORDERS);
        }
        else {
            return reqSpec
                    .body(order)
                    .when()
                    .post(ORDERS);
        }
    }
}
