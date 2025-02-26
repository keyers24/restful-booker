package api.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import static io.restassured.RestAssured.given;


public class apiUtils {
    public static JSONObject createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, Date checkin, Date checkout, String additionalneeds) {
        JSONObject booking = new JSONObject();
        booking.put("firstname", firstname);
        booking.put("lastname", lastname);
        booking.put("totalprice", totalprice);
        booking.put("depositpaid", depositpaid);

        //Tarih nesnesini JSON biçimine dönüştürme
        JSONObject bookingDates = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        bookingDates.put("checkin", dateFormat.format(checkin));
        bookingDates.put("checkout", dateFormat.format(checkout));

        booking.put("bookingdates", bookingDates);
        booking.put("additionalneeds", additionalneeds);
        return booking;
    }
    public static String getAuthToken() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        JSONObject requestParams = new JSONObject();
        requestParams.put("username", "admin");
        requestParams.put("password", "password123");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("token");
    }
}






