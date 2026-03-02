package com.api.automation.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Base class for REST Assured API automation tests
 * Provides common setup for request/response specifications and logging
 */
public class BaseTest implements BeforeTestExecutionCallback, TestWatcher {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    private static final Map<String, Long> TEST_START_TIMES = new ConcurrentHashMap<>();

    protected RequestSpecification requestSpec;
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    /**
     * Records the start time of a test execution.
     * Stores the start time in a map using the test's unique ID.
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) {
        TEST_START_TIMES.put(context.getUniqueId(), System.currentTimeMillis());
    }

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
        String operation = resolveOperation(context);
        String description = normalizeDescription(context.getDisplayName());
        long elapsedMs = elapsedMs(context);

        String message = String.format("[%s]: %s - %s (%dms)", status, operation, description, elapsedMs);
        if (cause == null) {
            LOGGER.info(message);
            return;
        }

        LOGGER.severe(message + " | Reason: " + cause.getMessage());
    }

    private long elapsedMs(ExtensionContext context) {
        Long startedAt = TEST_START_TIMES.remove(context.getUniqueId());
        if (startedAt == null) {
            return 0L;
        }
        return System.currentTimeMillis() - startedAt;
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
            case "GetPostTest" -> "GET /posts/1";
            case "PostTest" -> "POST /posts";
            case "PutTest" -> "PUT /posts/1";
            case "DeleteTest" -> methodName.equals("testDeleteMultiplePosts") ? "DELETE /posts/{id}" : "DELETE /posts/1";
            default -> "API TEST";
        };
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
