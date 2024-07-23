package com.example.db;

import com.example.exception.AsyncTestingFrameworkException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MongoDBService {

    private final MongoClient mongoClient;


    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Autowired
    public MongoDBService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Document getDocument(String collectionName, String documentId) {
        try {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            return collection.find(new Document("_id", documentId)).first();
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("An exception occurred while getting a document from DB", e);
        }
    }


}
