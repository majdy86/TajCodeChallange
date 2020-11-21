package org.assignmentSeera;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.assignmentSeera.apiEngine.model.requests.Room;
import org.assignmentSeera.apiEngine.model.response.RoomResponseApi;
import org.assignmentSeera.dataProvider.DataManipulationBaseObject;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * The assignment does not provide comprehensive information about
 * the API ex(schema, data types accepted, error messages, mandatory fields etc..)
 * so the test cases implemented accordingly.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostMethodApiTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    RoomResponseApi roomRes;
    JsonPath jsonPath;
    final Room room = new Room();

    final SoftAssertions softAssertions = new SoftAssertions();

    DataManipulationBaseObject dataManipulationBaseObject = new DataManipulationBaseObject();

    @BeforeAll
    static void setRequestSpec() {
        baseURI = "https://www.tajawal.ae/api/hotel/ahs";
        basePath = "/search/request";
        requestSpec = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                setBaseUri(baseURI).
                setBasePath(basePath).build();

    }

    @BeforeAll
    static void setResponseSpec() {
        responseSpec = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).build();

    }

    @Test
    @Order(1)
    @DisplayName("Post guest room search api with valid data")
    public void guestSearchRoomWithValidData() {

        room.dates.checkin = dataManipulationBaseObject.getValidStringDate(1, 10);
        room.dates.checkout = dataManipulationBaseObject.getValidStringDate(11, 20);

        jsonPath =
                given().
                        log().all().
                        spec(requestSpec).
                        request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                        when().post().
                        then().log().body().statusCode(200).spec(responseSpec).
                        extract().jsonPath();

        roomRes = jsonPath.getObject("", RoomResponseApi.class);

        softAssertions.assertThat(roomRes.type).as("Type").isEqualTo("hotel");
        softAssertions.assertThat(roomRes.query).as("Query").isNotEmpty();
        softAssertions.assertThat(roomRes.isCountry).as("Country:").isFalse();
        softAssertions.assertThat(roomRes.queryParameters).as("QueryParameters:").containsIgnoringCase(roomRes.queryParametersObj.placeId);
        softAssertions.assertThat(roomRes.queryParametersObj.hId).as("hId").isNullOrEmpty();
        softAssertions.assertThat(roomRes.queryParametersObj.isCountry).as("isCountry").isNotEmpty();
        softAssertions.assertThat(roomRes.queryParametersObj.isGeo).as("isGeo").isNotNull();
        softAssertions.assertThat(roomRes.queryParametersObj.placeId).as("placeId").isNotEmpty();
        softAssertions.assertThat(roomRes.queryParametersObj.types).as("types").isNullOrEmpty();

        softAssertions.assertAll();
    }

    @Test
    @Order(2)
    @DisplayName("Post guest room search Api with null check in date")
    public void emptyQueryParamValue1() {
        room.dates.checkin = "null";

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.\"dates.checkin\"[0]", equalTo("The dates.checkin is not a valid date."),
                        "detail.\"dates.checkin\"[1]", equalTo("The dates.checkin does not match the format d-m-Y."),
                        "detail.\"dates.checkin\"[2]", equalTo("The dates.checkin must be a date after 20-11-2020."));
    }

    @Test
    @Order(3)
    @DisplayName("Post guest room search Api with null check out date")
    public void invalidCheckOutQDate() {
        room.dates.checkout = "null";

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.\"dates.checkout\"[0]", equalTo("The dates.checkout is not a valid date."),
                        "detail.\"dates.checkout\"[1]", equalTo("The dates.checkout does not match the format d-m-Y."),
                        "detail.\"dates.checkout\"[2]", equalTo("The dates.checkout must be a date after dates.checkin."));
    }

    @Test
    @Order(4)
    @DisplayName("Post guest room search Api with invalid dates format")
    public void invalidDatesFormat() {
        room.dates.checkin = dataManipulationBaseObject.getInvalidStringDateFormat(1, 10);
        room.dates.checkout = dataManipulationBaseObject.getInvalidStringDateFormat(11, 20);

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.\"dates.checkin\"[0]", equalTo("The dates.checkin does not match the format d-m-Y."),
                        "detail.\"dates.checkout\"[0]", equalTo("The dates.checkout does not match the format d-m-Y."));
    }

    @Test
    @Order(5)
    @DisplayName("Post guest room search Api with invalid dates")
    public void invalidDates() {
        room.dates.checkin = dataManipulationBaseObject.getInvalidStringDate(1, 10);
        room.dates.checkout = dataManipulationBaseObject.getInvalidStringDate(11, 20);

        given().
                log().body().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).log().body().
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.\"dates.checkin\"[0]", equalTo("The dates.checkin does not match the format d-m-Y."),
                        "detail.\"dates.checkout\"[0]", equalTo("The dates.checkout does not match the format d-m-Y."));
    }


    @Test
    @Order(6)
    @DisplayName("Post guest room search Api with invalid destination field")
    public void invalidDestinationField() {
        room.destination = null;

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.destination[0]", equalTo("The destination field is required."));
    }

    @Test
    @Order(7)
    @DisplayName("Post guest room search api with checkin date before the checkout date")
    public void checkInDateBeforeCheckOutDate() {
        room.dates.checkin = dataManipulationBaseObject.getValidStringDate(10, 20);
        room.dates.checkout = dataManipulationBaseObject.getValidStringDate(1, 9);

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("detail.\"dates.checkout\"[0]", equalTo("The dates.checkout must be a date after dates.checkin."));
    }

    @Test
    @Order(8)
    @DisplayName("Post guest room search api with more than 29 days between the checkin and checkout dates")
    public void aMonthBetweenCheckInAndOut() {
        room.dates.checkin = dataManipulationBaseObject.getValidStringDate(1, 10);
        room.dates.checkout = dataManipulationBaseObject.getValidStringDate(70, 80);

        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().

                then().spec(responseSpec).
                assertThat().
                statusCode(HttpStatus.SC_BAD_REQUEST).log().body().
                body("detail.dates.checkout[0]", containsStringIgnoringCase("Checkout date must before "));
    }
}
