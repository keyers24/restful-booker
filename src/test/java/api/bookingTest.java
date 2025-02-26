package api;

import api.utils.apiUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertTrue;
import java.util.Calendar;
import java.util.Date;

public class bookingTest extends baseTest {

    private static int bookingId;

    @Test(description = "Create Success Scenario")
    public void create() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MARCH, 1);
        Date checkinDate = calendar.getTime();

        calendar.set(2025, Calendar.MARCH, 5);
        Date checkoutDate = calendar.getTime();

        JSONObject payload = apiUtils.createBooking("Omer", "Ozdemir", 100, true, checkinDate, checkoutDate, "Breakfast");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(payload.toString())
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        // responsedan booking ID'yi dinamik olarak alıyoruz
        bookingId = response.jsonPath().getInt("bookingid");

        Assert.assertTrue(bookingId > 0, "Booking ID geçersiz!");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Created Booking ID: " + bookingId);
        System.out.println(response.asPrettyString());
    }

    @Test(description = "Get Created Booking", dependsOnMethods = "create")
    public void testGetBooking() {
        Assert.assertTrue(bookingId > 0, "Booking ID geçersiz!");

        Response response = given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        System.out.println("Response Body: " + responseBody);

        assertTrue(responseBody.contains("Omer"));
        assertTrue(responseBody.contains("Ozdemir"));
    }

    @Test(description = "Delete Created Booking",dependsOnMethods = "create")
    public void testDeleteBooking() {
        Assert.assertTrue(bookingId > 0, "Booking ID geçersiz!");
        String token = apiUtils.getAuthToken();

        Response response = given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .statusCode(201)
                .extract().response();

        String responseBody = response.getBody().asString();
        System.out.println("Deleted Booking ID: " + bookingId);
        System.out.println("Response Body: " + responseBody);
    }
}
