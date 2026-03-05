package com.api.automation.tests.photos;

/**
 * Data Provider class for Photos API tests
 * Provides all test data and constants for Photos testing
 */
public class PhotosDataProvider {
    
    // Valid IDs
    public static final int VALID_PHOTO_ID = 1;
    public static final int VALID_ALBUM_ID = 1;
    public static final int INVALID_PHOTO_ID = 99999;
    
    // Test photo data
    public static final String TEST_PHOTO_TITLE = "New Test Photo";
    public static final String TEST_PHOTO_URL = "https://via.placeholder.com/600/test";
    public static final String TEST_PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/test";
    public static final String UPDATED_PHOTO_TITLE = "Updated Photo Title";
    public static final String UPDATED_PHOTO_URL = "https://via.placeholder.com/600/updated";
    public static final String UPDATED_PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/updated";
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    
    private PhotosDataProvider() {
        // Utility class, no instantiation
    }
}
