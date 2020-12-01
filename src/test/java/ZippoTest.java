import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

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




}
