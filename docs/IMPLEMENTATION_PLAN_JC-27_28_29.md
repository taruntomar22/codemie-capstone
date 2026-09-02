# Implementation Plan — JC-27 / JC-28 / JC-29

This document captures the implementation plan only (no functional code changes).

## Scope
- **JC-27**: Enforce unique ISBNs & deterministic catalog behavior
- **JC-28**: Prevent returning a book that was never borrowed
- **JC-29**: Add search/list enhancements (title/author search, availability filtering)

## Current state (baseline)
- `Library` stores books in-memory and allows duplicates.
- `returnBook(isbn)` returns `true` if book exists, regardless of current availability.
- No search beyond `findBookByIsbn` and no availability-filtered lists.

## Proposed changes

### 1) Domain logic updates (`src/main/java/com/example/library/Library.java`)

#### JC-27 — ISBN uniqueness
- Enforce ISBN uniqueness at the time of insertion.
- Preferred behavior: **keep method signatures stable** and throw a well-defined runtime exception.
  - Option A: `addBook(Book)` throws `IllegalArgumentException` if duplicate ISBN.
  - Option B (only if desired): change signature to `boolean addBook(Book)` and return `false` on duplicate.
- Ensure the library state is unchanged on duplicate insertion attempts.

#### JC-28 — return validation
- Update `returnBook(isbn)`:
  - If book not found → `false` (existing behavior)
  - If found and `available == true` → `false` (new behavior)
  - If found and `available == false` → set `available = true`, return `true`

#### JC-29 — search & availability lists
Add methods:
- `List<Book> findBooksByTitle(String query)`
- `List<Book> findBooksByAuthor(String query)`
- `List<Book> listAvailableBooks()`
- (Optional) `List<Book> listBorrowedBooks()`

Notes:
- Matching for title/author is **contains + case-insensitive**.
- Returned lists should be deterministic (preserve insertion order).

### 2) Tests

#### JUnit (`src/test/java/com/example/library/LibraryTest.java`)
Add/adjust tests:
- Duplicate ISBN insertion rejected; size unchanged.
- `returnBook` returns false when returning an already-available book.
- Title search: contains/case-insensitive.
- Author search: contains/case-insensitive.
- `listAvailableBooks` returns only available books after borrow operation.

#### Cucumber (optional)
- Add scenarios for:
  - Duplicate ISBN rejected
  - Return available book fails
  - Search by title/author returns expected results

Files:
- `src/main/resources/features/library.feature`
- `src/test/java/com/example/library/LibraryStepDefinitions.java`

### 3) Traceability updates
- Ensure commits and PR reference Jira keys in messages/description.
- If any Jira issue links are needed (blocks/relates), add them between JC-27/28/29 as appropriate:
  - JC-28 depends on clear catalog identity (JC-27) only if duplicates could affect behavior.

## Suggested sequencing
1. Implement JC-27 (uniqueness) first.
2. Implement JC-28 (return validation).
3. Implement JC-29 (search/list APIs).
4. Update/expand tests after each step.

## Out of scope
- Persistence/database
- REST API layer changes
- Error model/DTOs

