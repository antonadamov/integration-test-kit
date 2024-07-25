package com.example.rest.requests;

import com.example.rest.responses.AddUserResponse;

import static io.restassured.RestAssured.given;

public class AddUserRequest {

    private final String user;

    public AddUserRequest(String user) {
        this.user = user;
    }

    public AddUserResponse send() {
        return (AddUserResponse) given()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post("/users");
    }
}
