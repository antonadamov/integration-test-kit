package com.example.rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetUserRequest {

    private String userId;

    public GetUserRequest(String userId) {
        this.userId = userId;
    }

    public Response send() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get("/users/" + userId);
    }
}
