package petstore;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class Store {

    String getStore = "store/inventory";
    String uri = "https://petstore.swagger.io/v2";

    @Test(priority = 1)
    public void consultarStore(){

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


}
