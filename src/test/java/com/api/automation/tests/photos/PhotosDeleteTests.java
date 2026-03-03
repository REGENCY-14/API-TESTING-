package com.api.automation.tests.photos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Photos DELETE endpoints
 * Tests DELETE operations on JSONPlaceholder Photos API
 */
@Feature("API Testing")
@Story("Photos DELETE Tests")
@DisplayName("Photos DELETE API Tests")
public class PhotosDeleteTests extends BaseTest {

    // HTTP Request Methods
    
    private Response deletePhoto(int photoId) {
        return delete(Endpoints.PHOTOS + "/" + photoId);
    }

    // Test Methods

    /**
     * Test DELETE /photos/1 endpoint
     * Tests deleting a photo
     */
    @Test
    @DisplayName("Should delete photo successfully")
    public void testDeletePhoto() {
        deletePhoto(1)
                .then()
                .statusCode(200);
    }
}
