package com.api.automation.tests.albums;

/**
 * Data Provider class for Albums API tests
 * Provides all test data and constants for Albums testing
 */
public class AlbumsDataProvider {
    
    // Valid IDs
    public static final int VALID_ALBUM_ID = 1;
    public static final int VALID_USER_ID = 1;
    
    // Boundary IDs
    public static final int FIRST_VALID_ID = 1;
    public static final int LAST_VALID_ID = 100;
    public static final int ZERO_ID = 0;
    public static final int NEGATIVE_ID = -1;
    public static final int BEYOND_MAX_ID = 101;
    
    // Test data values
    public static final String TEST_ALBUM_TITLE = "New Test Album";
    public static final String UPDATED_ALBUM_TITLE = "Updated Album Title";
    public static final String PATCHED_ALBUM_TITLE = "Patched Album Title";
    
    // Intentional failure data
    public static final int WRONG_STATUS_CODE = 500;
    public static final String WRONG_TITLE = "This title does not exist";
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    
    private AlbumsDataProvider() {
        // Utility class, no instantiation
    }
}
