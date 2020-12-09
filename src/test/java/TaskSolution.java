import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import pojo.ToDo;

import java.util.Arrays;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TaskSolution {

    @Test
    public void task1()
    {
        given()
                .when()
                .get("https://httpstat.us/203")
                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
        ;
    }

    @Test
    public void task2()
    {
        given()
                .when()
                .get("https://httpstat.us/203")
                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT)
                .body( equalTo("203 Non-Authoritative Information"))
        ;
    }

    @Test
    public void task3()
    {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
                ;
    }

    @Test
    public void task4()
    {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))
        ;
    }

    @Test
    public void task5()
    {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("userId[2]", equalTo(1))
                .body("title[2]", equalTo("fugiat veniam minus"))
        ;
    }


    @Test
    public void task6()
    {
        ToDo toDo=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().as(ToDo.class)
        ;

        System.out.println(toDo);
    }


    @Test
    public void task7()
    {
        ToDo[] toDoArray=
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos")
                        .then()
                        //.log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().as(ToDo[].class)
                ;

        System.out.println(Arrays.toString(toDoArray) );
    }





}
