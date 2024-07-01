package services;


import modal.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class UserService extends Methods {
    private List<User> users;
    private Scanner scanner;

    public UserService() {
        this.users = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        users.add(new User(1, "Gamze Sever", UserType.LIBRARIAN, "gmz@gmz", "111"));
        users.add(new User(2, "John Doe", UserType.STANDARD, "1", "111"));
        users.add(new User(3, "Emily Smith", UserType.STAFF, "2", "222"));
        users.add(new User(4, "Michael Johnson", UserType.STANDARD, "michael.johnson@example.com", "password"));
        users.add(new User(5, "Sophia Williams", UserType.STAFF, "sophia.williams@example.com", "password"));
        users.add(new User(6, "David Brown", UserType.STUDENT, "3", "333"));
        users.add(new User(7, "Olivia Garcia", UserType.STUDENT, "olivia.garcia@example.com", "password"));
        users.add(new User(8, "Daniel Martinez", UserType.STUDENT, "daniel.martinez@example.com", "password"));
        users.add(new User(9, "Isabella Miller", UserType.STUDENT, "isabella.miller@example.com", "password"));
        users.add(new User(10, "Emma Wilson", UserType.STUDENT, "emma.wilson@example.com", "password"));

        scanner = new Scanner(System.in);
    }

    public List<User> getUsers() {
        return users;
    }

    public void createNewUser() {
        System.out.println("NEW USER");
        System.out.print("Full Name: ");
        String fullname = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email;
        boolean emailExists;
        do {
            email = scanner.nextLine().trim();
            emailExists = isEmailTaken(email);
            if (emailExists) {
                System.out.println("An account with this email already exists. Please choose another email.");
            }
        } while (emailExists);

        System.out.print("Create a password at least 3 max 10 characters: ");
        String password;
        do {
            password = scanner.nextLine().trim();
            if (password.length() < 3) {
                System.out.println("Password must be at least 3 characters. Please enter again:");
            } else if (password.length() > 10) {
                System.out.println("Password must be at max 10 characters. Please enter again:");
            }
        } while (password.length() < 3 || password.length() > 10);

        System.out.println("Select User Type:");
        System.out.println("1. STANDARD");
        System.out.println("2. STAFF");
        System.out.println("3. STUDENT");
        int userTypeChoice = scanner.nextInt();
        scanner.nextLine();

        UserType userType;
        switch (userTypeChoice) {
            case 1:
                userType = UserType.STANDARD;
                break;
            case 2:
                userType = UserType.STAFF;
                break;
            case 3:
                userType = UserType.STUDENT;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Standard user.");
                userType = UserType.STANDARD;
                break;
        }

        User newUser = new User(nextUserId++, fullname, userType, email, password);
        users.add(newUser);

        System.out.println("Account created successfully. Please login.");
    }


    public User loginCheck(String email, String password) {
        for (User user : users) {
            if (user.getEmail() != null && user.getPassword() != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                setCurrentUser(user);
                return user;
            }
        }
        return null;
    }


    public double calculateFee() {
        if (User.currentUser == null) {
            System.out.println("User cannot be null");
        }

        List<Book> borrowedBooks = User.currentUser.getBorrowedBooks();
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            System.out.println("This user has not borrowed any books.");
            return 0.0;
        }

        double totalFee = 0.0;

        for (Book book : borrowedBooks) {
            double fee = book.getPrice();
            if (User.currentUser.getUserType() == UserType.STANDARD) {
                fee *= 1.0;
            } else if (User.currentUser.getUserType() == UserType.STAFF) {
                fee *= 0.95; //// staff 5% discount
            } else if (User.currentUser.getUserType() == UserType.STUDENT) {
                fee *= 0.90; // student 10% discount ve kategoriye göre
                if (book.getCategory() == Category.HISTORY ||
                        book.getCategory() == Category.TEXTBOOK ||
                        book.getCategory() == Category.ENCYCLOPEDIA ||
                        book.getCategory() == Category.LANGUAGE) {
                    fee *= 0.80;

                }
            }

            totalFee += fee;
        }

        return totalFee;
    }

    public void printReceipt() {
        if (User.currentUser == null) {
            System.out.println("No user logged in.");
            return;
        }

        System.out.println("----- Receipt -----");
        System.out.println("User: " + User.currentUser.getFullname());
        System.out.println("User Type: " + User.currentUser.getUserType());
        System.out.println("Borrowed Books:");
        for (Book book : User.currentUser.getBorrowedBooks()) {
            System.out.println("- " + book.getTitle() + ": $" + book.getPrice());
        }
        System.out.println("Discounts Applied:");
        if (User.currentUser.getUserType() == UserType.STAFF) {
            System.out.println("- 5% Staff Discount");
        } else if (User.currentUser.getUserType() == UserType.STUDENT) {
            System.out.println("- 10% Student Discount");
            System.out.println("- Additional 20% Discount for Students in History, Textbook, Encyclopedia, Language books");
        }
        System.out.println("Total Fee: $" + calculateFee());
        System.out.println("-------------------");
    }

    public void setCurrentUser(User user) {
        User.currentUser = user;
    }

    public void listUsers() {
        System.out.println("Users:");
        for (User user : users) {
                System.out.println("- User Id: " + user.getUserId() +", Full Name: "+user.getFullname()+", Email: "+
                        user.getEmail()+", User Type: "+ user.getUserType());
            }

    }

    @Override
    public Book findBook(String input) {
        return null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return false;
    }
}
