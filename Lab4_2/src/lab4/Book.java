package lab4;

import javax.swing.JTextField;

public class Book {
    private String id, title, author, year, publisher, pages, price, isbn;

    public Book(String[] data) {
        this.id = data[0];
        this.title = data[1];
        this.author = data[2];
        this.year = data[3];
        this.publisher = data[4];
        this.pages = data[5];
        this.price = data[6];
        this.isbn = data[7];
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getPublisher() { return publisher; }
    public String getPages() { return pages; }
    public String getPrice() { return price; }
    public String getIsbn() { return isbn; }

    public void update(JTextField[] data) {
        this.id = data[0].getText();
        this.title = data[1].getText();
        this.author = data[2].getText();
        this.year = data[3].getText();
        this.publisher = data[4].getText();
        this.pages = data[5].getText();
        this.price = data[6].getText();
        this.isbn = data[7].getText();
    }

    public String toCsv() {
        return String.join(";", id, title, author, year, publisher, pages, price, isbn);
    }
}