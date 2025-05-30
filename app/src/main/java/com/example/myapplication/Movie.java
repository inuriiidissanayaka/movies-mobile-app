package com.example.myapplication;

public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int price;
    private int imageResource;
    private boolean selected;

    public Movie(int movieId, String title, String genre, int price, int imageResource) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.imageResource = imageResource;
        this.selected = false;
    }

    // Getters and Setters

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
