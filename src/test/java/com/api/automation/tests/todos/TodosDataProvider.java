package com.api.automation.tests.todos;

/**
 * Data Provider class for Todos API tests
 * Provides all test data and constants for Todos testing
 */
public class TodosDataProvider {
    
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
    
    private TodosDataProvider() {
        // Utility class, no instantiation
    }
}
