package api;


import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.baseURI;

public class baseTest {
    @BeforeClass
    public void setup() {
        baseURI = "https://restful-booker.herokuapp.com";
    }

}
