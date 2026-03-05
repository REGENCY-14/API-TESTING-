package com.api.automation.tests.posts;

/**
 * Payload class representing a Post object
 * Used for request/response mapping in Posts API tests
 */
public class PostsPayload {
    
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
    
    // Constructors
    
    public PostsPayload() {
    }
    
    public PostsPayload(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
    
    public PostsPayload(Integer userId, Integer id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
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
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    // toString
    
    @Override
    public String toString() {
        return "PostsPayload{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
