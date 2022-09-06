package site.nomoreparties.stellarburgers.clients;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.models.Order;

public class OrderClient extends RestAssuredClient {

    private final String ORDERS = "/orders";
    private final String GETORDERS = ORDERS + "/all";

    public Response getOrders() {
        return reqSpec
                .when()
                .get(GETORDERS);
    }

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
