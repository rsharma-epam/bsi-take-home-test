package ceo.dog.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

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
}
