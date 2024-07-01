package modal;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
private int bookId;
private String title;
private String author;
private Category category;
private int year;
private int pages;
private double price;
private BookStatus bookStatus;
private LocalDate borrowDate;
private LocalDate returnDate;
private LocalDate actualReturnDate;

    public Book(int bookId, String title, String author, Category category,
                int year, int pages, double price, BookStatus bookStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category=category;
        this.year = year;
        this.pages = pages;
        this.price = price;
        this.bookStatus=bookStatus;
    }
    public Book(int bookId, String title, String author, Category category, int year,
                int pages, double price, BookStatus bookStatus, LocalDate borrowDate,
                LocalDate returnDate, LocalDate actualReturnDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.pages = pages;
        this.price = price;
        this.bookStatus = bookStatus;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Book Id:" + bookId +
                ", Title:'" + title + '\'' +
                ", Author:'" + author + '\'' +
                ", Category:" + category +
                ", Year:" + year +
                ", Pages:" + pages +
                ", Price" + price +
                ", Status:" +bookStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && year == book.year && pages == book.pages && Double.compare(price, book.price) == 0 && Objects.equals(title, book.title) && Objects.equals(author, book.author) && category == book.category && bookStatus == book.bookStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, author, category, year, pages, price, bookStatus);
    }
}
