

import modal.*;
import services.BookService;
import services.UserService;

import java.util.List;
import java.util.Scanner;



public class Main {
    private static UserService userService = new UserService();
    private static BookService bookService = new BookService(userService); // Inject UserService into BookService
    private static Librarian librarian = new Librarian(userService); // Assuming Librarian needs access to UserService
    private static Reader reader = new Reader(); //
    private static User currentUser;
    private static Scanner scanner = new Scanner(System.in);
    private static final User users =new User();





    public static void main(String[] args) {
        System.out.println("Welcome to Workintech Library System");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();

        welcomeMenu();

    }

    public static void welcomeMenu(){
        System.out.println("WELCOME");
        System.out.println("1. Login");
        System.out.println("2. Create new Account");
        System.out.println("3. Exit");

        int choice= scanner.nextInt();
        switch (choice){
            case 1:
                login();
                break;
            case 2:
                userService.createNewUser();
                login();
                break;
            case 3:
                System.out.println("Good bye!");
                System.exit(0);
        }

    }
    private static void login() {
        scanner.nextLine();

        System.out.println("Enter your email address:");
        String email = scanner.nextLine().trim();

        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        User loggedInUser = userService.loginCheck(email, password);

        if (loggedInUser != null) {
            currentUser = loggedInUser;
            System.out.println("Login successful. Welcome, " + currentUser.getFullname() + ".");
            userMenu();
        } else {
            System.out.println("Login failed. Invalid email or password.");
            System.out.println("If you don't have an account, please Create a New Account.");
            welcomeMenu();
        }
    }





    public static void userMenu(){
        System.out.println("Menu");

        if (currentUser.getUserType() == UserType.LIBRARIAN){
            System.out.println("1. Profile");
            System.out.println("2. All Books");
            System.out.println("3. Search Book");
            System.out.println("4. All Users");
            System.out.println("5. Add Book");
            System.out.println("6. Edit Book");
            System.out.println("7. Remove Book");
            System.out.println("8. Edit Users");
            System.out.println("9. Exit");
        } else {
            System.out.println("1. Profile");
            System.out.println("2. All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("7. Receipt");
            System.out.println("8. Exit");
        }

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                userProfile(currentUser);
                returnToMenu();
                break;
            case 2:
                System.out.println("BOOKS");
                bookService.listAllBooks();
                returnToMenu();
                break;
            case 3:
                performSearch();
                returnToMenu();
                break;
            case 4:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    System.out.println("USERS");
                    userService.listUsers();

                    returnToMenu();
                } else {
                    bookService.borrowBook(currentUser);
                    returnToMenu();
                }
                break;
            case 5:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    bookService.addBook();
                    returnToMenu();
                } else {
                    bookService.returnBook(currentUser);
                    returnToMenu();
                }
                break;
            case 6:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    bookService.editBook();
                    returnToMenu();
                }
                break;
            case 7:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    bookService.removeBook();
                    returnToMenu();
                } else {
                    userService.printReceipt();
                    returnToMenu();
                }
                break;
            case 8:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    librarian.editUsers();
                    returnToMenu();
                }else {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                break;
            case 9:
                if (currentUser.getUserType() == UserType.LIBRARIAN) {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                userMenu();
                break;
        }
    }


    public static void userProfile(User currentUser) {
        System.out.println("USER PROFILE");
        System.out.println("Full Name: " + currentUser.getFullname());
        System.out.println("User Type: " + currentUser.getUserType());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Password: " + currentUser.getPassword());

        List<Book> borrowedBooks = currentUser.getBorrowedBooks();
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            System.out.println("Borrowed Books: None");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle());
            }
        }
    }

    private static void performSearch() {
        System.out.println("Enter the book or author you want to search:");
        String query = scanner.nextLine().trim();

        bookService.searchBook(query);
    }


    public static void returnToMenu() {
        System.out.println("***PRESS 'R' TO RETURN TO MENU PAGE***");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("r")) {
                userMenu();
                return;
            } else {
                System.out.println("Invalid input. Please press 'R' to return to menu.");
            }
        }
    }


}

