package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class Pet {
    String petId;
    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");
        //petId =
        petId = given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("camilinha") )
                .body("status", is("available"))
                .body("category.name", is("cachorro"))
                .body("tags.name", contains(is("Semana do teste - Agosto")))
                .extract().path("id").toString()
        ;

        System.out.println(petId);


    }

    @Test(priority = 2)
    public void consultarPet(){
        //String petId = "828385942006";
        //Integer petId02 = Integer.parseInt(petId);

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                //.body("id", equalTo(petId02))
                .body("name", is("camilinha"))
        ;

    }

    @Test(priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("camilinha"))
                .body("status", is("sold"))
        ;

    }

    @Test(priority = 4)
    public void excluirPet(){

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri +"/"+ petId)
        .then()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }
}
