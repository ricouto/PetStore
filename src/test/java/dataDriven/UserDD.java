package dataDriven;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UserDD {

        Data data;

        String postUser = "user";
        String uri = "https://petstore.swagger.io/v2";

    @BeforeMethod
    public void setUp(){
        data = new Data();
    }

    @DataProvider(name = "SearchProvider")
    public Iterator<Object[]> provider() throws IOException  {

        List<Object[]> testCases = new ArrayList<>();

        String linhaCSV;

        BufferedReader bufferedReader = new BufferedReader(new FileReader("db/user.csv"));
        while((linhaCSV = bufferedReader.readLine()) != null){
            testCases.add(linhaCSV.split(","));
        }
        return testCases.iterator();
    }

    @Test(dataProvider="SearchProvider")
    public void incluirUsuarioDataDriven(
            String id,
            String username,
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String userStatus) {

        String jsonBody = new JSONObject()
                .put("id", id)
                .put("username", username)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("email", email)
                .put("password", password)
                .put("phone", phone)
                .put("userStatus", userStatus)
                .toString();

        String messageUser =
        given()
                .contentType("application/json")
                .body(jsonBody)
                //.log().all()
        .when()
                .post(uri + "/" + postUser)
        .then()
                .log().all()
                .statusCode(200)
                .body("type", is("unknown"))
                //.body("message", is("2005159160"))
                .extract().path("message").toString()
                ;
        System.out.println("\nO id do user eh : " + messageUser);
    }
}