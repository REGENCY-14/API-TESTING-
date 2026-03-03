package com.api.automation.testdata.posts;

/**
 * Test data constants for Posts API tests
 */
public class PostsTestData {
    
    // Valid IDs
    public static final int VALID_POST_ID = 1;
    public static final int VALID_USER_ID = 1;
    public static final int VALID_POST_ID_FOR_COMMENTS = 1;
    
    // Boundary IDs
    public static final int FIRST_VALID_ID = 1;
    public static final int LAST_VALID_ID = 100;
    public static final int ZERO_ID = 0;
    public static final int NEGATIVE_ID = -1;
    public static final int BEYOND_MAX_ID = 101;
    public static final int INVALID_ID = 99999;
    
    // Test data values
    public static final String TEST_POST_TITLE = "Test Post Title";
    public static final String TEST_POST_BODY = "This is a test post body";
    public static final String UPDATED_POST_TITLE = "Updated Post Title";
    public static final String UPDATED_POST_BODY = "This post has been updated";
    
    // Intentional failure data
    public static final int WRONG_POST_ID = 999;
    public static final String WRONG_TITLE = "This title does not exist";
    public static final String NON_EXISTENT_FIELD = "nonExistentField";
    public static final int WRONG_STATUS_CODE = 404;
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    
    private PostsTestData() {
        // Utility class, no instantiation
    }
}
