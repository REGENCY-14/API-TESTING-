package com.api.automation.tests.todos;

import io.restassured.response.Response;
import utils.Endpoints;
import com.api.automation.base.BaseTest;

/**
 * Endpoint class for Todos API
 * Encapsulates all HTTP operations related to Todos
 */
public class TodosEndpoint {

    private BaseTest baseTest = new BaseTest();

    public TodosEndpoint() {
        baseTest.setup();
    }

    // GET Operations
    
    public Response getAllTodos() {
        return baseTest.get(Endpoints.TODOS);
    }
    
    public Response getTodoById(int todoId) {
        return baseTest.get(Endpoints.TODOS + "/" + todoId);
    }
    
    public Response getTodosByUserId(int userId) {
        return baseTest.get(Endpoints.TODOS, "userId", userId);
    }
    
    public Response getTodosByCompletionStatus(boolean completed) {
        return baseTest.get(Endpoints.TODOS, "completed", completed);
    }

    // POST Operations
    
    public Response createTodo(int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, title, completed);
        return baseTest.post(Endpoints.TODOS, payload);
    }

    // PUT Operations
    
    public Response updateTodo(int todoId, int userId, String title, boolean completed) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"completed\": %b\n" +
                "}", userId, todoId, title, completed);
        return baseTest.put(Endpoints.TODOS + "/" + todoId, payload);
    }

    // DELETE Operations
    
    public Response deleteTodo(int todoId) {
        return baseTest.delete(Endpoints.TODOS + "/" + todoId);
    }
}
