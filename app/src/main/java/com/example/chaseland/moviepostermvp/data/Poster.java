package com.example.chaseland.moviepostermvp.data;


import static com.example.chaseland.moviepostermvp.R.id.time;

/**
 * Created by chaseland on 12/28/16.
 */

public final class Poster {

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteCount() {
        return voteCount;
    }

    public String getImagePath(){
        return imagePath;
    }

    private final String id;

    private final String title;

    private final String description;

    private final boolean isFavorited;

    private final double voteCount;

    private final String releaseDate;

    private final String imagePath;


    public Poster(String id, String title, String description,
                  boolean isFavorited, double voteCount, String releaseDate, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isFavorited = isFavorited;
        this.voteCount = voteCount;
        this.releaseDate = releaseDate;
        this.imagePath = imagePath;
    }
}
