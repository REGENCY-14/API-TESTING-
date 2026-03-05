package com.api.automation.tests.users;

/**
 * Payload class representing a User object
 * Used for request/response mapping in Users API tests
 */
public class UsersPayload {
    
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    
    // Constructors
    
    public UsersPayload() {
    }
    
    public UsersPayload(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }
    
    public UsersPayload(Integer id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }
    
    // Getters and Setters
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    // toString
    
    @Override
    public String toString() {
        return "UsersPayload{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
