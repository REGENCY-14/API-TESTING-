package com.api.automation.pages;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Endpoints;

/**
 * Page Object for Posts API endpoints
 * Contains all post-related API interactions and assertions
 */
public class PostsPage extends BasePage {
    
    public PostsPage(RequestSpecification requestSpec) {
        super(requestSpec);
    }
    
    /**
     * Retrieves all posts
     * @return Response object
     */
    public Response getAllPosts() {
        return get(Endpoints.POSTS);
    }
    
    /**
     * Retrieves a post by ID
     * @param postId The post ID
     * @return Response object
     */
    public Response getPostById(int postId) {
        return get(Endpoints.POSTS + "/" + postId);
    }
    
    /**
     * Retrieves posts filtered by user ID
     * @param userId The user ID
     * @return Response object
     */
    public Response getPostsByUserId(int userId) {
        return get(Endpoints.POSTS, "userId", userId);
    }
    
    /**
     * Retrieves comments for a specific post
     * @param postId The post ID
     * @return Response object
     */
    public Response getPostComments(int postId) {
        return get(Endpoints.POSTS + "/" + postId + "/comments");
    }
    
    /**
     * Creates a new post
     * @param userId The user ID
     * @param title The post title
     * @param body The post body
     * @return Response object
     */
    public Response createPost(int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, title, body);
        return post(Endpoints.POSTS, payload);
    }
    
    /**
     * Updates an existing post
     * @param postId The post ID
     * @param userId The user ID
     * @param title The post title
     * @param body The post body
     * @return Response object
     */
    public Response updatePost(int postId, int userId, String title, String body) {
        String payload = String.format("{\n" +
                "  \"userId\": %d,\n" +
                "  \"id\": %d,\n" +
                "  \"title\": \"%s\",\n" +
                "  \"body\": \"%s\"\n" +
                "}", userId, postId, title, body);
        return put(Endpoints.POSTS + "/" + postId, payload);
    }
    
    /**
     * Deletes a post
     * @param postId The post ID
     * @return Response object
     */
    public Response deletePost(int postId) {
        return delete(Endpoints.POSTS + "/" + postId);
    }
    
    /**
     * Verifies that all posts response is valid
     */
    public PostsPage verifyGetAllPostsResponse() {
        getAllPosts()
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("[0].userId", notNullValue())
                .body("[0].id", notNullValue())
                .body("[0].title", notNullValue())
                .body("[0].body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get post by ID response is valid
     * @param postId The post ID
     */
    public PostsPage verifyGetPostByIdResponse(int postId) {
        getPostById(postId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("userId", notNullValue())
                .body("id", equalTo(postId))
                .body("title", notNullValue())
                .body("body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that get posts by user ID response is valid
     * @param userId The user ID
     */
    public PostsPage verifyGetPostsByUserIdResponse(int userId) {
        getPostsByUserId(userId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("userId", everyItem(equalTo(userId)));
        return this;
    }
    
    /**
     * Verifies that get post comments response is valid
     * @param postId The post ID
     */
    public PostsPage verifyGetPostCommentsResponse(int postId) {
        getPostComments(postId)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("$", hasSize(greaterThan(0)))
                .body("postId", everyItem(equalTo(postId)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].body", notNullValue());
        return this;
    }
    
    /**
     * Verifies that create post response is valid
     * @param userId The user ID
     * @param title The post title
     * @param body The post body
     */
    public PostsPage verifyCreatePostResponse(int userId, String title, String body) {
        createPost(userId, title, body)
                .then()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("userId", equalTo(userId))
                .body("title", equalTo(title))
                .body("body", equalTo(body));
        return this;
    }
    
    /**
     * Verifies that update post response is valid
     * @param postId The post ID
     * @param title The post title
     * @param body The post body
     */
    public PostsPage verifyUpdatePostResponse(int postId, String title, String body) {
        updatePost(postId, 1, title, body)
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("id", equalTo(postId))
                .body("title", equalTo(title))
                .body("body", equalTo(body));
        return this;
    }
    
    /**
     * Verifies that delete post response is valid
     * @param postId The post ID
     */
    public PostsPage verifyDeletePostResponse(int postId) {
        deletePost(postId)
                .then()
                .statusCode(200);
        return this;
    }
}
