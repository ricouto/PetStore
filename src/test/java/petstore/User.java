package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class User {

    String postUser = "user";
    String uri = "https://petstore.swagger.io/v2";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
    public void incluirUsuario() throws IOException {

        String jsonBody = lerJson("db/user1.json");

        String messageUser = given()
                .contentType("application/json")
                .body(jsonBody)
                //.log().all()
        .when()
                .post(uri + "/" + postUser)
        .then()
                .log().all()
                .statusCode(200)
                .body("type", is("unknown"))
                .body("message", is("2005159160"))
                .extract().path("message").toString()
        ;
        System.out.println("\nO id do user eh : " + messageUser);
    }

}
