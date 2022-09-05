package site.nomoreparties.stellarburgers.clients;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.models.Order;
import site.nomoreparties.stellarburgers.models.User;

public class OrderClient extends RestAssuredClient {

    private final String INGREDIENTS = "/ingredients";
    private final String ORDERS = "/orders";

    public Response getIngredients() {
        return reqSpec
                .when()
                .get(INGREDIENTS);
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
