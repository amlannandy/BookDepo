package com.aknindustries.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class BooksAdaptor extends RecyclerView.Adapter<BookViewHolder> {

    private final ArrayList<Book> books;
    private final ListItemClicked listItemClicked;
    private Context context;

    public BooksAdaptor(ArrayList<Book> books, ListItemClicked listItemClicked, Context context) {
        this.books = books;
        this.listItemClicked = listItemClicked;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        BookViewHolder bookViewHolder = new BookViewHolder(view);
        view.setOnClickListener(v -> this.listItemClicked.onClick(this.books.get(bookViewHolder.getAdapterPosition()).getUrl()));
        return bookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = this.books.get(position);
        holder.title.setText(book.getTitle());
        String publishedOn = "Published on " + book.getPublishedDate();
        holder.publishedDate.setText(publishedOn);
        String numberOfPages = "Number of Pages - " + book.getPageCount();
        holder.pageCount.setText(numberOfPages);
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        Glide.with(this.context)
                .load(book.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }
}

class BookViewHolder extends RecyclerView.ViewHolder {

    final TextView title;
    final TextView publishedDate;
    final TextView pageCount;
    final ImageView imageView;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.title);
        this.publishedDate = itemView.findViewById(R.id.publishedDate);
        this.pageCount = itemView.findViewById(R.id.pageCount);
        this.imageView = itemView.findViewById(R.id.image_view);
    }
}

interface ListItemClicked {

    void onClick(String url);

}