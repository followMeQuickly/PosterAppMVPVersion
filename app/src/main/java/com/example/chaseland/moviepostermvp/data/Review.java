package com.example.chaseland.moviepostermvp.data;

/**
 * Created by chaseland on 2/7/17.
 */

public class Review {
    //todo: add in review details

    private String author;
    private String content;
    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }
}
