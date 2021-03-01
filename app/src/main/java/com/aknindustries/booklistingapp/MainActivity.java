package com.aknindustries.booklistingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListItemClicked {

    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        fetchBooksWithQuery("android");
    }

    public void searchQuery(View view) {
        EditText editText = findViewById(R.id.text_field);
        String query = editText.getText().toString();
        if (query.isEmpty()) {
            Log.d("Empty", "Please provide a query");
            return;
        }
        fetchBooksWithQuery(query);
    }

    private void fetchBooksWithQuery(String query) {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&maxResults=10";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        ArrayList<Book> books = new ArrayList<>();
                        JSONArray booksData = response.getJSONArray("items");
                        for (int i = 0; i < booksData.length(); i++) {
                            JSONObject bookData = (JSONObject) booksData.get(i);
                            Book book = createBookFromJson(bookData);
                            books.add(book);
                        }
                        BooksAdaptor booksAdaptor = new BooksAdaptor(books, MainActivity.this, MainActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(booksAdaptor);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.d("Error", error.getMessage())
        );
        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    private Book createBookFromJson(JSONObject bookData) {
        try {
            JSONObject volumeInfo = bookData.getJSONObject("volumeInfo");
            String id = bookData.getString("id");
            String title, publishedDate, imageUrl, url;
            int pageCount;
            if (volumeInfo.has("title")) {
                title = volumeInfo.getString("title");
            } else {
                title = "Unnamed Book";
            }
            if (volumeInfo.has("publishedDate")) {
                publishedDate = volumeInfo.getString("publishedDate");
            } else {
                publishedDate = "Unknown";
            }
            if (volumeInfo.has("imageLinks")) {
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                if (imageLinks.has("thumbnail")) {
                    imageUrl = imageLinks.getString("thumbnail");
                } else {
                    imageUrl = "http://ctt.trains.com/sitefiles/images/no-preview-available.png";
                }
            } else {
                imageUrl = "http://ctt.trains.com/sitefiles/images/no-preview-available.png";
            }
            if (volumeInfo.has("pageCount")) {
                pageCount = volumeInfo.getInt("pageCount");
            } else {
                pageCount = 100;
            }
            if (volumeInfo.has("infoLink")) {
                url = volumeInfo.getString("infoLink");
            } else {
                url = "www.google.co.in";
            }
            return new Book(id, title, publishedDate, pageCount, imageUrl, url);
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
            return null;
        }
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}