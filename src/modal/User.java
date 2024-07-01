package modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private int userId;
    private String fullname;
    private UserType userType;
    private String email;
    private String password;
    private List<Book> borrowedBooks;
    private Reader reader;
    public static User currentUser;

    public User(int userId, String fullname, UserType userType,
                String email, String password) {
        this.userId = userId;
        this.fullname = fullname;
        this.userType = userType;
        this.email = email;
        this.password = password;

    }

    public User() {
    }



    public User(int nextUserId, String fullName, UserType userType, String email, String password, String phoneNumber) {
    }

    public User(int i, String fullname, String email, String phoneNumber, UserType userType, String password) {
    }

    public Reader getReader() {

        return reader;
    }

    public void setReader(Reader reader) {

        this.reader = reader;
    }

    public User(int userId, String fullname, UserType userType, String email, String password, List<Book> borrowedBooks, Reader reader) {
        this.userId = userId;
        this.fullname = fullname;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.borrowedBooks = borrowedBooks != null ? borrowedBooks : new ArrayList<>();
        this.reader=reader;
    }
    public void profile() {
        System.out.println("USER PROFILE");
        System.out.println("User Id: " + userId);
        System.out.println("Full Name: '" + fullname + "'");
        System.out.println("User Type: " + userType);
        System.out.println("Email: '" + email + "'");
        System.out.println("Password: '" + password + "'");

        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println(book);
            }
        }
    }

    public User(List<Book> borrowedBooks) {

        this.borrowedBooks = borrowedBooks;
    }

    public static User getCurrentUser() {

        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {

        User.currentUser = currentUser;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    public List<Book> getBorrowedBooks() {

        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {

        this.borrowedBooks = borrowedBooks;
    }



    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(fullname, user.fullname) && userType == user.userType && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(borrowedBooks, user.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fullname, userType, email, password, borrowedBooks);
    }

    @Override
    public String toString() {
        return "User{" +
                "User Id:" + userId +
                ", Full Name:'" + fullname + '\'' +
                ", User Type:" + userType +
                ", Email:'" + email + '\'' +
                ", Password:'" + password + '\'' +
                '}';
    }
}






