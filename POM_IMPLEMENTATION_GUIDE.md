## Page Object Model (POM) Implementation Guide

### Overview
This API testing project implements the **Page Object Model (POM)** design pattern for better maintainability, reusability, and scalability of test automation code. The POM pattern encapsulates API interactions and assertions within dedicated page object classes.

### Directory Structure
```
src/test/java/
├── com/api/automation/
│   ├── base/
│   │   └── BaseTest.java
│   ├── pages/                   # Page Objects Package
│   │   ├── BasePage.java        # Base class for all page objects
│   │   ├── AlbumsPage.java
│   │   ├── UsersPage.java
│   │   ├── PostsPage.java
│   │   ├── TodosPage.java
│   │   ├── CommentsPage.java
│   │   └── PhotosPage.java
│   └── tests/                   # Test Classes
│       ├── AlbumsTest.java
│       ├── UsersTest.java
│       ├── PostsTest.java
│       ├── TodosTest.java
│       ├── CommentsTest.java
│       └── PhotosTest.java
└── utils/
    └── Endpoints.java
```

### Key Components

#### 1. **BasePage.java** - Base Class for All Page Objects
The `BasePage` class provides common functionality for all API interactions:
- **HTTP Methods**: `get()`, `post()`, `put()`, `patch()`, `delete()`
- **Query Parameter Support**: Built-in query parameter handling
- **RequestSpecification**: Centralized request specification management

```java
// Example usage in page objects
protected Response get(String endpoint) {
    return requestSpec.when().get(endpoint);
}

protected Response post(String endpoint, String body) {
    return requestSpec.body(body).when().post(endpoint);
}
```

#### 2. **Page Objects** - Endpoint-Specific Classes

Each endpoint has a dedicated page object class that encapsulates:
- **API Calls**: Methods for making requests to specific endpoints
- **Assertions**: Built-in verification methods for response validation
- **Fluent Interface**: Method chaining for readable test code

##### Example: AlbumsPage.java
```java
public class AlbumsPage extends BasePage {
    
    // API Methods
    public Response getAllAlbums() { ... }
    public Response getAlbumById(int albumId) { ... }
    public Response createAlbum(int userId, String title) { ... }
    
    // Assertion Methods
    public AlbumsPage verifyGetAllAlbumsResponse() { ... }
    public AlbumsPage verifyCreateAlbumResponse(int userId, String title) { ... }
}
```

#### 3. **Test Classes** - Simplified Using POM

Tests now use page objects for cleaner, more readable code:

**Before (Without POM):**
```java
@Test
public void testCreateAlbum() {
    String albumPayload = "{ \"userId\": 1, \"title\": \"New Album\" }";
    given(requestSpec)
        .body(albumPayload)
        .when()
        .post(Endpoints.ALBUMS)
        .then()
        .statusCode(201)
        .body("userId", equalTo(1));
}
```

**After (With POM):**
```java
@Test
public void testCreateAlbum() {
    albumsPage.verifyCreateAlbumResponse(1, "New Test Album");
}
```

### Available Page Objects

#### **AlbumsPage**
- `getAllAlbums()` - Retrieve all albums
- `getAlbumById(int id)` - Get album by ID
- `getAlbumsByUserId(int userId)` - Filter albums by user
- `createAlbum(int userId, String title)` - Create new album
- `updateAlbum(int id, int userId, String title)` - Update album
- `patchAlbum(int id, String title)` - Partial update
- `deleteAlbum(int id)` - Delete album
- `verify*Response()` methods - Validation methods

#### **UsersPage**
- `getAllUsers()` - Retrieve all users
- `getUserById(int id)` - Get user by ID
- `getUserByUsername(String username)` - Filter by username
- `getUserByEmail(String email)` - Filter by email
- `getUserPosts(int userId)` - Get user's posts
- `getUserAlbums(int userId)` - Get user's albums
- `createUser(String name, String username, String email)` - Create user
- `verify*Response()` methods - Validation methods

#### **PostsPage**
- `getAllPosts()` - Retrieve all posts
- `getPostById(int id)` - Get post by ID
- `getPostsByUserId(int userId)` - Filter by user
- `getPostComments(int postId)` - Get post comments
- `createPost(int userId, String title, String body)` - Create post
- `updatePost(int id, int userId, String title, String body)` - Update post
- `deletePost(int id)` - Delete post
- `verify*Response()` methods - Validation methods

#### **TodosPage**
- `getAllTodos()` - Retrieve all todos
- `getTodoById(int id)` - Get todo by ID
- `getTodosByUserId(int userId)` - Filter by user
- `getTodosByCompletionStatus(boolean completed)` - Filter by status
- `createTodo(int userId, String title, boolean completed)` - Create todo
- `updateTodo(int id, int userId, String title, boolean completed)` - Update todo
- `deleteTodo(int id)` - Delete todo
- `verify*Response()` methods - Validation methods

