package basqar;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {

    Cookies cookies;

    @BeforeClass
    public void login()
    {
        baseURI ="https://test.basqar.techno.study";

        // {"username": "daulet2030@gmail.com", "password": "TechnoStudy123@", "rememberMe": true}

        Map<String, String> credentials= new HashMap<>();
        credentials.put("username", "daulet2030@gmail.com");
        credentials.put("password", "TechnoStudy123@");
        credentials.put("rememberMe", "true");

        cookies= given()
                .body(credentials) // json ı direk yazmak yerine map olarak da verebiliriz.
                .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().getDetailedCookies();
                ;

        System.out.println(cookies);
    }


    @Test
    public void createCountry()
    {

    }

}
