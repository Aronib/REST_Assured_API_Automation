import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class booking {
    Properties prop=new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String token;
    public void callingLoginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                given()
                        .contentType("application/json")
                        .body(
                                "{\"username\":\"admin\",\n" +
                                        "    \"password\":\"password123\"}"
                        ).
                        when()
                        .post("/auth").
                        then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath jsonpath = res.jsonPath();
        token = jsonpath.get("token");
        Utils.setEnvVariable("token", token);
    }
    public void bookingList() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                        when()
                        .get("/booking").
                        then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("bookingid[0]").toString(),"101");
        System.out.println(res.asString());
    }

    public void createBooking() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                        body(
                                "{\n" +
                                        "    \"firstname\": \"Aroniiii\",\n" +
                                        "    \"lastname\": \"R\",\n" +
                                        "    \"totalprice\": 900,\n" +
                                        "    \"depositpaid\": true,\n" +
                                        "    \"bookingdates\": {\n" +
                                        "        \"checkin\": \"2023-03-11\",\n" +
                                        "        \"checkout\": \"2023-03-13\"\n" +
                                        "    }\n" +
                                        "}"
                        ).
                        when()
                        .post("/booking").
                        then()
                        .assertThat().statusCode( 200 ).extract().response();

        JsonPath response = res.jsonPath();
        //
    }
    public void deleteBooking() throws IOException {
        prop.load(file);
        RestAssured.baseURI  = prop.getProperty("baseUrl");
        Response res =
                (Response) given()
                        .contentType("application/json").header("Authorization",prop.getProperty("token")).
                        when()
                        .delete("/booking/4").
                        then()
                        .assertThat().statusCode( 403 ).extract().response();

        //API doesn't support deleting any entry, hence asserted Status Code 403
    }
}
