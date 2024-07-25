package com.example.rest.requests;

import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetUsersRequest {

    private Response response;

    public Response send() {
        if (response == null){
            response = given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/users");
        }
        return response;
    }

    public List<String> getUsersData() {
        if (response == null){
            this.send();
        }
        return response
                .jsonPath()
                .getList(".", String.class);
    }

}
