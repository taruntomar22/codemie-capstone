package com.example.library;

import java.util.List;

public class LibraryApplication {

    public static void main(String[] args) {

        Library library = new Library();

        Book book1 = new Book(
                "978001",
                "Clean Code",
                "Robert C. Martin"
        );

        Book book2 = new Book(
                "978002",
                "Effective Java",
                "Joshua Bloch"
        );

        library.addBook(book1);
        library.addBook(book2);

        System.out.println("===== LIBRARY MANAGEMENT SYSTEM =====");

        System.out.println("\nAll Books:");

        List<Book> books = library.listBooks();

        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("\nSearching for ISBN 978001:");

        Book foundBook = library.findBookByIsbn("978001");

        if (foundBook != null) {
            System.out.println(foundBook);
        }

        System.out.println("\nBorrowing ISBN 978001:");

        boolean borrowed = library.borrowBook("978001");

        System.out.println("Borrow successful: " + borrowed);

        System.out.println("\nBook after borrowing:");

        System.out.println(library.findBookByIsbn("978001"));

        System.out.println("\nReturning ISBN 978001:");

        boolean returned = library.returnBook("978001");

        System.out.println("Return successful: " + returned);

        System.out.println("\nBook after returning:");

        System.out.println(library.findBookByIsbn("978001"));
    }
}