package org.apiTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseLogSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class ApiTest1
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills() throws InterruptedException {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.'place name'", hasItems("Beverly Hills"));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills1() throws InterruptedException {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.'longitude'", hasItems("-118.4065"));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills2() throws InterruptedException {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills3() throws InterruptedException {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills4() throws InterruptedException {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills5()
    {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.'place name'", hasItems("Beverly Hills"));
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills6()
    {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.'longitude'", hasItems("-118.4065"));
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills7()
    {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills8()
    {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
    }

    @Test
    public void requestZipCode9010_checkPlaceNameInResponseBody_expectBeverlyHills9()
    {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).
                body("places.state", hasItems("California"));
    }

    @BeforeClass
    static public void setup(){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(202);
        ResponseSpecification responseSpec = responseSpecBuilder.build();

    }
}
