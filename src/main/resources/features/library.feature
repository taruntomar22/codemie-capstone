Feature: Library Management

  Scenario: Add a new book
    Given the library is empty
    When I add a book with ISBN "978001", title "Clean Code", and author "Robert C. Martin"
    Then the library should contain a book with ISBN "978001"

  Scenario: Find a book by ISBN
    Given the library contains a book with ISBN "978001", title "Clean Code", and author "Robert C. Martin"
    When I search for the book with ISBN "978001"
    Then the book "Clean Code" should be found

  Scenario: Search for a book that does not exist
    Given the library is empty
    When I search for the book with ISBN "999999"
    Then no book should be found

  Scenario: Borrow an available book
    Given the library contains an available book with ISBN "978001"
    When I borrow the book with ISBN "978001"
    Then the book should not be available

  Scenario: Return a borrowed book
    Given the library contains a borrowed book with ISBN "978001"
    When I return the book with ISBN "978001"
    Then the book should be available

  Scenario: Reject a duplicate ISBN
    Given the library contains a book with ISBN "978001", title "Clean Code", and author "Robert C. Martin"
    When I try to add a book with ISBN "978001", title "Duplicate", and author "Someone Else"
    Then the book should be rejected as a duplicate

  Scenario: Return a book that is already available
    Given the library contains an available book with ISBN "978001"
    When I try to return the book with ISBN "978001"
    Then the return should fail

  Scenario: Search books by title
    Given the library contains a book with ISBN "978001", title "Clean Code", and author "Robert C. Martin"
    When I search for books with title containing "clean"
    Then 1 book should be found by title

  Scenario: Search books by author
    Given the library contains a book with ISBN "978001", title "Clean Code", and author "Robert C. Martin"
    When I search for books with author containing "martin"
    Then 1 book should be found by author