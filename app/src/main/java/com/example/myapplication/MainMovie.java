package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainMovie extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private Button buttonGoToCart;
    private Button buttonBackToDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviemain);

        recyclerView = findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieList = new ArrayList<>();
        loadMovies();

        movieAdapter = new MovieAdapter(this, movieList);
        recyclerView.setAdapter(movieAdapter);

        buttonGoToCart = findViewById(R.id.buttonGoToCart);
        buttonGoToCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainMovie.this, CartActivity.class);
            startActivity(intent);
        });

        buttonBackToDashboard = findViewById(R.id.buttonBackToDashboard);
        buttonBackToDashboard.setOnClickListener(v -> {
            // Start the dashboard activity
            Intent intent = new Intent(MainMovie.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optionally finish the current activity if you don't want it in the back stack
        });
    }

    private void loadMovies() {
        movieList.add(new Movie(1, "Cinderella", "Fantasy", 450, R.drawable.cinderella));
        movieList.add(new Movie(2, "Cars", "Animation", 500, R.drawable.cars));
        movieList.add(new Movie(3, "The Lion King", "Adventure", 550, R.drawable.lion_king));
        movieList.add(new Movie(4, "A Dog's Purpose", "Fantasy", 300, R.drawable.a_dog_purpose));
        movieList.add(new Movie(5, "Beauty And The Beast", "Fantasy", 650, R.drawable.beauty_and_the_beast));
        movieList.add(new Movie(6, "Frozen 1", "Family/Musical ", 550, R.drawable.frozen));
        movieList.add(new Movie(7, "How to train your dragon", "Adventure", 650, R.drawable.how_to_train_your_dragon));
        movieList.add(new Movie(8, "Sea beast ", "Adventure", 550, R.drawable.sea_beast));
        movieList.add(new Movie(9, "The Lord of the Rings", "Adventure", 550, R.drawable.lord_of_the_rings));
        movieList.add(new Movie(10, "Wonder Woman", "Adventure", 750, R.drawable.wonder_women));
    }
}

