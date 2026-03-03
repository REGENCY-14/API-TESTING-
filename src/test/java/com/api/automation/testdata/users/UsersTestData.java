package com.api.automation.testdata.users;

/**
 * Test data constants for Users API tests
 */
public class UsersTestData {
    
    // Valid IDs
    public static final int FIRST_VALID_ID = 1;
    public static final int LAST_VALID_ID = 10;
    public static final int ZERO_ID = 0;
    public static final int NEGATIVE_ID = -1;
    public static final int BEYOND_MAX_ID = 11;
    
    // Valid user credentials
    public static final String VALID_USERNAME = "Bret";
    public static final String VALID_EMAIL = "Sincere@april.biz";
    
    // Test user data
    public static final String TEST_USER_NAME = "Test User";
    public static final String TEST_USERNAME = "testuser";
    public static final String TEST_EMAIL = "testuser@example.com";
    public static final String TEST_STREET = "Test Street";
    public static final String TEST_CITY = "Test City";
    public static final String TEST_ZIPCODE = "12345";
    public static final String TEST_PHONE = "123-456-7890";
    public static final String TEST_WEBSITE = "testuser.com";
    public static final String TEST_COMPANY_NAME = "Test Company";
    public static final String TEST_CATCHPHRASE = "Test Catchphrase";
    public static final String TEST_BS = "Test BS";
    
    // Intentional failure data
    public static final String WRONG_EMAIL = "wrong@email.com";
    public static final String WRONG_USERNAME = "WrongUsername";
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    
    private UsersTestData() {
        // Utility class, no instantiation
    }
}
