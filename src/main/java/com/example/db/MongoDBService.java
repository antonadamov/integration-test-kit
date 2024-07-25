package com.example.db;

import com.example.exception.AsyncTestingFrameworkException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.Thread.*;

@Service
@Slf4j
public class MongoDBService {

    private final MongoDatabase database;


    @Value("${spring.mongodb.database}")
    private String dbName;

    @Value("${spring.mongodb.polling.interval:1000}")
    private long pollingInterval;

    @Value("${spring.mongodb.polling.timeout:30000}")
    private long pollingTimeout;

    @Autowired
    public MongoDBService(MongoClient mongoClient) {
        this.database = mongoClient.getDatabase(dbName);
    }

    public DbDocument getDocument(String collectionName, String documentId) throws AsyncTestingFrameworkException {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            return (DbDocument) collection.find(new Document("_id", documentId)).first();
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("An exception occurred while getting a document from DB", e);
        }
    }

    public DbDocument getDocument(String collectionName, Map<String, Object> fieldValues) throws AsyncTestingFrameworkException {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document query = new Document();
            for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                query.append(entry.getKey(), entry.getValue());
            }
            return (DbDocument) collection.find(query).first();
        } catch (Exception e) {
            throw new AsyncTestingFrameworkException("An exception occurred while getting a document from DB", e);
        }
    }

    public DbDocument waitForDocument(String collectionName, String documentId) throws AsyncTestingFrameworkException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < pollingTimeout) {
            DbDocument document = getDocument(collectionName, documentId);
            if (document != null) {
                return document;
            }
            try {
                sleep(pollingInterval);
            } catch (InterruptedException e) {
                currentThread().interrupt();
                throw new AsyncTestingFrameworkException("Polling interrupted while waiting for document", e);
            }
        }
        throw new AsyncTestingFrameworkException("Timeout while waiting for document to appear in DB");
    }

    public DbDocument waitForDocument(String collectionName, Map<String, Object> fieldValues) throws AsyncTestingFrameworkException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < pollingTimeout) {
            DbDocument document = getDocument(collectionName, fieldValues);
            if (document != null) {
                return document;
            }
            try {
                sleep(pollingInterval);
            } catch (InterruptedException e) {
                currentThread().interrupt();
                throw new AsyncTestingFrameworkException("Polling interrupted while waiting for document", e);
            }
        }
        throw new AsyncTestingFrameworkException("Timeout while waiting for document to appear in DB");
    }


}
