package com.api.automation.tests.todos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.api.automation.base.BaseTest;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import utils.Endpoints;

/**
 * Test class for Todos DELETE endpoints
 * Tests DELETE operations on JSONPlaceholder Todos API
 */
@Feature("API Testing")
@Story("Todos DELETE Tests")
@DisplayName("Todos DELETE API Tests")
public class TodosDeleteTests extends BaseTest {

    // HTTP Request Methods
    
    private Response deleteTodo(int todoId) {
        return delete(Endpoints.TODOS + "/" + todoId);
    }

    // Test Methods

    /**
     * Test DELETE /todos/1 endpoint
     * Tests deleting a todo
     */
    @Test
    @DisplayName("Should delete todo successfully")
    public void testDeleteTodo() {
        deleteTodo(1)
                .then()
                .statusCode(200);
    }
}
