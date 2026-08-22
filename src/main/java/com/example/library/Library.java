package com.example.library;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books);
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

        book.returnBook();
        return true;
    }
}