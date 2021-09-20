package model;

import javax.persistence.*;

@Entity
@NamedNativeQuery(name = "ReturnBookInfo", query = "Select * " +
                          "FROM BOOKS " +
                          "Where ISBN = ?", 
                          resultClass = Book.class)
public class Book {
    /** A unique number associated with the book*/
    @Id
    @Column(nullable = false,length = 13)
    private String ISBN;
    /**The name of the book*/
    @Column(nullable = false, length = 50)
    private String title;
    /**The year the book was published */
    @Column(nullable = false, length = 4)
    private int yearPublished;
    /**The name of the publisher */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_name",nullable = false)
    private Publisher publisherName;

    public Book()
    {}

    public Book(String ISBN, String title, int year, Publisher name )
    {
        this.ISBN = ISBN;
        this.title = title;
        yearPublished = year;
        publisherName = name;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public Publisher getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(Publisher publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String toString()
    {
        return "Title: " + this.getTitle() + "\nYear Published: " + this.getYearPublished()
                + "\nISBN: " + this.getISBN() + "\nPublisher: " + this.getPublisherName();
    }
}
