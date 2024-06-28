package ceo.dog.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class APITest {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://dog.ceo";
    }

    @Test(description = "test GET request to the Dog API endpoint that provides a random dog image")
    public void testRandomImage() {
        Response response = RestAssured.get("/api/breeds/image/random");
        commonChecks(response);
        response.then().body("message", startsWith("https://images.dog.ceo"), "message", endsWith(".jpg"));
    }

    @Test(description = "test GET request to the Dog API endpoint that lists all dog breeds")
    public void testListAllBreeds() {
        Response response = RestAssured.get("/api/breeds/list/all");
        commonChecks(response);

        response.then().body("message", hasKey("affenpinscher"));
        response.then().body("message", hasKey("beagle"));
        response.then().body("message", hasKey("wolfhound"));
    }

    @Test(description = "test GET request to the Dog API endpoint that provides images for the specified sub-breed")
    public void testRetrieveSubBreedImages() {
        Response response = RestAssured.get("/api/breed/hound/afghan/images");
        commonChecks(response);

        response.then().body("message", hasItems("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                , "https://images.dog.ceo/breeds/hound-afghan/n02088094_1259.jpg"
                , "https://images.dog.ceo/breeds/hound-afghan/n02088094_988.jpg"));

    }

    private static void commonChecks(Response response) {
        response.then().statusCode(200);
        response.then().contentType("application/json");
        response.then().body("status", is("success"));
    }
}
