package org.assignmentSeera;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.assertj.core.api.SoftAssertions;
import org.assignmentSeera.apiEngine.model.response.Hotel;
import org.assignmentSeera.apiEngine.model.response.Locations;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * The assignment does not provide comprehensive information about
 * the API ex(schema, data types accepted, error messages, mandatory fields etc..)
 * so the test cases implemented accordingly.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetMethodApiTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    final String paramName = "query";
    final String paramValue = "paris";

    JsonPath jsonPath;

    @BeforeAll
    static void setRequestSpec() {
        String baseURI1 = "https://www.tajawal.ae";
        String basePath1 = "/api/hotel/ahs/v2/geo-suggest";
        requestSpec = new RequestSpecBuilder().
                setAccept(ContentType.JSON).
                setBaseUri(baseURI1).
                setBasePath(basePath1).build();

    }

    @BeforeAll
    static void setResponseSpec() {
        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).build();

    }


    @Test
    @Order(1)
    @DisplayName("Search for hotels with valid data")
    void hotelFieldsIsNotEmpty() {
        jsonPath =
                given().
                        spec(requestSpec).
                        param(paramName, paramValue).
                        when().
                        get().
                        then().
                        spec(responseSpec).
                        extract().jsonPath();

        Hotel[] hotels = jsonPath.getObject("hotels", Hotel[].class);

        SoftAssertions softAssertions = new SoftAssertions();
        for (Hotel hotel : hotels) {
            softAssertions.assertThat(hotel.hotelId).as("hotelId with name:" + hotel.name).isNotNull();
            softAssertions.assertThat(hotel.name).as("name with hotel Id:" + hotel.hotelId).isNotEmpty();
            softAssertions.assertThat(hotel.city).as("city with hotel Id:" + hotel.hotelId).isNotEmpty();
            softAssertions.assertThat(hotel.country).as("country with hotel Id:" + hotel.hotelId).isNotEmpty();
            softAssertions.assertThat(hotel.thumbnail_url).as("thumbnail_url with hotel Id:" + hotel.hotelId).isNotNull();
            softAssertions.assertThat(hotel.displayType).as("displayType with hotel Id:" + hotel.hotelId).isNotEmpty();
            softAssertions.assertThat(hotel.latitude).as("latitude name with hotel Id:" + hotel.hotelId).isNotNull();
            softAssertions.assertThat(hotel.longitude).as("longitude with hotel Id:" + hotel.hotelId).isNotNull();
            softAssertions.assertThat(hotel.isActive).as("isActive with hotel Id:" + hotel.hotelId).isNotNull();
        }
        softAssertions.assertAll();

    }


    @Test
    @Order(2)
    @DisplayName("search hotel locations with valid data")
    void getHotelLocation() {
        jsonPath =
                given().
                        spec(requestSpec).
                        param(paramName, paramValue).
                        when().
                        get().
                        then().
                        spec(responseSpec).
                        extract().jsonPath();

        Locations[] locations = jsonPath.getObject("locations", Locations[].class);

        SoftAssertions softAssertions = new SoftAssertions();
        for (Locations location : locations) {
            softAssertions.assertThat(location.name).as("location with placeId:" + location.placeId).isNotNull();
            softAssertions.assertThat(location.placeId).as("placeId with country: " + location.country + " city: " + location.city).isNotEmpty();
            softAssertions.assertThat(location.source).as("source with placeId:" + location.placeId).isNotEmpty();
            softAssertions.assertThat(location.country).as("country with placeId:" + location.placeId).isNotEmpty();
            softAssertions.assertThat(location.city).as("city with placeId:" + location.placeId).isNotNull();
            softAssertions.assertThat(location.displayType).as("displayType with hotel Id:" + location.placeId).isNotEmpty();
            softAssertions.assertThat(location.googleType.TypeNameAR).as("TypeNameAR name with placeId:" + location.placeId).isNotNull();
            softAssertions.assertThat(location.googleType.TypeNameEN).as("TypeNameEN name with placeId:" + location.placeId).isNotNull();
        }
        softAssertions.assertAll();

    }

    @Test
    @Order(3)
    @DisplayName("search for hotels and location with an empty city field")
    void emptyQueryParamValue() {

        given().
                spec(requestSpec).
                param(paramName, "").
                when().
                get().
                then().
                spec(responseSpec).
                assertThat().body("hotels", empty(), "locations", empty());
    }
}
