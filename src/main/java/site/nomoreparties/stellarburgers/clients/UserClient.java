package site.nomoreparties.stellarburgers.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.models.User;

public class UserClient extends RestAssuredClient {

    private final String AUTH = "/auth";
    private final String CREATE_USER_URL = AUTH + "/register";
    private final String LOGIN_USER_URL = AUTH + "/login";
    private final String USER_URL = AUTH + "/user";
    private final String LOGOUT_USER_URL = AUTH + "/logout";

    @Step("Create user")
    public Response createUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(CREATE_USER_URL);
    }

    @Step("Login user")
    public Response loginUser(User user) {
        return reqSpec
                .body(user)
                .when()
                .post(LOGIN_USER_URL);
    }

    @Step("Edit user")
    public Response editUser(User user, String token) {
        if (token.length() > 0) {
            return reqSpec
                    .auth()
                    .oauth2(token)
                    .body(user)
                    .when()
                    .patch(USER_URL);
        }
        else {
            return reqSpec
                    .body(user)
                    .when()
                    .patch(USER_URL);
        }

    }

    @Step("Delete user")
    public Response deleteUser(){
        return reqSpec
                .when()
                .delete(USER_URL);
    }
}
