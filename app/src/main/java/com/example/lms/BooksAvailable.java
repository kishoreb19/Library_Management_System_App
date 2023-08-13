package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lms.Adapter.BookIssueAdapter;
import com.example.lms.Adapter.BooksAvailableAdapter;
import com.example.lms.Adapter.BooksDataSet;

import java.util.ArrayList;

public class BooksAvailable extends AppCompatActivity {
    ImageView bck2;
    RecyclerView booksAvailable_rv;
    ArrayList<BooksDataSet> data= new ArrayList<>();
    BooksAvailableAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_available);

        bck2 = (ImageView) findViewById(R.id.bck2);
        bck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        UserDatabase db = new UserDatabase(this);
        Cursor c = db.getBooksRecords();
        while(c.moveToNext()){
            data.add(new BooksDataSet(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        }
        booksAvailable_rv = (RecyclerView) findViewById(R.id.booksAvailable_rv);
        booksAvailable_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BooksAvailableAdapter(this,data);
        booksAvailable_rv.setAdapter(adapter);
    }
}