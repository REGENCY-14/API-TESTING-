package com.api.automation.tests.todos;

/**
 * Payload class representing a Todo object
 * Used for request/response mapping in Todos API tests
 */
public class TodosPayload {
    
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;
    
    // Constructors
    
    public TodosPayload() {
    }
    
    public TodosPayload(Integer userId, String title, Boolean completed) {
        this.userId = userId;
        this.title = title;
        this.completed = completed;
    }
    
    public TodosPayload(Integer userId, Integer id, String title, Boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }
    
    // Getters and Setters
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    // toString
    
    @Override
    public String toString() {
        return "TodosPayload{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}
