package modal;


import services.UserService;


import java.util.List;
import java.util.Scanner;

public class Librarian extends User {
    private UserService userService;
    private Scanner scanner;

    public Librarian(int userId, String fullname, UserType userType, String email, String password, UserService userService) {
        super(userId, fullname, userType, email, password);
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public Librarian(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public Librarian() {
        this.scanner = new Scanner(System.in);
    }



    public void editUsers() {
        List<User> users = userService.getUsers();
        if (users == null) {
            System.out.println("Users list is not initialized.");
            return;
        }

        System.out.println("Select an operation:");
        System.out.println("1. Remove User");
        System.out.println("2. Change User Type");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {


            case 1:
                removeUser(users);
                break;
            case 2:
                changeUserType(users);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private void removeUser(List<User> users) {
        System.out.println("Select a user ID to remove:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", Full Name: " + user.getFullname());
        }

        System.out.print("Enter the ID of the user to remove: ");
        int userIdToRemove = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for (User user : users) {
            if (user.getUserId() == userIdToRemove) {
                found = true;
                System.out.println("Are you sure you want to remove user '" + user.getFullname() + "'? (Y/N)");
                String confirm = scanner.nextLine().trim().toUpperCase();
                if (confirm.equals("Y")) {
                    users.remove(user);
                    System.out.println("User removed successfully.");
                } else {
                    System.out.println("User removal canceled.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("User with ID " + userIdToRemove + " not found.");
        }
    }

    private void changeUserType(List<User> users) {
        System.out.println("Select a user ID to change User Type:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", Full Name: " + user.getFullname() +
                    ", User Type: " + user.getUserType());
        }

        System.out.print("Enter the ID of the user to change userType: ");
        int userIdToChange = scanner.nextInt();
        scanner.nextLine(); // Consume newline after nextInt()

        boolean found = false;
        for (User user : users) {
            if (user.getUserId() == userIdToChange) {
                found = true;
                System.out.println("Current User Type: " + user.getUserType());
                System.out.print("Enter new userType (LIBRARIAN, STAFF, STUDENT, STANDARD): ");
                UserType newType = UserType.valueOf(scanner.nextLine().toUpperCase());

                System.out.println("Are you sure you want to change userType of user '" + user.getFullname() + "' to " + newType + "? (Y/N)");
                String confirm = scanner.nextLine().trim().toUpperCase();
                if (confirm.equals("Y")) {
                    user.setUserType(newType);
                    System.out.println("User type changed successfully.");
                } else {
                    System.out.println("User type change canceled.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("User with ID " + userIdToChange + " not found.");
        }
    }
}
