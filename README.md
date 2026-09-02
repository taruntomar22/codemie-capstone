# Library Management System

A small Maven-based Java application demonstrating basic library management functionality with in-memory storage and a simple console interface.

## Features

The current version supports:

- Add a book
  - Enforces **unique ISBNs** (duplicate ISBNs are rejected)
- List all books
- List **available** (not borrowed) books
- Find a book by ISBN
- Search books by:
  - Title
  - Author
- Borrow a book
  - Prevents borrowing a book that is already borrowed
- Return a book
  - Validates return requests (e.g., cannot return a book that is not currently borrowed)

## Technology

- Java (see version note below)
- Maven
- JUnit 5
- Cucumber
- Gherkin

## Architecture

The application currently uses an in-memory collection.

```text
LibraryApplication
        |
        v
     Library
        |
        v
       Book
```

## Setup

### Prerequisites

- **Java 17** (recommended)
- Maven 3.9+

#### Java version note (known mismatch)

The repository currently has a mismatch in build configuration:
- `pom.xml` declares Java **26** via properties, but
- the Maven compiler plugin is configured to compile with Java **17**.

Until the POM is aligned, use **Java 17** to match the compiler configuration and avoid unexpected build behavior.

## Build

```bash
mvn clean package
```

## Test

### Run all tests (JUnit + Cucumber via Surefire)

```bash
mvn test
```

### Run only JUnit tests

If your project separates unit tests by naming conventions (e.g., `*Test`), you can target them explicitly:

```bash
mvn -Dtest="*Test" test
```

### Run only Cucumber tests

If Cucumber runner classes are named with a convention (e.g., `*CucumberTest`), you can run them explicitly:

```bash
mvn -Dtest="*CucumberTest" test
```

If your runner naming differs, adjust the pattern accordingly.

## Run

If the project is configured with an executable main class and/or a Maven plugin for running, you can typically run it via one of the following approaches.

### Option A: Run the packaged JAR (if built as an executable JAR)

```bash
mvn clean package
java -jar target/*.jar
```

### Option B: Run with Maven (if exec plugin is configured)

```bash
mvn exec:java
```

If neither option works, the repository may not yet be configured for direct execution via Maven plugins; in that case, run the main class from your IDE.

## Known Limitations

- **In-memory storage only**: all data is lost when the application stops (no database/persistence).
- **Linear scans**: searching/listing uses simple iteration and may not scale well for large collections.
- **Console-only**: no REST API or GUI.
- **Search null query behavior**: searching by title/author with a `null` query currently throws a `NullPointerException` (no null-safe handling yet).
- No concurrency/thread-safety guarantees.