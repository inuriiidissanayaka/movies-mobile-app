package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public ImageView logoutImage;
    public ImageView watchImage;
    public ImageView cartImage;
    public ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust the padding of the main view based on system bars insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize ImageViews
        logoutImage = findViewById(R.id.logoutImage);
        watchImage = findViewById(R.id.watchImage);
        cartImage = findViewById(R.id.cartImage);
        profileImage = findViewById(R.id.profileImage);

        // Check if ImageViews are initialized correctly
        if (logoutImage == null) Log.e("MainActivity", "logoutImage not found");
        if (watchImage == null) Log.e("MainActivity", "watchImage not found");
        if (profileImage == null) Log.e("MainActivity", "profileImage not found");

        // Logout image click listener
        logoutImage.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Clear session data
            editor.apply();

            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

            // Redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity
        });

        // Watch image click listener
        watchImage.setOnClickListener(v -> {
            // Navigate to Movie activity
            Intent intent = new Intent(MainActivity.this, MainMovie.class);
            startActivity(intent);
        });

        // Cart image click listener
        cartImage.setOnClickListener(v -> {
            // Navigate to CartActivity
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Profile image click listener
        profileImage.setOnClickListener(v -> {
            // Check if user is logged in using SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false); // Check if the user is logged in

            if (isLoggedIn) {
                // If logged in, go to ProfileActivity
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else {
                // If not logged in, redirect to LoginActivity
                Toast.makeText(MainActivity.this, "Please log in first", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close MainActivity
            }
        });
    }
}



