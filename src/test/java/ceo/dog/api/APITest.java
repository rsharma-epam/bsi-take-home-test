package ceo.dog.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class APITest {

    @Test(description = "test GET request to the Dog API endpoint that provides a random dog image")
    public void testRandomImage() {
        Response response = RestAssured.get("https://dog.ceo/api/breeds/image/random");
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo("application/json");
        response.then().body("message", startsWith("https://images.dog.ceo"), "message", endsWith(".jpg"));
        response.then().body("status", is("success"));
    }

    @Test(description = "test GET request to the Dog API endpoint that lists all dog breeds")
    public void testListAllBreeds() {
        Response response = RestAssured.get("https://dog.ceo/api/breeds/list/all");
        response.then().statusCode(200);
        response.then().contentType("application/json");

        response.then().body("message", hasKey("affenpinscher"));
        response.then().body("message", hasKey("beagle"));
        response.then().body("message", hasKey("wolfhound"));

        response.then().body("status", is("success"));
    }

    @Test(description = "test GET request to the Dog API endpoint that provides images for the specified sub-breed")
    public void testRetrieveSubBreedImages() {
        Response response = RestAssured.get("https://dog.ceo/api/breed/hound/afghan/images");
        response.then().statusCode(200);
        response.then().contentType("application/json");
        response.then().body("status", is("success"));

        response.then().body("message", hasItems("https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg"
                , "https://images.dog.ceo/breeds/hound-afghan/n02088094_1259.jpg"
                , "https://images.dog.ceo/breeds/hound-afghan/n02088094_988.jpg"));

    }
}
