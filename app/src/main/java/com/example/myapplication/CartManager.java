package com.example.myapplication;

import android.content.Context;
import java.util.List;

public class CartManager {

    private Context context;  // Store the context to pass to CartDatabaseHelper

    // Constructor that accepts a context
    public CartManager(Context context) {
        this.context = context;  // Initialize the context
    }

    // Method to add a movie to the cart
    public void addMovieToCart(Movie movie) {
        // Create an instance of CartDatabaseHelper and pass the context
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(context);

        // Call the addToCart method to add the movie to the cart database
        cartDatabaseHelper.addToCart(movie);
    }

    // Method to remove a movie from the cart
    public void removeMovieFromCart(int movieId) {
        // Create an instance of CartDatabaseHelper and pass the context
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(context);

        // Call the removeFromCart method to remove the movie from the cart
        cartDatabaseHelper.removeFromCart(movieId);
    }

    // Method to get all movies from the cart
    public List<Movie> getCartItems() {
        // Create an instance of CartDatabaseHelper and pass the context
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(context);

        // Return the list of cart items from the database
        return cartDatabaseHelper.getCartItems();
    }

    // Method to clear all movies from the cart
    public void clearAllCart() {
        // Create an instance of CartDatabaseHelper and pass the context
        CartDatabaseHelper cartDatabaseHelper = new CartDatabaseHelper(context);

        // Call the clearAllCart method to remove all items from the cart
        cartDatabaseHelper.clearAllCart();
    }
}
