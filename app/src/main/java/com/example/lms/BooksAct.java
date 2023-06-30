package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lms.Adapter.BooksDataAdapter;
import com.example.lms.Adapter.BooksDataSet;

import java.util.ArrayList;

public class BooksAct extends AppCompatActivity {
    EditText bookName,bookAuthor,bookSubject;
    Button addBook;
    ImageView img_btn_bck;
    RecyclerView books_rv;
    BooksDataAdapter adapter;
    ArrayList<BooksDataSet> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookName = (EditText)findViewById(R.id.bookName);
        bookAuthor = (EditText)findViewById(R.id.bookAuthor);
        bookSubject = (EditText)findViewById(R.id.bookSubject);
        addBook = (Button) findViewById(R.id.b_add);
        img_btn_bck = (ImageView) findViewById(R.id.img_btn_bck);
        img_btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase db = new UserDatabase(BooksAct.this);
                long x = db.addBooks(bookName.getText().toString(),bookAuthor.getText().toString(),bookSubject.getText().toString());
                if(x !=-1){
                    Toast.makeText(BooksAct.this, "Added Successfully !", Toast.LENGTH_SHORT).show();
                    UserDatabase dbb = new UserDatabase(BooksAct.this);
                    Cursor c = dbb.getBooksRecords();
                    while(c.moveToNext()){
                        if(c.isLast()){
                            data.add(new BooksDataSet(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
                        }
                    }
                    adapter.notifyItemInserted(data.size()-1);
                    books_rv.scrollToPosition(data.size()-1);
                }
                else{
                    Toast.makeText(BooksAct.this, "Failed !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        books_rv = (RecyclerView) findViewById(R.id.books_rv);
        books_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BooksDataAdapter(this,data);
        UserDatabase db = new UserDatabase(this);
        Cursor c = db.getBooksRecords();
        while(c.moveToNext()){
            data.add(new BooksDataSet(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        }
        books_rv.setAdapter(adapter);
    }
}