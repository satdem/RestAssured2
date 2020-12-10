package basqar;

import basqar.model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {

    Cookies cookies;
    private String randomGenName;
    private String randomGenCode;
    String id;

    @BeforeClass
    public void login()
    {
        baseURI ="https://test.basqar.techno.study";

        randomGenName = RandomStringUtils.randomAlphabetic(8);
        randomGenCode = RandomStringUtils.randomAlphabetic(4);

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
        Country country=new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);

        id=  given()
                  .cookies(cookies)  // aldığımız yetki bilgilerini barındıran bilgileri tekrar göndererek yetkili işlem yaptığımızı belirttik.
                  .body(country) // JSON formatında vermek yerine NESNE olarak daha kolay formatta verdim.
                  .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                  .when()
                  .post("/school-service/api/countries")
                  .then()
                  .log().body()
                  .statusCode(201)
                  .body("name", equalTo(randomGenName))
                  .extract().jsonPath().getString("id")
                  //.extract().path("id"); // 2.yöntem
          ;

        System.out.println(id);
    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative()
    {
        Country country=new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);

        given()
                .cookies(cookies)  // aldığımız yetki bilgilerini barındıran bilgileri tekrar göndererek yetkili işlem yaptığımızı belirttik.
                .body(country) // JSON formatında vermek yerine NESNE olarak daha kolay formatta verdim.
                .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                .when()
                .post("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("The Country with Name \""+randomGenName+"\" already exists."))
        ;
    }




}












