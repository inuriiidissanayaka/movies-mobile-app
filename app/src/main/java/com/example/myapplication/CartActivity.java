package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCart;
    private Button buttonPay, buttonBackToMovies;
    private TextView textTotal;
    private CartDatabaseHelper cartDatabaseHelper;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        buttonPay = findViewById(R.id.buttonPay);
        buttonBackToMovies = findViewById(R.id.buttonBack);
        textTotal = findViewById(R.id.textTotal);

        cartDatabaseHelper = new CartDatabaseHelper(this);

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        updateCart();

        buttonBackToMovies.setOnClickListener(v -> finish());

        buttonPay.setOnClickListener(v -> {
            double totalPrice = calculateTotalPrice();
            if (totalPrice > 0) {
                Toast.makeText(this, "Total payment: Rs. " + totalPrice, Toast.LENGTH_SHORT).show();
                cartDatabaseHelper.clearAllCart();  // Clear all cart items after payment
                updateCart();  // Refresh the cart view
            } else {
                Toast.makeText(this, "No movies selected to pay", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCart() {
        List<Movie> cartItems = cartDatabaseHelper.getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        recyclerViewCart.setAdapter(cartAdapter);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = calculateTotalPrice();
        textTotal.setText("Total: Rs. " + totalPrice);
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        List<Movie> cartItems = cartDatabaseHelper.getCartItems();
        for (Movie movie : cartItems) {
            totalPrice += movie.getPrice();
        }
        return totalPrice;
    }
}

