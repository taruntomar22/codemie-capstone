package com.example.library;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (findBookByIsbn(book.getIsbn()) != null) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }

        books.add(book);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> findBooksByTitle(String query) {
        if (query == null) {
            return new ArrayList<>();
        }

        String queryLower = query.toLowerCase();
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            String title = book.getTitle();
            if (title == null) {
                continue;
            }

            if (title.toLowerCase().contains(queryLower)) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findBooksByAuthor(String query) {
        if (query == null) {
            return new ArrayList<>();
        }

        String queryLower = query.toLowerCase();
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            String author = book.getAuthor();
            if (author == null) {
                continue;
            }

            if (author.toLowerCase().contains(queryLower)) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> listAvailableBooks() {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> listBorrowedBooks() {
        List<Book> result = new ArrayList<>();

        for (Book book : books) {
            if (!book.isAvailable()) {
                result.add(book);
            }
        }

        return result;
    }

    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    public boolean borrowBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            return false;
        }

        if (!book.isAvailable()) {
            return false;
        }

        book.borrow();
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            return false;
        }

        if (book.isAvailable()) {
            return false;
        }

        book.returnBook();
        return true;
    }
}
