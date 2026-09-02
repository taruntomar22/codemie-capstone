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

    @Test
    void shouldRejectDuplicateIsbn() {

        assertThrows(
                IllegalArgumentException.class,
                () -> library.addBook(
                        new Book("978001", "Another Title", "Another Author")
                )
        );

        assertEquals(1, library.listBooks().size());
    }

    @Test
    void shouldNotReturnBookThatIsAlreadyAvailable() {

        boolean result = library.returnBook("978001");

        assertFalse(result);
    }

    @Test
    void shouldFindBooksByTitle() {

        library.addBook(
                new Book("978002", "Clean Architecture", "Robert C. Martin")
        );

        List<Book> books = library.findBooksByTitle("clean");

        assertEquals(2, books.size());
    }

    @Test
    void shouldReturnEmptyListWhenFindBooksByTitleQueryIsNull() {

        assertDoesNotThrow(
                () -> {
                    List<Book> books = library.findBooksByTitle(null);
                    assertNotNull(books);
                    assertEquals(0, books.size());
                }
        );
    }

    @Test
    void shouldFindBooksByAuthor() {

        library.addBook(
                new Book("978002", "Effective Java", "Joshua Bloch")
        );

        List<Book> books = library.findBooksByAuthor("martin");

        assertEquals(1, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenFindBooksByAuthorQueryIsNull() {

        assertDoesNotThrow(
                () -> {
                    List<Book> books = library.findBooksByAuthor(null);
                    assertNotNull(books);
                    assertEquals(0, books.size());
                }
        );
    }

    @Test
    void shouldListOnlyAvailableBooks() {

        library.addBook(
                new Book("978002", "Effective Java", "Joshua Bloch")
        );

        library.borrowBook("978001");

        List<Book> available = library.listAvailableBooks();

        assertEquals(1, available.size());
        assertEquals("978002", available.get(0).getIsbn());
    }

    @Test
    void shouldListOnlyBorrowedBooks() {

        library.addBook(
                new Book("978002", "Effective Java", "Joshua Bloch")
        );

        library.borrowBook("978001");

        List<Book> borrowed = library.listBorrowedBooks();

        assertEquals(1, borrowed.size());
        assertEquals("978001", borrowed.get(0).getIsbn());
    }
}
