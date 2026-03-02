# REST Assured API Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.0-green.svg)
![JUnit](https://img.shields.io/badge/JUnit-5.11.2-red.svg)
![Allure](https://img.shields.io/badge/Allure-2.27.0-yellow.svg)

A professional REST API automation testing framework built with REST Assured, JUnit 5, and Allure reporting. This framework provides comprehensive test coverage for RESTful APIs with built-in logging, JSON schema validation, and detailed reporting capabilities.

## 📋 Project Overview

This framework demonstrates industry best practices for API testing automation, including:

- **Full CRUD Operations Testing**: Complete coverage for GET, POST, PUT, and DELETE operations
- **JSON Schema Validation**: Ensures API responses match expected schema definitions
- **Request/Response Logging**: Detailed logging for debugging and test analysis
- **Allure Reporting**: Rich, interactive HTML reports with test execution history
- **SOLID Principles**: Clean architecture with reusable base classes and utilities
- **Git Workflow**: Feature branch development with dev/main branch strategy

**Test Endpoint**: [JSONPlaceholder API](https://jsonplaceholder.typicode.com)

**Total Test Coverage**: 20 automated test cases
- ✅ 5 GET endpoint tests
- ✅ 4 POST endpoint tests
- ✅ 5 PUT endpoint tests
- ✅ 6 DELETE endpoint tests

## 🛠️ Tools & Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming language |
| **Maven** | 3.9+ | Build automation and dependency management |
| **REST Assured** | 5.5.0 | REST API testing framework |
| **JUnit 5** | 5.11.2 | Unit testing framework |
| **Allure** | 2.27.0 | Test reporting framework |
| **JSON Schema Validator** | 5.5.0 | API response schema validation |
| **Maven Surefire** | 3.2.5 | Test execution plugin |
| **Allure Maven** | 2.12.0 | Allure report generation plugin |

## 📁 Project Structure

```
API TESTING/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── api/
│       │           └── automation/
│       │               ├── base/
│       │               │   └── BaseTest.java          # Base test configuration
│       │               ├── tests/
│       │               │   ├── GetPostTest.java       # GET endpoint tests
│       │               │   ├── PostTest.java          # POST endpoint tests
│       │               │   ├── PutTest.java           # PUT endpoint tests
│       │               │   └── DeleteTest.java        # DELETE endpoint tests
│       │               └── utils/
│       │                   └── SchemaUtil.java        # Schema validation utility
│       └── resources/
│           ├── schemas/
│           │   └── post-schema.json                   # JSON schema definitions
│           └── allure.properties                      # Allure configuration
├── pom.xml                                            # Maven configuration
├── TESTING_INSTRUCTIONS.md                             # Detailed testing guide
└── README.md                                          # This file
```

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your system:

- **Java Development Kit (JDK) 17** or higher
- **Apache Maven 3.9** or higher
- **Git** (for version control)
- **Allure CLI** (optional, for local report viewing)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd "API TESTING"
   ```

2. **Verify Maven installation**
   ```bash
   mvn -version
   ```

3. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=GetPostTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=GetPostTest#testGetPost
```

### Run Tests with Verbose Output
```bash
mvn test -X
```

### Run Tests and Skip Report Generation
```bash
mvn test -Dmaven.test.failure.ignore=true
```

## 📊 Generating Allure Reports

### Option 1: Generate and Open Report Automatically
```bash
mvn clean test
allure serve target/allure-results
```
This command will:
- Execute all tests
- Generate the Allure report
- Open the report in your default browser

### Option 2: Generate Report Manually
```bash
# Run tests first
mvn clean test

# Generate report
allure generate target/allure-results --clean -o target/allure-report

# Open report in browser
allure open target/allure-report
```

### Install Allure CLI (if not installed)

**Windows (using Scoop)**:
```bash
scoop install allure
```

**macOS (using Homebrew)**:
```bash
brew install allure
```

**Linux (manual installation)**:
```bash
# Download and extract Allure
wget https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.zip
unzip allure-2.27.0.zip -d /opt/
sudo ln -s /opt/allure-2.27.0/bin/allure /usr/bin/allure
```

## 📈 Test Execution Results

After running tests, you can find:

- **Surefire Reports**: `target/surefire-reports/` (XML/TXT format)
- **Allure Results**: `target/allure-results/` (JSON format)
- **Allure Report**: `target/allure-report/` (HTML format)
- **Compiled Classes**: `target/test-classes/`

## 🎯 Key Features

### BaseTest Class
- Centralized test configuration
- Shared RequestSpecification setup
- Base URI configuration
- Request/Response logging filters
- Content-Type management

### Schema Validation
- JSON Schema Draft 7 compliance
- Automatic schema loading from resources
- Detailed validation error messages

### Allure Reporting Features
- Test execution history tracking
- Request/Response attachments
- Feature and Story categorization
- Execution timeline visualization
- Test case descriptions and steps

### Logging Capabilities
- Complete request details (headers, body, method, URI)
- Full response details (status, headers, body, time)
- Console output for debugging
- Allure report attachments

## 🔧 Configuration

### Base URI
Configure in [BaseTest.java](src/test/java/com/api/automation/base/BaseTest.java):
```java
setBaseUri("https://jsonplaceholder.typicode.com");
```

### Allure Properties
Configure in [allure.properties](src/test/resources/allure.properties):
```properties
allure.report.title=API Testing Framework - REST Assured
allure.history.trend.enable=true
allure.execution.enabled=true
```

## 🌿 Git Workflow

### Branches
- `main` - Production-ready code
- `dev` - Development integration branch
- `feature/*` - Individual feature branches

### Development Process
```bash
# Create feature branch
git checkout -b feature/new-feature dev

# Make changes and commit
git add .
git commit -m "Add new feature"

# Push to remote
git push origin feature/new-feature

# Merge to dev
git checkout dev
git merge feature/new-feature
git push origin dev
```

## 📝 Test Examples

### GET Request Test
```java
@Test
@DisplayName("Get single post with response validation")
public void testGetPostWithResponseValidation() {
    Response response = given()
        .spec(requestSpec)
        .when()
        .get("/posts/1")
        .then()
        .statusCode(200)
        .extract()
        .response();
        
    assertEquals(1, response.path("id"));
    assertNotNull(response.path("title"));
}
```

### POST Request Test
```java
@Test
@DisplayName("Create new post")
public void testCreatePost() {
    String requestBody = """
        {
            "title": "foo",
            "body": "bar",
            "userId": 1
        }
        """;
        
    given()
        .spec(requestSpec)
        .body(requestBody)
        .when()
        .post("/posts")
        .then()
        .statusCode(201)
        .body("title", equalTo("foo"));
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Zakaria Osman**

## 📚 Additional Resources

- [REST Assured Documentation](https://rest-assured.io/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Allure Framework Documentation](https://docs.qameta.io/allure/)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)

## 🐛 Known Issues

No known issues at this time. All 20 tests passing successfully.

## ✅ Test Status

| Test Class | Tests | Status |
|------------|-------|--------|
| GetPostTest | 5 | ✅ PASSING |
| PostTest | 4 | ✅ PASSING |
| PutTest | 5 | ✅ PASSING |
| DeleteTest | 6 | ✅ PASSING |
| **Total** | **20** | **✅ ALL PASSING** |

---

**Last Updated**: February 27, 2026  
**Framework Version**: 1.0.0  
**Build Status**: ✅ Passing
