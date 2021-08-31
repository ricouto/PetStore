package petstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Store {
    Data data;
    String orderId;
    String getStore = "store/inventory";
    String verboGetPostDelete = "store/order";
    String uri = "https://petstore.swagger.io/v2";

    @BeforeMethod
    public void setUp(){
        data = new Data();
    }

    @Test(priority = 1)
    public void test1_consultarStoreInventory(){

        given()
                .contentType("application/json")
                //.log().all()
        .when()
                .get(uri + "/" + getStore)
        .then()
                .log().all()
                .statusCode(200)
                //.body("id", equalTo(petId02))
                //.body("sold", is(2))
        ;
    }

    @Test(priority = 2)
    public void test2_incluirStoreOrder() throws IOException {
        String jsonBody = data.lerJson("db/store1.json");

        orderId = given()
                .contentType("application/json")
                .body(jsonBody)
        .when()
                .post(uri + "/" + verboGetPostDelete)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(9))
                .body("petId", is(7))
                .body("quantity", is(1))
                .extract().path("id").toString()
        ;
    }

    @Test(priority = 3)
    public void test3_consultarStoreOrderID(){

        given()
                .contentType("application/json")
                //.log().all()
        .when()
                .get(uri + "/" + verboGetPostDelete+ "/" + orderId)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(9))
                .body("petId", is(7))
                .body("quantity", is(1))
        ;
    }

    @Test(priority = 4)
    public void test4_deleteOrderId() throws IOException {

        given()
                .contentType("application/json")
        .when()
                .delete(uri + "/" + verboGetPostDelete+ "/" + orderId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(orderId))
        ;
    }

}
