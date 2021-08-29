package petstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

public class Pet {

    Data data;
    String petId;
    String pathPetStatus = "findByStatus?status=";
    String uri = "https://petstore.swagger.io/v2/pet";

    @BeforeMethod
    public void setUp(){
        data = new Data();
    }

    @Test(priority = 1)
    public void test1_incluirPet() throws IOException {
        String jsonBody = data.lerJson("db/pet1.json");

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
    public void test2_consultarPet(){

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

    @Test(priority = 4)
    public void test4_alterarPet() throws IOException {
        String jsonBody = data.lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("renatinha"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 5)
    public void test5_excluirPet(){

        given()
                .contentType("application/json")
                //.log().all()
        .when()
                .delete(uri +"/"+ petId)
        .then()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }

    @Test(priority = 3)
    public void test3_consultarPetPorStatus(){

        String tipoStatus = "available";

        given()
                .contentType("application/json")
                //.log().all()
        .when()
                .get(uri + "/" + pathPetStatus + tipoStatus)
        .then()
                .log().all()
                .statusCode(200)
                //.body("id", equalTo(petId02))
                //.body("name", containsInAnyOrder("fish"))


                .body("name[]", everyItem(equalTo("camilinha")))
        ;
    }
}