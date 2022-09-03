package site.nomoreparties.stellarburgers.clients;

import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.models.User;

public class UserClient extends RestAssuredClient {

    private final String AUTH = "/auth";
    private final String CREATE_USER_URL = AUTH + "/register";
    private final String LOGIN_USER_URL = AUTH + "/login";
    private final String LOGOUT_USER_URL = AUTH + "/logout";
    //private final String DELETE_USER_URL = COURIER + "/{courierId}";


    public Response createUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(CREATE_USER_URL);
    }

    public Response loginUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(LOGIN_USER_URL);
    }

    /*public Response deleteUser(User user){
        return reqSpec
                .when()
                .delete(DELETE_USER_URL);
    }*/
}