#### **CommentsPage**
- `getAllComments()` - Retrieve all comments
- `getCommentById(int id)` - Get comment by ID
- `getCommentsByPostId(int postId)` - Filter by post
- `createComment(int postId, String name, String email, String body)` - Create comment
- `updateComment(int id, int postId, String name, String email, String body)` - Update comment
- `deleteComment(int id)` - Delete comment
- `verify*Response()` methods - Validation methods

#### **PhotosPage**
- `getAllPhotos()` - Retrieve all photos
- `getPhotoById(int id)` - Get photo by ID
- `getPhotosByAlbumId(int albumId)` - Filter by album
- `createPhoto(int albumId, String title, String url, String thumbnailUrl)` - Create photo
- `updatePhoto(int id, int albumId, String title, String url, String thumbnailUrl)` - Update photo
- `deletePhoto(int id)` - Delete photo
- `verify*Response()` methods - Validation methods

### Benefits of Using POM

1. **Maintainability**: Changes to API calls are made in one place (page object) rather than in multiple tests
2. **Readability**: Tests are more readable and business-focused rather than implementation-focused
3. **Reusability**: Common API operations can be reused across multiple tests
4. **Scalability**: Easy to add new tests or extend existing functionality
5. **Separation of Concerns**: API interactions are separated from test logic
6. **Reduced Test Maintenance**: If API changes, only page objects need updating

### How to Use Page Objects in Tests

#### Step 1: Initialize Page Object in Setup
```java
private AlbumsPage albumsPage;

@Override
public void setup() {
    super.setup();
    albumsPage = new AlbumsPage(requestSpec);
}
```

#### Step 2: Use Page Object Methods in Tests
```java
@Test
public void testGetAlbum() {
    albumsPage.verifyGetAlbumByIdResponse(1);
}

@Test
public void testCreateAlbum() {
    albumsPage.verifyCreateAlbumResponse(1, "Test Album");
}
```

#### Step 3: Chain Methods for Complex Scenarios
```java
@Test
public void testMultipleOperations() {
    albumsPage
        .verifyGetAllAlbumsResponse()
        .verifyGetAlbumByIdResponse(1)
        .verifyCreateAlbumResponse(2, "New Album");
}
```

### Best Practices

1. **One Page Object Per Resource**: Each API endpoint/resource has its own page object class
2. **Keep Assertions in Page Objects**: Response validations are handled in page objects, not tests
3. **Meaningful Method Names**: Use descriptive names like `verifyGetAlbumByIdResponse()` instead of `checkResponse()`
4. **Parameterize Test Data**: Pass test data as parameters to page object methods
5. **Use Fluent Interface**: Return `this` from verification methods to allow method chaining
6. **Avoid Test Logic in Page Objects**: Keep page objects focused on API interactions and assertions
7. **DRY Principle**: Extract common patterns into base class methods

### Example: Adding a New Test

To add a new test without modifying the page object:

```java
@Test
@DisplayName("Should update album title")
public void testUpdateAlbumTitle() {
    albumsPage.verifyUpdateAlbumResponse(5, "Updated Title");
}
```

To add new functionality to a page object:

```java
public class AlbumsPage extends BasePage {
    // New method for searching albums
    public Response searchAlbumsByTitle(String title) {
        return get(Endpoints.ALBUMS, "title", title);
    }
    
    // New verification method
    public AlbumsPage verifySearchAlbumsResponse(String title) {
        searchAlbumsByTitle(title)
            .then()
            .statusCode(200)
            .body("$", hasSize(greaterThan(0)))
            .body("title", everyItem(containsString(title)));
        return this;
    }
}
```

### Extending the Framework

#### Adding a New Endpoint
1. Create a new page object class extending `BasePage`
2. Implement API methods for GET, POST, PUT, PATCH, DELETE operations
3. Add verification methods for assertions
4. Create corresponding test class using the page object

#### Adding Helper Methods to BasePage
```java
// In BasePage.java
protected Response getWithHeaders(String endpoint, Map<String, String> headers) {
    RequestSpecification spec = requestSpec;
    for (Map.Entry<String, String> entry : headers.entrySet()) {
        spec = spec.header(entry.getKey(), entry.getValue());
    }
    return spec.when().get(endpoint);
}
```

### Troubleshooting

**Issue**: Tests not finding page object methods
- **Solution**: Ensure page object class is imported correctly and extends `BasePage`

**Issue**: RequestSpecification is null in page object
- **Solution**: Make sure to call `setup()` before using page objects in tests

**Issue**: Payload formatting errors
- **Solution**: Use String.format() for dynamic payload creation (see examples in page objects)

### Future Enhancements

1. Add request/response logging
2. Implement custom assertions
3. Add retry mechanisms for flaky tests
4. Create data builder classes for complex payloads
5. Add API response serialization/deserialization using POJO classes
