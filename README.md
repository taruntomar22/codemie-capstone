# Library Management System

A small Java 17 Maven application demonstrating basic library management functionality.

## Features

The current version supports:

- Add a book
- List books
- Find a book by ISBN
- Borrow a book
- Return a book

## Technology

- Java 17
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