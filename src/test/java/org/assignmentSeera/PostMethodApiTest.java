package org.assignmentSeera;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostMethodApiTest {
    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;
    final String paramName = "query";
    String paramValue = "paris";
    JsonPath jsonPath;

    String bodyAsString;

    ObjectMapper mapper = new ObjectMapper();
    Room room = new Room(3);

    SoftAssertions softAssertions = new SoftAssertions();

    DataManipulationBaseObject dataManipulationBaseObject = new DataManipulationBaseObject();

    @BeforeAll
    static void setRequestSpec() {
        requestSpec = new RequestSpecBuilder().
                setContentType(ContentType.JSON).
                setBaseUri("https://www.tajawal.ae/api/hotel/ahs").
                setBasePath("/search/request").build();

    }

    @BeforeAll
    static void setResponseSpec() {
        responseSpec = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).build();

    }

    @Test
    @Order(1)
    @DisplayName("Post guest room search api with valid data")
    public void guestSearchRoomWithValidData() throws JsonProcessingException {

        room.dates.checkin = getValidDate(1,2);
        room.dates.checkout = getValidDate(11,22);

        jsonPath =
        given().
                log().all().
                spec(requestSpec).
                request().body(dataManipulationBaseObject.convertJavaObjectToJsonString(room)).
                when().post().
                then().log().body().statusCode(200).spec(responseSpec).
                extract().jsonPath();

        RoomResponseApi roomRes = jsonPath.getObject("",RoomResponseApi.class);


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
    public void emptyQueryParamValue1() throws JsonProcessingException {
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
                        "detail.\"dates.checkin\"[2]",equalTo("The dates.checkin must be a date after 20-11-2020."));
    }

    @Test
    @Order(3)
    @DisplayName("Post guest room search Api with null check out date")
    public void invalidCheckOutQDate(){
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
                        "detail.\"dates.checkout\"[2]",equalTo("The dates.checkout must be a date after dates.checkin."));
    }

    @Test
    @Order(4)
    @DisplayName("Post guest room search Api with invalid check in date format")
    public void invalidChekInDatesFormat() throws JsonProcessingException {
        room.dates.checkin = "1.12.2020";
        room.dates.checkout = "10.12.2020";

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
    @DisplayName("Post guest room search Api with invalid destination field")
    public void invalidDestinationField() throws JsonProcessingException {
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

    public String getValidDate(int start, int end){
        return dataManipulationBaseObject.
                setValidDateFormat_RetunDateAsString(dataManipulationBaseObject.getDatePlusMinusRandomDay(start, end));
    }




}
