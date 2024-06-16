package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetStoreClient {
    public static final Config config = ConfigGenerate.getConfig();

    public static Response postOrder(Object body) {
        return RestAssured.given()
                .baseUri(config.getPetstoreUrl())
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .post("/store/order")
                .prettyPeek();
    }

    public static Response getInventory() {
        return RestAssured.given()
                .baseUri(config.getPetstoreUrl())
                .log().all()
                .get("/store/inventory")
                .prettyPeek();
    }

    public static Response getOrderById(Object id) {
        return RestAssured.given()
                .baseUri(config.getPetstoreUrl())
                .log().all()
                .get("/store/order/" + id)
                .prettyPeek();
    }

    public static Response deleteOrderByID(Object id) {
        return RestAssured.given()
                .baseUri(config.getPetstoreUrl())
                .log().all()
                .delete("/store/order/" + id)
                .prettyPeek();
    }
}
