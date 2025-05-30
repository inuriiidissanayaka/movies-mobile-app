package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Use consistent database name (SignLog.db instead of MovieCart.db)
    public static final String DATABASE_NAME = "SignLog.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        // Pass the correct database name to SQLiteOpenHelper
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Table and columns for user and cart data
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MOVIE_ID = "movie_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE = "image";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating users table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_EMAIL + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Creating cart table
        String createCartTable = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOVIE_ID + " INTEGER, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_GENRE + " TEXT, "
                + COLUMN_PRICE + " INTEGER, "
                + COLUMN_IMAGE + " INTEGER)";
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Insert user data
    public Boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);

        // Insert the user and check if insertion is successful
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();

        return result != -1; // Return false if insertion failed
    }

    // Check if email already exists in the users table
    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Check if email and password match
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{email, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Add movie to cart
    public void addToCart(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIE_ID, movie.getMovieId());
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_PRICE, movie.getPrice());
        values.put(COLUMN_IMAGE, movie.getImageResource());

        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // Get all cart items
    public List<Movie> getCartItems() {
        List<Movie> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int movieId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));

                Movie movie = new Movie(movieId, title, genre, price, image);
                cartItems.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return cartItems;
    }

    // Remove movie from cart
    public void removeFromCart(int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(movieId)});
        db.close();
    }

    // Clear all items from cart
    public void clearAllCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }
}
