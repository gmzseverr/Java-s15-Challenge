package services;

import modal.Book;
import modal.BookStatus;
import modal.Category;
import modal.User;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookService extends Book {
    private List<Book> books = new ArrayList<>();
    private Scanner scanner;

    public BookService(UserService userService) {
        this.books = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        books.add(new Book(1, "To Kill a Mockingbird", "Harper Lee", Category.FICTION, 1960, 281, 12.90, BookStatus.AVAILABLE));
        books.add(new Book(2, "1984", "George Orwell", Category.SCIENCE_FICTION, 1949, 328, 14.99, BookStatus.AVAILABLE));
        books.add(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald", Category.FICTION, 1925, 180, 10.99, BookStatus.AVAILABLE));
        books.add(new Book(4, "A Brief History of Time", "Stephen Hawking", Category.NON_FICTION, 1988, 256, 18.99, BookStatus.AVAILABLE));
        books.add(new Book(5, "The Road", "Cormac McCarthy", Category.DRAMA, 2006, 287, 16.99, BookStatus.BORROWED));
        books.add(new Book(6, "The Odyssey", "Homer", Category.POETRY, -800, 541, 13.99, BookStatus.AVAILABLE));
        books.add(new Book(7, "The Diary of a Young Girl", "Anne Frank", Category.BIOGRAPHY, 1947, 283, 12.99, BookStatus.AVAILABLE));
        books.add(new Book(8, "The Selfish Gene", "Richard Dawkins", Category.SCIENCE_FICTION, 1976, 360, 17.99, BookStatus.BORROWED));
        books.add(new Book(9, "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", Category.HISTORY, 2011, 443, 19.99, BookStatus.AVAILABLE));
        books.add(new Book(10, "Becoming", "Michelle Obama", Category.BIOGRAPHY, 2018, 448, 22.99, BookStatus.BORROWED));
        books.add(new Book(11, "The Art of War", "Sun Tzu", Category.HISTORY, -500, 273, 9.99, BookStatus.AVAILABLE));
        books.add(new Book(12, "The Catcher in the Rye", "J.D. Salinger", Category.FICTION, 1951, 214, 13.99, BookStatus.BORROWED));
        books.add(new Book(13, "The Alchemist", "Paulo Coelho", Category.DRAMA, 1988, 208, 14.99, BookStatus.BORROWED));
        books.add(new Book(14, "A Song of Ice and Fire", "George R.R. Martin", Category.FICTION, 1996, 694, 24.99, BookStatus.AVAILABLE));
        books.add(new Book(15, "Moby-Dick", "Herman Melville", Category.FICTION, 1851, 635, 17.99, BookStatus.AVAILABLE));
    }

    public void listAllBooks() {
        for (Book book : books) {
            System.out.println("- BOOK ID: " +book.getBookId()+", " +book.getBookStatus()+", " + book.getTitle()+book.getPrice()+", " + book.getCategory()+", " +book.getAuthor()+", " +book.getYear()+", " +book.getPages()) ;
        }
    }
    public void searchBook(String query) {
        boolean foundBook = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                System.out.println(book);
                foundBook = true;
            }
        }
        if (!foundBook) {
            System.out.println("No book found matching the search!");
        }
    }

    public void borrowBook(User user) {
        Scanner scanner = new Scanner(System.in);
        listAllBooks();
        while (true) {
            System.out.print("Enter the book ID or name you want to borrow: ");
            String input = scanner.nextLine().trim();

            Book bookToBorrow = findBook(input);
            if (bookToBorrow == null) {
                System.out.println("Book with ID or name '" + input + "' not found.");
                continue;
            }

            if (!bookToBorrow.getBookStatus().equals(BookStatus.AVAILABLE)) {
                System.out.println("This book is already borrowed by another person. Please try another book.");
                continue;
            }
            List<Book> borrowedBooks = user.getBorrowedBooks();
            if (borrowedBooks == null) {
                borrowedBooks = new ArrayList<>();
                user.setBorrowedBooks(borrowedBooks);
            }

            if (borrowedBooks.size() >= 5) {
                System.out.println("You can borrow max 5 books.");
                break;
            }

            borrowedBooks.add(bookToBorrow);
            bookToBorrow.setBookStatus(BookStatus.BORROWED);
            bookToBorrow.setBorrowDate(LocalDate.now());
            bookToBorrow.setReturnDate(calculateReturnDate(bookToBorrow));

            System.out.println("You borrowed the book '" + bookToBorrow.getTitle() + "'.");
            System.out.println("Borrow Date: " + bookToBorrow.getBorrowDate());
            System.out.println("Return Date: " + bookToBorrow.getReturnDate());

            System.out.print("Do you want to borrow another book? (Y/N): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println("You borrowed the following books:");
        for (Book book : user.getBorrowedBooks()) {
            System.out.println("- " + book.getTitle());
        }
        System.out.println("Please check the receipt for borrowed books.");
    }




    private Book findBook(String input) {
        for (Book book : books) {
            if (String.valueOf(book.getBookId()).equals(input) || book.getTitle().equalsIgnoreCase(input)) {
                return book;
            }
        }
        return null;
    }

    private LocalDate calculateReturnDate(Book book) {
        if (book.getPages() < 500) {
            return LocalDate.now().plusDays(10);
        } else {
            return LocalDate.now().plusDays(20);
        }
    }
    private boolean isValidCategory(String categoryInput) {
        for (Category category : Category.values()) {
            if (category.name().equals(categoryInput)) {
                return true;
            }
        }
        return false;
    }

    public void addBook() {
        System.out.println("Enter book details:");

        System.out.print("Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Category: ");
        String categoryInput = scanner.nextLine().trim().toUpperCase();
        Category category;
        if (categoryInput.isEmpty() || !isValidCategory(categoryInput)) {
            category = Category.OTHER;
        } else {
            category = Category.valueOf(categoryInput);
        }

        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Pages: ");
        int pages = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Book newBook = new Book(books.size() + 1, title, author, category, year, pages, price, BookStatus.AVAILABLE);
        books.add(newBook);
        System.out.println("Book added successfully!");
    }

    public void removeBook() {
        System.out.println("Select a book to remove:");
       listAllBooks();
        System.out.print("Enter the ID of the book to remove: ");
        int bookIdQuery;
        try {
            bookIdQuery = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid book ID.");
            return;
        }
        Book selectedBook = null;
        for (Book book : books) {
            if (book.getBookId() == bookIdQuery) {
                selectedBook = book;
                break;
            }
        }
        if (selectedBook == null) {
            System.out.println("No book found.");
            return;
        } else {
            System.out.print("Are you sure you want to remove '" + selectedBook.getTitle() + "'? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("Y")) {
                books.remove(selectedBook);
                System.out.println("Book removed successfully.");
            } else if (confirm.equalsIgnoreCase("N")) {
                System.out.println("Removal canceled.");
            }
        }
    }




    public void editBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the title or ID of the book to edit: ");
        String query = scanner.nextLine();

        Book selectedBook = null;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(query) || String.valueOf(book.getBookId()).equals(query)) {
                selectedBook = book;
                break;
            }
        }
        if (selectedBook == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Select an section to edit:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. Year");
        System.out.println("5. Pages");
        System.out.println("6. Price");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Edit title: ");
                String newTitle = scanner.nextLine();
                selectedBook.setTitle(newTitle);
                break;
            case 2:
                System.out.print("Edit author: ");
                String newAuthor = scanner.nextLine();
                selectedBook.setAuthor(newAuthor);
                break;
            case 3:
                System.out.print("Change the Category: ");
                Category newCategory = Category.valueOf(scanner.nextLine().toUpperCase());
                selectedBook.setCategory(newCategory);
                break;
            case 4:
                System.out.print("Edit year: ");
                int newYear = scanner.nextInt();
                selectedBook.setYear(newYear);
                break;
            case 5:
                System.out.print("Edit pages ");
                int newPages = scanner.nextInt();
                selectedBook.setPages(newPages);
                break;
            case 6:
                System.out.print("Edit price: ");
                double newPrice = scanner.nextDouble();
                selectedBook.setPrice(newPrice);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        System.out.println("Book updated successfully:");
        System.out.println(selectedBook);
    }


    public void returnBook(User user) {
        if (user.getBorrowedBooks() == null || user.getBorrowedBooks().isEmpty()) {
            System.out.println("You didn't borrow any book.");
            return;
        }

        System.out.println("Books you borrowed:");
        for (Book book : user.getBorrowedBooks()) {
            System.out.println("- " + book.getTitle() + ": $" + book.getPrice() + " (Book ID: " + book.getBookId() + ")");
            System.out.println("  Borrow Date: " + book.getBorrowDate() + "-- Must Return Date: " + book.getReturnDate());
        }

        System.out.print("Enter the book ID to return: ");
        int bookIdQuery = Integer.parseInt(scanner.nextLine().trim());

        Book bookToReturn = findBook(String.valueOf(bookIdQuery));
        if (bookToReturn == null) {
            System.out.println("Book with ID '" + bookIdQuery + "' not found.");
            return;
        }

        if (!user.getBorrowedBooks().contains(bookToReturn)) {
            System.out.println("You did not borrow this book.");
            return;
        }


        System.out.print("Enter the actual return date (YYYY-MM-DD): ");
        String actualReturnDateString = scanner.nextLine().trim();
        LocalDate actualReturnDate = LocalDate.parse(actualReturnDateString);

        LocalDate returnDate = bookToReturn.getReturnDate();
        long daysOverdue = returnDate.until(actualReturnDate).getDays();
        if (daysOverdue > 0) {
            double overdueFee = daysOverdue * 1.0; // Assuming $1 per day overdue fee
            System.out.println("Overdue days: " + daysOverdue);
            System.out.println("Overdue Fee: $" + overdueFee);
        }

        bookToReturn.setBookStatus(BookStatus.AVAILABLE);
        bookToReturn.setActualReturnDate(actualReturnDate);
        user.getBorrowedBooks().remove(bookToReturn);

        System.out.println("Book returned successfully.");
    }


}
