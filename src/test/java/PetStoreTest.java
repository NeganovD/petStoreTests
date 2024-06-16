import org.example.PetStoreClient;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetStoreTest {

    int expectedOrderId = 100;

    @Test
    @Tag("Positive")
    @Order(3)
    public void getInventoryTest() {
        PetStoreClient.getInventory()
                .then()
                .statusCode(200);
    }

    @Test
    @Tag("Positive")
    @Order(1)
    public void postOrderPositiveTest() {
        PetStoreClient.postOrder(Map.of("id", expectedOrderId,
                        "petId", 1,
                        "quantity", 1,
                        "shipDate", "2024-06-15T14:30:00.000Z",
                        "status", "placed",
                        "complete", true))
                .then()
                .statusCode(200);
    }

    @Test
    @Tag("Negative")
    @Order(2)
    public void postOrderNegativeTest() {
        PetStoreClient.postOrder(Map.of("id", 1,
                        "petId", "aa",
                        "quantity", 1,
                        "shipDate", "2024-06-15T14:30:00.000Z",
                        "status", "placed"))
                .then()
                .statusCode(500);
    }

    @Test
    @Tag("Positive")
    @Order(4)
    public void getOrderByIdPositiveTest() {
        PetStoreClient.getOrderById(expectedOrderId) //При условии выполнения метода postOrder с id = 1
                .then()
                .statusCode(200)
                .body("id", equalTo(expectedOrderId))
                .body("petId", notNullValue())
                .body("quantity", notNullValue())
                .body("shipDate", notNullValue())
                .body("status", notNullValue())
                .body("complete", notNullValue());
    }

    @Test
    @Order(5)
    @Tag("Negative")
    public void getOrderByIdNegativeTestNotFound() {
        PetStoreClient.getOrderById(1111)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    @Tag("Negative")
    public void getOrderByIdNegativeTestInvalidId() {
        PetStoreClient.getOrderById("invalid")
                .then()
                .statusCode(404);
    }


    @Test
    @Order(7)
    @Tag("Positive")
    public void deleteOrderByIdPositive() {
        PetStoreClient.deleteOrderByID(expectedOrderId)  //При условии выполнения метода postOrder с id = 1
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", notNullValue())
                .body("message", notNullValue());
    }

    @Test
    @Order(8)
    @Tag("Negative")
    public void deleteOrderByIdNotFoundNegative() {
        PetStoreClient.deleteOrderByID(expectedOrderId + Math.random())
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("type", notNullValue())
                .body("message", notNullValue());
    }
}

