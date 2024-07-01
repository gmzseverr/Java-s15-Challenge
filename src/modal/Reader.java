package modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader extends User {
    private List<Book> borrowedBooks;

    public Reader(int userId, String fullname, UserType userType, String email, String password) {
        super(userId, fullname, userType, email, password);
        borrowedBooks = new ArrayList<>();
    }

    public Reader() {

    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return Objects.equals(borrowedBooks, reader.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), borrowedBooks);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "borrowedBooks=" + borrowedBooks +
                "} " + super.toString();
    }
}

