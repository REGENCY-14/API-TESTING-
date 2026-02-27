# API Testing Framework - Quick Start Guide

## Prerequisites
- Java 17+
- Maven 3.6+
- Allure Command Line 2.20+

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=GetPostTest
```

### Run specific test method
```bash
mvn test -Dtest=GetPostTest#testGetPost
```

## Generating Allure Reports

### Generate and serve Allure report
```bash
allure serve target/allure-results
```

### Generate Allure report to directory
```bash
allure generate target/allure-results -o target/allure-report --clean
```

### View generated report
```bash
allure open target/allure-report
```

## Test Results Location
- Test Results: `target/allure-results/`
- Report: `target/allure-report/`
- Surefire Report: `target/surefire-reports/`

## Project Structure
```
src/
├── test/
│   ├── java/
│   │   └── com/api/automation/
│   │       ├── base/          # Base test class with setup
│   │       ├── tests/         # Test classes (GET, POST, PUT, DELETE)
│   │       └── utils/         # Utility classes (SchemaUtil)
│   └── resources/
│       ├── schemas/          # JSON schemas
│       └── allure.properties  # Allure configuration
```

## SOLID Principles Implementation
- **S**ingle Responsibility: Each test class handles one HTTP method
- **O**pen/Closed: BaseTest is open for extension, closed for modification
- **L**iskov Substitution: All test classes properly extend BaseTest
- **I**nterface Segregation: SchemaUtil provides minimal interface
- **D**ependency Inversion: Tests depend on BaseTest abstraction

## Git Workflow
- `main`: Production-ready code
- `dev`: Development branch with merged features
- `feature/*`: Individual feature branches

Create feature branches for each new feature:
```bash
git checkout -b feature/feature-name
# Make changes
git commit -m "description"
git push -u origin feature/feature-name
# Create PR to dev, review, and merge
```
