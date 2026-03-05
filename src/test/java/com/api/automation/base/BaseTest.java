package com.api.automation.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.api.automation.utils.ConfigReader;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base class for REST Assured API automation tests
 * Provides common setup for request/response specifications and logging
 */
public class BaseTest implements TestWatcher {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = "═══════════════════════════════════════════════════════════════";
    private static final String PASS_SYMBOL = "✓";
    private static final String FAIL_SYMBOL = "✗";

    protected RequestSpecification requestSpec;

    /**
     * Initializes the RequestSpecification before each test.
     * Sets the base URI from configuration and common request configurations.
     */
    @BeforeEach
    public void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUri())
                .setContentType("application/json")
                .build();
        
        RestAssured.requestSpecification = requestSpec;
    }

    // HTTP Request Methods
    
    /**
     * Makes a GET request to the specified endpoint
     * @param endpoint The endpoint path
     * @return Response object containing the API response
     */
    public Response get(String endpoint) {
        return RestAssured.given(requestSpec)
                .when()
                .get(endpoint);
    }
    
    /**
     * Makes a GET request with query parameters
     * @param endpoint The endpoint path
     * @param queryParam The query parameter name
     * @param value The query parameter value
     * @return Response object containing the API response
     */
    public Response get(String endpoint, String queryParam, Object value) {
        return RestAssured.given(requestSpec)
                .queryParam(queryParam, value)
                .when()
                .get(endpoint);
    }
    
    /**
     * Makes a POST request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    public Response post(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .post(endpoint);
    }
    
    /**
     * Makes a PUT request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    public Response put(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .put(endpoint);
    }
    
    /**
     * Makes a PATCH request with a body
     * @param endpoint The endpoint path
     * @param body The request body
     * @return Response object containing the API response
     */
    public Response patch(String endpoint, String body) {
        return RestAssured.given(requestSpec)
                .body(body)
                .when()
                .patch(endpoint);
    }
    
    /**
     * Makes a DELETE request
     * @param endpoint The endpoint path
     * @return Response object containing the API response
     */
    public Response delete(String endpoint) {
        return RestAssured.given(requestSpec)
                .when()
                .delete(endpoint);
    }

    /**
     * Logs the result of a successful test execution.
     * @param context The context of the test execution.
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        logResult("PASSED", context, null);
    }

    /**
     * Logs the result of a failed test execution.
     * @param context The context of the test execution.
     * @param cause The cause of the failure.
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logResult("FAILED", context, cause);
    }

    /**
     * Logs the result of an aborted test execution.
     * @param context The context of the test execution.
     * @param cause The cause of the abortion.
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logResult("SKIPPED", context, cause);
    }

    /**
     * Logs the result of a test execution.
     * @param status The status of the test (e.g., PASSED, FAILED, SKIPPED).
     * @param context The context of the test execution.
     * @param cause The cause of the result, if any.
     */
    private void logResult(String status, ExtensionContext context, Throwable cause) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String operation = resolveOperation(context);
        String description = normalizeDescription(context.getDisplayName());
        String symbol = status.equals("PASSED") ? PASS_SYMBOL : (status.equals("FAILED") ? FAIL_SYMBOL : "⊘");
        
        // Format the result message
        String resultLine = String.format("%s  [%s] %s", symbol, status, description);
        String operationLine = String.format("   Operation: %s", operation);
        String timeLine = String.format("   Timestamp: %s", timestamp);
        
        if (cause == null) {
            // Success case
            LOGGER.info("\n" + SEPARATOR);
            LOGGER.info(resultLine);
            LOGGER.info(operationLine);
            LOGGER.info(timeLine);
            LOGGER.info(SEPARATOR);
        } else {
            // Failure case
            String errorMessage = cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName();
            String errorLine = String.format("   Error: %s", errorMessage);
            
            LOGGER.severe("\n" + SEPARATOR);
            LOGGER.severe(resultLine);
            LOGGER.severe(operationLine);
            LOGGER.severe(timeLine);
            LOGGER.severe(errorLine);
            LOGGER.severe(SEPARATOR);
        }
    }

    /**
     * Normalizes test description by converting first character to lowercase.
     * If description is null or blank, returns a default message.
     * 
     * @param description The original test description
     * @return Normalized description with lowercase first character
     */
    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return "test executed";
        }

        // Convert first character to lowercase for consistent formatting
        char first = description.charAt(0);
        if (Character.isUpperCase(first)) {
            return Character.toLowerCase(first) + description.substring(1);
        }

        return description;
    }

    /**
     * Resolves the API operation being tested based on the test class and method name.
     * Determines which resource-specific operation resolver to use.
     * 
     * @param context The JUnit extension context containing test metadata
     * @return Human-readable operation string (e.g., "GET /posts/1", "POST /users")
     */
    private String resolveOperation(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();

        // Route to the appropriate operation resolver based on test class
        return switch (className) {
            case "PostsTest" -> resolvePostsOperation(methodName);
            case "CommentsTest" -> resolveCommentsOperation(methodName);
            case "AlbumsTest" -> resolveAlbumsOperation(methodName);
            case "PhotosTest" -> resolvePhotosOperation(methodName);
            case "TodosTest" -> resolveTodosOperation(methodName);
            case "UsersTest" -> resolveUsersOperation(methodName);
            default -> "API TEST";
        };
    }

    /**
     * Resolves the Posts API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Posts resource (e.g., "GET /posts", "POST /posts")
     */
    private String resolvePostsOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /posts";
        if (methodName.contains("Update")) return "PUT /posts/1";
        if (methodName.contains("Patch")) return "PATCH /posts/1";
        if (methodName.contains("DeleteMultiplePosts")) return "DELETE /posts/{id}";
        if (methodName.contains("Delete")) return "DELETE /posts/1";
        if (methodName.contains("ByUserId")) return "GET /posts?userId=1";
        if (methodName.contains("Comments")) return "GET /posts/1/comments";
        if (methodName.contains("All")) return "GET /posts";
        return "GET /posts/1"; // Default: get single post by ID
    }

    /**
     * Resolves the Comments API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Comments resource (e.g., "GET /comments", "POST /comments")
     */
    private String resolveCommentsOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /comments";
        if (methodName.contains("Update")) return "PUT /comments/1";
        if (methodName.contains("Patch")) return "PATCH /comments/1";
        if (methodName.contains("Delete")) return "DELETE /comments/1";
        if (methodName.contains("ByPostId")) return "GET /comments?postId=1";
        if (methodName.contains("All")) return "GET /comments";
        return "GET /comments/1"; // Default: get single comment by ID
    }

    /**
     * Resolves the Albums API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Albums resource (e.g., "GET /albums", "POST /albums")
     */
    private String resolveAlbumsOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /albums";
        if (methodName.contains("Update")) return "PUT /albums/1";
        if (methodName.contains("Patch")) return "PATCH /albums/1";
        if (methodName.contains("Delete")) return "DELETE /albums/1";
        if (methodName.contains("ByUserId")) return "GET /albums?userId=1";
        if (methodName.contains("Photos")) return "GET /albums/1/photos";
        if (methodName.contains("All")) return "GET /albums";
        return "GET /albums/1"; // Default: get single album by ID
    }

    /**
     * Resolves the Photos API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Photos resource (e.g., "GET /photos", "POST /photos")
     */
    private String resolvePhotosOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /photos";
        if (methodName.contains("Update")) return "PUT /photos/1";
        if (methodName.contains("Patch")) return "PATCH /photos/1";
        if (methodName.contains("Delete")) return "DELETE /photos/1";
        if (methodName.contains("ByAlbumId") || methodName.contains("MultipleFilters")) return "GET /photos?albumId=1";
        if (methodName.contains("All")) return "GET /photos";
        return "GET /photos/1"; // Default: get single photo by ID
    }

    /**
     * Resolves the Todos API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * Handles special cases for completion status and filtering.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Todos resource (e.g., "GET /todos", "POST /todos")
     */
    private String resolveTodosOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /todos";
        if (methodName.contains("Update")) return "PUT /todos/1";
        if (methodName.contains("Patch")) return "PATCH /todos/1";
        if (methodName.contains("Delete")) return "DELETE /todos/1";
        if (methodName.contains("ByUserId") || methodName.contains("MultipleFilters")) return "GET /todos?userId=1";
        // Handle completion status filters
        if (methodName.contains("Completed")) return "GET /todos?completed=true/false";
        if (methodName.contains("Incomplete")) return "GET /todos?completed=false";
        if (methodName.contains("All")) return "GET /todos";
        return "GET /todos/1"; // Default: get single todo by ID
    }

    /**
     * Resolves the Users API operation based on the test method name.
     * Maps test method names to their corresponding HTTP operations.
     * Includes related resource queries (posts, albums, todos) for a user.
     * 
     * @param methodName The test method name
     * @return HTTP operation for Users resource (e.g., "GET /users", "POST /users")
     */
    private String resolveUsersOperation(String methodName) {
        // Match keywords in method name to determine the operation
        if (methodName.contains("Create")) return "POST /users";
        if (methodName.contains("Update")) return "PUT /users/1";
        if (methodName.contains("Patch")) return "PATCH /users/1";
        if (methodName.contains("Delete")) return "DELETE /users/1";
        // Handle query/filter operations
        if (methodName.contains("ByUsername")) return "GET /users?username=Bret";
        if (methodName.contains("ByEmail")) return "GET /users?email=...";
        // Handle related resource queries
        if (methodName.contains("Posts")) return "GET /users/1/posts";
        if (methodName.contains("Albums")) return "GET /users/1/albums";
        if (methodName.contains("Todos")) return "GET /users/1/todos";
        if (methodName.contains("All")) return "GET /users";
        return "GET /users/1"; // Default: get single user by ID
    }

    /**
     * Getter for the current RequestSpecification.
     * Provides access to the REST Assured request specification configured in setup().
     * Useful for extending the specification in subclasses.
     *
     * @return RequestSpecification with base configuration (base URI, content type, etc.)
     */
    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    /**
     * Getter for the base URI used by all API tests.
     * Provides access to the configured API endpoint from config.properties.
     *
     * @return Base URI for API endpoints
     */
    public String getBaseUri() {
        return ConfigReader.getBaseUri();
    }
}
