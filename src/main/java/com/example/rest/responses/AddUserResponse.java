package com.example.rest.responses;

import io.restassured.internal.RestAssuredResponseImpl;

public class AddUserResponse extends RestAssuredResponseImpl {

    public String getId(){
        return this.jsonPath().getString("id");
    }
}
