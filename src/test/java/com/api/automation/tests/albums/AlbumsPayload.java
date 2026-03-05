package com.api.automation.tests.albums;

/**
 * Payload class representing an Album object
 * Used for request/response mapping in Albums API tests
 */
public class AlbumsPayload {
    
    private Integer userId;
    private Integer id;
    private String title;
    
    // Constructors
    
    public AlbumsPayload() {
    }
    
    public AlbumsPayload(Integer userId, String title) {
        this.userId = userId;
        this.title = title;
    }
    
    public AlbumsPayload(Integer userId, Integer id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
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
    
    // toString
    
    @Override
    public String toString() {
        return "AlbumsPayload{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
