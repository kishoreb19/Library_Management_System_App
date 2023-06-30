package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lms.Adapter.UserDataAdapter;
import com.example.lms.Adapter.UserDataSet;

import java.util.ArrayList;

public class LibranianAct extends AppCompatActivity {
    ArrayList<UserDataSet> data = new ArrayList<>();
    RecyclerView rv;
    Button bck_btn,book_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libranian);
        UserDatabase obj = new UserDatabase(this);
        Cursor c = obj.getUserRecords();
        while(c.moveToNext()){
            data.add(new UserDataSet(c.getString(0),c.getString(1),c.getString(2)));
        }
        rv = (RecyclerView) findViewById(R.id.users_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        UserDataAdapter adapter = new UserDataAdapter(this,data);
        rv.setAdapter(adapter);
        bck_btn = (Button)findViewById(R.id.back_btn);
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        book_btn = (Button)findViewById(R.id.book_btn);
        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibranianAct.this,BooksAct.class));
            }
        });
    }
}