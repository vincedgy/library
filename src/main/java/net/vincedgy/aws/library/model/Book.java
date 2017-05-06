package net.vincedgy.aws.library.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by vincent on 05/05/2017.
 */
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;
    String ISBN10;
    String ISBN13;
    String classification;
    String format;
    String pagination;
    Long numberOfPage;
    String dimensions;
    String language;

    @Temporal(TemporalType.DATE)
    private Date publishDate;

    String publishLocation;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN10() {
        return ISBN10;
    }

    public void setISBN10(String ISBN10) {
        this.ISBN10 = ISBN10;
    }

    public String getISBN13() {
        return ISBN13;
    }

    public void setISBN13(String ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public Long getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(Long numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishLocation() {
        return publishLocation;
    }

    public void setPublishLocation(String publishLocation) {
        this.publishLocation = publishLocation;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN10='" + ISBN10 + '\'' +
                ", ISBN13='" + ISBN13 + '\'' +
                ", classification='" + classification + '\'' +
                ", format='" + format + '\'' +
                ", pagination='" + pagination + '\'' +
                ", numberOfPage=" + numberOfPage +
                ", dimensions='" + dimensions + '\'' +
                ", language='" + language + '\'' +
                ", publishDate=" + publishDate +
                ", publishLocation='" + publishLocation + '\'' +
                '}';
    }
}
