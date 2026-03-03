package com.api.automation.testdata.todos;

/**
 * Test data constants for Todos API tests
 */
public class TodosTestData {
    
    // Valid IDs
    public static final int VALID_TODO_ID = 1;
    public static final int VALID_USER_ID = 1;
    
    // Test todo data
    public static final String TEST_TODO_TITLE = "New Test Todo";
    public static final boolean TEST_TODO_COMPLETED = false;
    public static final String UPDATED_TODO_TITLE = "Updated Todo Title";
    public static final boolean UPDATED_TODO_COMPLETED = true;
    
    // Expected status codes
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int CREATED_STATUS_CODE = 201;
    
    private TodosTestData() {
        // Utility class, no instantiation
    }
}
