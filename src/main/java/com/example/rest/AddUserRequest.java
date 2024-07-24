package com.example.rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AddUserRequest {

    private String user;

    public AddUserRequest(String user) {
        this.user = user;
    }

    public Response send() {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/users");
    }
}
