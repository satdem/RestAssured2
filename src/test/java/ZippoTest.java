import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void setup()
    {
        baseURI = "http://api.zippopotam.us";  // static REST Assured un kendi değişkeni

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.BODY)
                .build();

    }

    @Test
    public void bodyArraySizeTest_ResponseSpecification_RequestSpecification()
    {
        given()
                .spec(requestSpecification)
                .when()
                .get("/us/90210")// http ile başlamıyorsa baseURI kullanacak.

                .then()
                .body("places", hasSize(1))
                .spec(responseSpecification)
        ;
    }

    @Test
    public void bodyArraySizeTestResponseSpecification()
    {
        given()
                .log().uri()
                .when()
                .get("/us/90210")// http ile başlamıyorsa baseURI kullanacak.

                .then()
                .body("places", hasSize(1))
                .spec(responseSpecification)
        ;
    }


    @Test
    public void bodyArraySizeTestBaseUri()
    {
        given()
                .log().uri()
                .when()
                .get("/us/90210")// http ile başlamıyorsa baseURI kullanacak.

                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }


    @Test
    public void test()
    {
        given() // balagıç ayarları (setup) işlemleri
                .when()  // aksiyon kısmı
                .then(); // test kısmı
    }

    @Test
    public void statusCodeTest()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().all()  // bütün dönen sonuçları görüntüledik
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()  // sadece body kısmını görüntüledik
                .contentType(ContentType.JSON)
        ;
    }

    @Test
    public void logTest()
    {
        given()
                .log().all()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()

                ;
    }

    @Test
    public void bodyJsonPathTest()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .statusCode(200)
        ;

    }

    @Test
    public void bodyJsonPathTest2()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
        ;
    }

    @Test
    public void bodyJsonPathTest3()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                // arasında bosluk olan key lerde keyin başına ve sonuna tek tırnak konur (''place name'')
                .statusCode(200)
        ;
    }

    @Test
    public void bodyArraySizeTest()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .statusCode(200)
        ;
    }

    @Test
    public void combiningTest()
    {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;
    }

    @Test
    public void pathParamTest()
    {
        String country="us";
        String zipKod="90210";

        given()
                .pathParam("country", country)
                .pathParam("zipKod", zipKod)
                .log().uri()  // çalışmadna önce oluşturulan URL verir.

                .when()
                .get("http://api.zippopotam.us"+"/{country}/{zipKod}")

                .then()
                .log().body()
                .body("places", hasSize(1))
                ;
    }

    // https://gorest.co.in/public-api/users?page=1
    @Test
    public void queryParamTest()
    {
        int page=10;

        given()
                .param("page", page)
                .log().uri()

                .when()
                .get("https://gorest.co.in/public-api/users")

                .then()
                .log().body()
                .body("meta.pagination.page", equalTo(page))
                ;
    }


    @Test
    public void queryParamTestCoklu()
    {
        for(int page=1; page <= 10 ; page++) {
            given()
                    .param("page", page)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public-api/users")

                    .then()
                    .log().body()
                    .body("meta.pagination.page", equalTo(page))
            ;
        }
    }

    @Test
    public void extractingJsonPath()
    {
        String extractValue= given()
                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .extract().path("places[0].'place name'")
                ;

        System.out.println(extractValue);
        Assert.assertEquals(extractValue, "Beverly Hills");
    }

    @Test
    public void extractingJsonPathList()
    {
        List<String> liste=
        given()

                .when()
                .get("/tr/01000")

                .then()
                //.log().body()
                .extract().path("places.'place name'")
                // [0] bir elaman indexi verilmeyince
                // dizideki bütün 'place name' leri alır
                // dönüş tipi string list olur.
        ;

        System.out.println(liste);
        Assert.assertTrue(liste.contains("Çaputçu Köyü"));
    }

  /*  @Test
    public void extractingJsonAsPojo()
    {
        Location location=
        given()
                .when()
                .get("/us/90210")
                .then()
                .log().body()
                .extract().as(Location.class)
                ;

        System.out.println(location);
        System.out.println(location.getCountry());
        System.out.println(location.getPlaces());
        System.out.println(location.getPlaces().get(0).getState());

        
    }*/



}








