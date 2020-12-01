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
                .log().all()
                .statusCode(200)
        ;
    }

}
