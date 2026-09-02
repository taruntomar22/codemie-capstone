package com.example.library;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryStepDefinitions {

    private Library library;
    private Book foundBook;
    private List<Book> foundBooks;
    private boolean addRejected;
    private boolean returnResult;

    @Given("the library is empty")
    public void theLibraryIsEmpty() {

        library = new Library();
    }

    @When("I add a book with ISBN {string}, title {string}, and author {string}")
    public void iAddABook(
            String isbn,
            String title,
            String author) {

        library.addBook(
                new Book(isbn, title, author)
        );
    }

    @Then("the library should contain a book with ISBN {string}")
    public void theLibraryShouldContainBook(String isbn) {

        Book book =
                library.findBookByIsbn(isbn);

        assertNotNull(book);
    }

    @Given("the library contains a book with ISBN {string}, title {string}, and author {string}")
    public void libraryContainsBook(
            String isbn,
            String title,
            String author) {

        library = new Library();

        library.addBook(
                new Book(isbn, title, author)
        );
    }

    @When("I search for the book with ISBN {string}")
    public void iSearchForBook(String isbn) {

        foundBook =
                library.findBookByIsbn(isbn);
    }

    @Then("the book {string} should be found")
    public void theBookShouldBeFound(String title) {

        assertNotNull(foundBook);
        assertEquals(title, foundBook.getTitle());
    }

    @Then("no book should be found")
    public void noBookShouldBeFound() {

        assertNull(foundBook);
    }

    @Given("the library contains an available book with ISBN {string}")
    public void libraryContainsAvailableBook(String isbn) {

        library = new Library();

        library.addBook(
                new Book(
                        isbn,
                        "Clean Code",
                        "Robert C. Martin"
                )
        );
    }

    @When("I borrow the book with ISBN {string}")
    public void iBorrowTheBook(String isbn) {

        boolean result =
                library.borrowBook(isbn);

        assertTrue(result);
    }

    @Then("the book should not be available")
    public void theBookShouldNotBeAvailable() {

        Book book =
                library.findBookByIsbn("978001");

        assertNotNull(book);
        assertFalse(book.isAvailable());
    }

    @Given("the library contains a borrowed book with ISBN {string}")
    public void libraryContainsBorrowedBook(String isbn) {

        library = new Library();

        library.addBook(
                new Book(
                        isbn,
                        "Clean Code",
                        "Robert C. Martin"
                )
        );

        library.borrowBook(isbn);
    }

    @When("I return the book with ISBN {string}")
    public void iReturnTheBook(String isbn) {

        boolean result =
                library.returnBook(isbn);

        assertTrue(result);
    }

    @Then("the book should be available")
    public void theBookShouldBeAvailable() {

        Book book =
                library.findBookByIsbn("978001");

        assertNotNull(book);
        assertTrue(book.isAvailable());
    }

    @When("I try to add a book with ISBN {string}, title {string}, and author {string}")
    public void iTryToAddABook(
            String isbn,
            String title,
            String author) {

        try {
            library.addBook(new Book(isbn, title, author));
            addRejected = false;
        } catch (IllegalArgumentException e) {
            addRejected = true;
        }
    }

    @Then("the book should be rejected as a duplicate")
    public void theBookShouldBeRejectedAsADuplicate() {

        assertTrue(addRejected);
    }

    @When("I try to return the book with ISBN {string}")
    public void iTryToReturnTheBook(String isbn) {

        returnResult = library.returnBook(isbn);
    }

    @Then("the return should fail")
    public void theReturnShouldFail() {

        assertFalse(returnResult);
    }

    @When("I search for books with title containing {string}")
    public void iSearchForBooksWithTitleContaining(String query) {

        foundBooks = library.findBooksByTitle(query);
    }

    @Then("{int} book should be found by title")
    public void nBookShouldBeFoundByTitle(int count) {

        assertEquals(count, foundBooks.size());
    }

    @When("I search for books with author containing {string}")
    public void iSearchForBooksWithAuthorContaining(String query) {

        foundBooks = library.findBooksByAuthor(query);
    }

    @Then("{int} book should be found by author")
    public void nBookShouldBeFoundByAuthor(int count) {

        assertEquals(count, foundBooks.size());
    }

    @Then("{int} book should be listed as available")
    public void nBookShouldBeListedAsAvailable(int count) {

        assertEquals(count, library.listAvailableBooks().size());
    }
}
