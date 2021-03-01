package com.aknindustries.booklistingapp;

import java.util.ArrayList;

public class Book {

    private final String id;
    private final String title;
    private final String publishedDate;
    private final int pageCount;
    private final String imageUrl;
    private final String url;

    public Book(String id, String title, String publishedDate, int pageCount, String imageUrl, String url) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getId() {
        return  this.id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", pageCount=" + pageCount +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
