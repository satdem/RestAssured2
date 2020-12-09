package goRest;

import goRest.model.User;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {

    int userId;

    @Test(enabled = false)
    public void getUsers()
    {
        List<User> userList=
        given()
                .when()
                .get("https://gorest.co.in/public-api/users") // request linkini çalıştırdık.
                .then()
                //.log().body()
                .statusCode(200) // dönenen durumun kontrolünü yaptık
                .contentType(ContentType.JSON) // burda dönen verinin type ni kontrol ettik.
                .body("code", equalTo(200)) // dönen (respons) body nin ilk bölümündeki code un değeri kontorl edildi.
                .body("data", not(empty())) // data bölümünün bboş olmadığı kontrol edildi.
                .extract().jsonPath().getList("data" , User.class)// en sona yazılır.
       ;

        for(User us : userList)
            System.out.println(us.toString());

    }

    @Test
    public void createUser()
    {
        userId =
        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"techno\", \"gender\":\"Male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"Active\"}")
                .when()
                .post("https://gorest.co.in/public-api/users")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", equalTo(201))
                .extract().jsonPath().getInt("data.id")
        ;

        System.out.println(userId);
    }

    private String getRandomEmail()
    {
        //randomAlphabetic : emailde geçerli olmayan karakter getirebilir, hata oluşturabilir.
        return RandomStringUtils.randomAlphabetic(8).toLowerCase()+"@gmail.com";
    }

//    https://gorest.co.in/public-api/users -> post
//    headera ekle Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb
//    {"name":"{{$randomFullName}}", "gender":"Male", "email":"{{$randomEmail}}", "status":"Active"}
//    body content JSON
//    işlemin sonucnda id yi almıştık
//    genel kontroller

    @Test(dependsOnMethods = "createUser")
    public void getUserById()
    {
        given()
                .pathParam("userId", userId)
                .when()
                .get("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.id", equalTo(userId))
                ;
    }


    @Test(dependsOnMethods = "createUser")
    public void updateUserById()
    {
        String newName="mehmet yılmaz";
        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+newName+"\"}")
                .pathParam("userId",userId)
                .when()
                .put("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("data.name", equalTo(newName))
                ;
    }

//    https://gorest.co.in/public-api/users/{{userId}}   -> put
//    Authorization Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb
//    body-> {"name":"mehmet yılmaz"} JSON
//    normal kontroller ve name kontrolü


    @Test(dependsOnMethods = "createUser", priority = 1) // 1 değer olmayanlara göre sonra çalışacak
    public void deleteUserById()
    {
        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userId", userId)
                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(204))
                ;
    }

    @Test(dependsOnMethods = "deleteUserById") // 1 değer olmayanlara göre sonra çalışacak
    public void deleteUserByIdNegative()
    {
        given()
                .header("Authorization","Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userId", userId)
                .when()
                .delete("https://gorest.co.in/public-api/users/{userId}")
                .then()
                .statusCode(200)
                .body("code", equalTo(404))
        ;
    }


}














