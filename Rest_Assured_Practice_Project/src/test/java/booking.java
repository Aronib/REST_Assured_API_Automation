import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;

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
}
