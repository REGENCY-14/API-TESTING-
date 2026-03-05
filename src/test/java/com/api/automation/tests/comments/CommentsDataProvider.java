package com.api.automation.tests.comments;

/**
 * Data Provider class for Comments API tests
 * Provides all test data and constants for Comments testing
 */
public class CommentsDataProvider {
    
    // Valid IDs
    public static final int VALID_COMMENT_ID = 1;
    public static final int VALID_POST_ID = 1;
    public static final int INVALID_COMMENT_ID = 99999;
    
    // Test comment data
    public static final String TEST_COMMENT_NAME = "Test Comment";
    public static final String TEST_COMMENT_EMAIL = "test@example.com";
    public static final String TEST_COMMENT_BODY = "This is a test comment";
    public static final String UPDATED_COMMENT_NAME = "Updated Comment";
    public static final String UPDATED_COMMENT_EMAIL = "updated@example.com";
    public static final String UPDATED_COMMENT_BODY = "This comment has been updated";
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    public static final int NOT_FOUND_STATUS_CODE = 404;
    
    private CommentsDataProvider() {
        // Utility class, no instantiation
    }
}
