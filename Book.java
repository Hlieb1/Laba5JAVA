package model;

import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String author;
    private final String publisher;
    private final int publishYear;
    private final int pageCount;
    private final double price;

    public Book(String name, String author, String publisher, int publishYear, int pageCount, double price) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.pageCount = pageCount;
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    @Override
    public String toString() {
        return String.format(
                "%-25s │ %-20s │ %-20s │ %4d │ %4d стор. │ %8.2f грн",
                name, author, publisher, publishYear, pageCount, price
        );
    }
}
