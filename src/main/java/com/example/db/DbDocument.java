package com.example.db;

import io.restassured.path.json.JsonPath;
import org.bson.Document;

public class DbDocument extends Document {

    public JsonPath toJsonPath(){
        return JsonPath.from(this.toJson());
    }
}
