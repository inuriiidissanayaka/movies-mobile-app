package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CartDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Moviecart.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MOVIE_ID = "movie_id";  // Corrected column name
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MOVIE_ID + " INTEGER,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_PRICE + " INTEGER,"
                + COLUMN_IMAGE + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Method to add a movie to the cart
    public void addToCart(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getMovieId());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_PRICE, movie.getPrice());
        values.put(COLUMN_IMAGE, movie.getImageResource());  // Assuming the image is stored as a resource ID

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // Method to get all items in the cart
    public List<Movie> getCartItems() {
        List<Movie> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int movieId = cursor.getInt(cursor.getColumnIndex(COLUMN_MOVIE_ID)); // Using correct column name here
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String genre = cursor.getString(cursor.getColumnIndex(COLUMN_GENRE));
                @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                @SuppressLint("Range") int imageResource = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE));

                cartItems.add(new Movie(movieId, title, genre, price, imageResource));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return cartItems;
    }

    // Method to clear all items from the cart
    public void clearAllCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }

    // Method to remove a movie from the cart
    public void removeFromCart(int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, "movie_id=?", new String[]{String.valueOf(movieId)}); // Using correct column name here
        db.close();
    }
}
