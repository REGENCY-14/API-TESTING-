package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Photos API endpoints
 * Tests CRUD operations on JSONPlaceholder Photos API
 */
@Feature("API Testing")
@Story("Photos Endpoint Tests")
@DisplayName("Photos API Tests")
public class PhotosTest extends BaseTest {

    private static final String PHOTO_BY_ID_1 = Endpoints.PHOTOS + "/1";

    /**
     * Test GET /photos endpoint
     * Validates that the API returns a list of photos
     */
    @Test
    @DisplayName("Should retrieve all photos")
    public void testGetAllPhotos() {
        given(requestSpec)
                .when()
                .get(Endpoints.PHOTOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].albumId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].url", notNullValue())
                .body("[0].thumbnailUrl", notNullValue());
    }

    /**
     * Test GET /photos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve photo with id 1")
    public void testGetPhotoById() {
        given(requestSpec)
                .when()
                .get(PHOTO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("albumId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("url", notNullValue())
                .body("thumbnailUrl", notNullValue());
    }

    /**
     * Test GET /photos/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate photo response fields")
    public void testGetPhotoWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(PHOTO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present and have proper types
        assert response.jsonPath().getInt("albumId") > 0 : "albumId should be a positive integer";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert response.jsonPath().getString("url") != null : "url should not be null";
        assert response.jsonPath().getString("url").startsWith("https://") : "url should be a valid HTTPS URL";
        assert response.jsonPath().getString("thumbnailUrl") != null : "thumbnailUrl should not be null";
        assert response.jsonPath().getString("thumbnailUrl").startsWith("https://") : "thumbnailUrl should be a valid HTTPS URL";
    }

    /**
     * Test GET /photos?albumId=1 endpoint
     * Validates filtering photos by album ID
     */
    @Test
    @DisplayName("Should retrieve photos by album ID")
    public void testGetPhotosByAlbumId() {
        given(requestSpec)
                .queryParam("albumId", 1)
                .when()
                .get(Endpoints.PHOTOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("albumId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /photos with multiple query parameters
     * Validates filtering with multiple parameters
     */
    @Test
    @DisplayName("Should retrieve photos with multiple filters")
    public void testGetPhotosWithMultipleFilters() {
        given(requestSpec)
                .queryParam("albumId", 1)
                .queryParam("id", 1)
                .when()
                .get(Endpoints.PHOTOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(1))
                .body("[0].albumId", equalTo(1))
                .body("[0].id", equalTo(1));
    }

    /**
     * Test GET /photos with invalid ID
     * Validates error handling for non-existent photo
     */
    @Test
    @DisplayName("Should return empty response for invalid photo ID")
    public void testGetPhotoWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.PHOTOS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /photos endpoint
     * Tests creating a new photo
     */
    @Test
    @DisplayName("Should create a new photo successfully")
    public void testCreatePhoto() {
        String photoPayload = "{\n" +
                "  \"albumId\": 1,\n" +
                "  \"title\": \"New Test Photo\",\n" +
                "  \"url\": \"https://via.placeholder.com/600/test\",\n" +
                "  \"thumbnailUrl\": \"https://via.placeholder.com/150/test\"\n" +
                "}";

        given(requestSpec)
                .body(photoPayload)
                .when()
                .post(Endpoints.PHOTOS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("albumId", equalTo(1))
                .body("title", equalTo("New Test Photo"))
                .body("url", equalTo("https://via.placeholder.com/600/test"))
                .body("thumbnailUrl", equalTo("https://via.placeholder.com/150/test"));
    }

    /**
     * Test PUT /photos/1 endpoint
     * Tests updating an existing photo
     */
    @Test
    @DisplayName("Should update photo successfully")
    public void testUpdatePhoto() {
        String updatePayload = "{\n" +
                "  \"albumId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Updated Photo Title\",\n" +
                "  \"url\": \"https://via.placeholder.com/600/updated\",\n" +
                "  \"thumbnailUrl\": \"https://via.placeholder.com/150/updated\"\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(PHOTO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Photo Title"));
    }

    /**
     * Test PATCH /photos/1 endpoint
     * Tests partial update of a photo
     */
    @Test
    @DisplayName("Should patch photo successfully")
    public void testPatchPhoto() {
        String patchPayload = "{\n" +
                "  \"title\": \"Patched Photo Title\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(PHOTO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("title", equalTo("Patched Photo Title"));
    }

    /**
     * Test DELETE /photos/1 endpoint
     * Tests deleting a photo
     */
    @Test
    @DisplayName("Should delete photo successfully")
    public void testDeletePhoto() {
        given(requestSpec)
                .when()
                .delete(PHOTO_BY_ID_1)
                .then()
                .statusCode(200);
    }

    /**
     * Test photo URL validation
     * Validates that photo URLs are accessible
     */
    @Test
    @DisplayName("Should validate photo URL structure")
    public void testPhotoUrlStructure() {
        given(requestSpec)
                .when()
                .get(PHOTO_BY_ID_1)
                .then()
                .statusCode(200)
                .body("url", matchesRegex("https://via\\.placeholder\\.com/\\d+/.*"))
                .body("thumbnailUrl", matchesRegex("https://via\\.placeholder\\.com/\\d+/.*"));
    }
}
