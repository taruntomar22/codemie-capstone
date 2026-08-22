package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
        library.addBook(
                new Book(
                        "978001",
                        "Clean Code",
                        "Robert C. Martin"
                )
        );
    }

    @Test
    void shouldAddBook() {

        library.addBook(
                new Book(
                        "978002",
                        "Effective Java",
                        "Joshua Bloch"
                )
        );

        List<Book> books = library.listBooks();

        assertEquals(2, books.size());
    }

    @Test
    void shouldFindBookByIsbn() {

        Book book = library.findBookByIsbn("978001");

        assertNotNull(book);
        assertEquals("Clean Code", book.getTitle());
    }

    @Test
    void shouldReturnNullWhenBookDoesNotExist() {

        Book book = library.findBookByIsbn("999999");

        assertNull(book);
    }

    @Test
    void shouldBorrowAvailableBook() {

        boolean result = library.borrowBook("978001");

        assertTrue(result);

        Book book = library.findBookByIsbn("978001");

        assertFalse(book.isAvailable());
    }

    @Test
    void shouldNotBorrowBookThatIsAlreadyBorrowed() {

        library.borrowBook("978001");

        boolean secondBorrow =
                library.borrowBook("978001");

        assertFalse(secondBorrow);
    }

    @Test
    void shouldReturnBorrowedBook() {

        library.borrowBook("978001");

        boolean result =
                library.returnBook("978001");

        assertTrue(result);

        Book book =
                library.findBookByIsbn("978001");

        assertTrue(book.isAvailable());
    }
}