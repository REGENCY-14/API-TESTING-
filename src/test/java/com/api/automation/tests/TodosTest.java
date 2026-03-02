package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Test class for Todos API endpoints
 * Tests CRUD operations on JSONPlaceholder Todos API
 */
@Feature("API Testing")
@Story("Todos Endpoint Tests")
@DisplayName("Todos API Tests")
public class TodosTest extends BaseTest {

    private static final String TODO_BY_ID_1 = Endpoints.TODOS + "/1";

    /**
     * Test GET /todos endpoint
     * Validates that the API returns a list of todos
     */
    @Test
    @DisplayName("Should retrieve all todos")
    public void testGetAllTodos() {
        given(requestSpec)
                .when()
                .get(Endpoints.TODOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].completed", notNullValue());
    }

    /**
     * Test GET /todos/1 endpoint
     * Validates status code, content type, and response body structure
     */
    @Test
    @DisplayName("Should retrieve todo with id 1")
    public void testGetTodoById() {
        given(requestSpec)
                .when()
                .get(TODO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("completed", isA(Boolean.class));
    }

    /**
     * Test GET /todos/1 endpoint with response validation
     * Demonstrates extracting and validating specific fields
     */
    @Test
    @DisplayName("Should validate todo response fields")
    public void testGetTodoWithResponseValidation() {
        Response response = given(requestSpec)
                .when()
                .get(TODO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .extract()
                .response();

        // Validate that required fields are present and have proper types
        assert response.jsonPath().getInt("userId") > 0 : "userId should be a positive integer";
        assert response.jsonPath().getInt("id") == 1 : "id should be 1";
        assert response.jsonPath().getString("title") != null : "title should not be null";
        assert !response.jsonPath().getString("title").isEmpty() : "title should not be empty";
        assert response.jsonPath().get("completed") instanceof Boolean : "completed should be a boolean";
    }

    /**
     * Test GET /todos?userId=1 endpoint
     * Validates filtering todos by user ID
     */
    @Test
    @DisplayName("Should retrieve todos by user ID")
    public void testGetTodosByUserId() {
        given(requestSpec)
                .queryParam("userId", 1)
                .when()
                .get(Endpoints.TODOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)));
    }

    /**
     * Test GET /todos?completed=true endpoint
     * Validates filtering todos by completion status
     */
    @Test
    @DisplayName("Should retrieve completed todos")
    public void testGetCompletedTodos() {
        given(requestSpec)
                .queryParam("completed", true)
                .when()
                .get(Endpoints.TODOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("completed", everyItem(equalTo(true)));
    }

    /**
     * Test GET /todos?completed=false endpoint
     * Validates filtering todos by incomplete status
     */
    @Test
    @DisplayName("Should retrieve incomplete todos")
    public void testGetIncompleteTodos() {
        given(requestSpec)
                .queryParam("completed", false)
                .when()
                .get(Endpoints.TODOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("completed", everyItem(equalTo(false)));
    }

    /**
     * Test GET /todos with multiple filters
     * Validates combining userId and completed filters
     */
    @Test
    @DisplayName("Should retrieve todos with multiple filters")
    public void testGetTodosWithMultipleFilters() {
        given(requestSpec)
                .queryParam("userId", 1)
                .queryParam("completed", false)
                .when()
                .get(Endpoints.TODOS)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(1)))
                .body("completed", everyItem(equalTo(false)));
    }

    /**
     * Test GET /todos with invalid ID
     * Validates error handling for non-existent todo
     */
    @Test
    @DisplayName("Should return empty response for invalid todo ID")
    public void testGetTodoWithInvalidId() {
        given(requestSpec)
                .when()
                .get(Endpoints.TODOS + "/99999")
                .then()
                .statusCode(404);
    }

    /**
     * Test POST /todos endpoint
     * Tests creating a new todo
     */
    @Test
    @DisplayName("Should create a new todo successfully")
    public void testCreateTodo() {
        String todoPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"New Test Todo\",\n" +
                "  \"completed\": false\n" +
                "}";

        given(requestSpec)
                .body(todoPayload)
                .when()
                .post(Endpoints.TODOS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(1))
                .body("title", equalTo("New Test Todo"))
                .body("completed", equalTo(false));
    }

    /**
     * Test POST /todos endpoint with completed todo
     * Tests creating a todo that is already completed
     */
    @Test
    @DisplayName("Should create a completed todo successfully")
    public void testCreateCompletedTodo() {
        String todoPayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"title\": \"Completed Todo\",\n" +
                "  \"completed\": true\n" +
                "}";

        given(requestSpec)
                .body(todoPayload)
                .when()
                .post(Endpoints.TODOS)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("completed", equalTo(true));
    }

    /**
     * Test PUT /todos/1 endpoint
     * Tests updating an existing todo
     */
    @Test
    @DisplayName("Should update todo successfully")
    public void testUpdateTodo() {
        String updatePayload = "{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Updated Todo Title\",\n" +
                "  \"completed\": true\n" +
                "}";

        given(requestSpec)
                .body(updatePayload)
                .when()
                .put(TODO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(1))
                .body("title", equalTo("Updated Todo Title"))
                .body("completed", equalTo(true));
    }

    /**
     * Test PATCH /todos/1 endpoint
     * Tests partial update of a todo (marking as completed)
     */
    @Test
    @DisplayName("Should patch todo to mark as completed")
    public void testPatchTodoCompleted() {
        String patchPayload = "{\n" +
                "  \"completed\": true\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(TODO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("completed", equalTo(true));
    }

    /**
     * Test PATCH /todos/1 endpoint
     * Tests partial update of a todo title
     */
    @Test
    @DisplayName("Should patch todo title")
    public void testPatchTodoTitle() {
        String patchPayload = "{\n" +
                "  \"title\": \"Patched Todo Title\"\n" +
                "}";

        given(requestSpec)
                .body(patchPayload)
                .when()
                .patch(TODO_BY_ID_1)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("title", equalTo("Patched Todo Title"));
    }

    /**
     * Test DELETE /todos/1 endpoint
     * Tests deleting a todo
     */
    @Test
    @DisplayName("Should delete todo successfully")
    public void testDeleteTodo() {
        given(requestSpec)
                .when()
                .delete(TODO_BY_ID_1)
                .then()
                .statusCode(200);
    }
}
