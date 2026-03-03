package com.api.automation.base;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

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
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    /**
     * Initializes the RequestSpecification before each test.
     * Sets the base URI and common request configurations.
     */
    @BeforeEach
    public void setup() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
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
    protected Response get(String endpoint) {
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
    protected Response get(String endpoint, String queryParam, Object value) {
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
    protected Response post(String endpoint, String body) {
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
    protected Response put(String endpoint, String body) {
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
    protected Response patch(String endpoint, String body) {
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
    protected Response delete(String endpoint) {
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

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return "test executed";
        }

        char first = description.charAt(0);
        if (Character.isUpperCase(first)) {
            return Character.toLowerCase(first) + description.substring(1);
        }

        return description;
    }

    private String resolveOperation(ExtensionContext context) {
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = context.getRequiredTestMethod().getName();

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

    private String resolvePostsOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /posts";
        if (methodName.contains("Update")) return "PUT /posts/1";
        if (methodName.contains("Patch")) return "PATCH /posts/1";
        if (methodName.contains("DeleteMultiplePosts")) return "DELETE /posts/{id}";
        if (methodName.contains("Delete")) return "DELETE /posts/1";
        if (methodName.contains("ByUserId")) return "GET /posts?userId=1";
        if (methodName.contains("Comments")) return "GET /posts/1/comments";
        if (methodName.contains("All")) return "GET /posts";
        return "GET /posts/1";
    }

    private String resolveCommentsOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /comments";
        if (methodName.contains("Update")) return "PUT /comments/1";
        if (methodName.contains("Patch")) return "PATCH /comments/1";
        if (methodName.contains("Delete")) return "DELETE /comments/1";
        if (methodName.contains("ByPostId")) return "GET /comments?postId=1";
        if (methodName.contains("All")) return "GET /comments";
        return "GET /comments/1";
    }

    private String resolveAlbumsOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /albums";
        if (methodName.contains("Update")) return "PUT /albums/1";
        if (methodName.contains("Patch")) return "PATCH /albums/1";
        if (methodName.contains("Delete")) return "DELETE /albums/1";
        if (methodName.contains("ByUserId")) return "GET /albums?userId=1";
        if (methodName.contains("Photos")) return "GET /albums/1/photos";
        if (methodName.contains("All")) return "GET /albums";
        return "GET /albums/1";
    }

    private String resolvePhotosOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /photos";
        if (methodName.contains("Update")) return "PUT /photos/1";
        if (methodName.contains("Patch")) return "PATCH /photos/1";
        if (methodName.contains("Delete")) return "DELETE /photos/1";
        if (methodName.contains("ByAlbumId") || methodName.contains("MultipleFilters")) return "GET /photos?albumId=1";
        if (methodName.contains("All")) return "GET /photos";
        return "GET /photos/1";
    }

    private String resolveTodosOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /todos";
        if (methodName.contains("Update")) return "PUT /todos/1";
        if (methodName.contains("Patch")) return "PATCH /todos/1";
        if (methodName.contains("Delete")) return "DELETE /todos/1";
        if (methodName.contains("ByUserId") || methodName.contains("MultipleFilters")) return "GET /todos?userId=1";
        if (methodName.contains("Completed")) return "GET /todos?completed=true/false";
        if (methodName.contains("Incomplete")) return "GET /todos?completed=false";
        if (methodName.contains("All")) return "GET /todos";
        return "GET /todos/1";
    }

    private String resolveUsersOperation(String methodName) {
        if (methodName.contains("Create")) return "POST /users";
        if (methodName.contains("Update")) return "PUT /users/1";
        if (methodName.contains("Patch")) return "PATCH /users/1";
        if (methodName.contains("Delete")) return "DELETE /users/1";
        if (methodName.contains("ByUsername")) return "GET /users?username=Bret";
        if (methodName.contains("ByEmail")) return "GET /users?email=...";
        if (methodName.contains("Posts")) return "GET /users/1/posts";
        if (methodName.contains("Albums")) return "GET /users/1/albums";
        if (methodName.contains("Todos")) return "GET /users/1/todos";
        if (methodName.contains("All")) return "GET /users";
        return "GET /users/1";
    }

    /**
     * Get the current RequestSpecification
     *
     * @return RequestSpecification with base configuration
     */
    public RequestSpecification getRequestSpec() {
        return requestSpec;
    }

    /**
     * Get the base URI
     *
     * @return Base URI for API endpoints
     */
    public String getBaseUri() {
        return BASE_URI;
    }
}
