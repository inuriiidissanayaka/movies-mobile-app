package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public CartAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textTitle.setText(movie.getTitle());
        holder.textGenre.setText(movie.getGenre());
        holder.textPrice.setText("Rs. " + movie.getPrice());
        // Set the image (make sure your images are stored as resource IDs in the database)
        holder.imagePoster.setImageResource(movie.getImageResource());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textGenre, textPrice;
        ImageView imagePoster;

        public CartViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textCartTitle);
            textGenre = itemView.findViewById(R.id.textCartGenre);
            textPrice = itemView.findViewById(R.id.textCartPrice);
            imagePoster = itemView.findViewById(R.id.imageViewCartItem);
        }
    }
}
