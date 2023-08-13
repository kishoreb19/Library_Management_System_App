package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lms.Adapter.BooksDataAdapter;
import com.example.lms.Adapter.BooksDataSet;

import java.util.ArrayList;

public class BooksAct extends AppCompatActivity {
    EditText bookName,bookAuthor,bookSubject;
    Button addBook;
    LinearLayout ll;
    ImageView img_btn_bck,img_logout;
    RecyclerView books_rv;
    BooksDataAdapter adapter;
    ArrayList<BooksDataSet> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        img_logout = (ImageView)findViewById(R.id.logout_img3);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BooksAct.this,MainActivity.class));
                Toast.makeText(BooksAct.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
            }
        });
        bookName = (EditText)findViewById(R.id.bookName);
        bookAuthor = (EditText)findViewById(R.id.bookAuthor);
        bookSubject = (EditText)findViewById(R.id.bookSubject);
        addBook = (Button) findViewById(R.id.b_add);
        ll = (LinearLayout)findViewById(R.id.linearLayout2);
        ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
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
                if(!bookName.getText().toString().isEmpty() && !bookAuthor.getText().toString().isEmpty() && !bookSubject.getText().toString().isEmpty()) {
                    long x = db.addBooks(bookName.getText().toString(), bookAuthor.getText().toString(), bookSubject.getText().toString());
                    if (x != -1) {
                        Toast.makeText(BooksAct.this, "Added Successfully !", Toast.LENGTH_SHORT).show();
                        UserDatabase dbb = new UserDatabase(BooksAct.this);
                        Cursor c = dbb.getBooksRecords();
                        while (c.moveToNext()) {
                            if (c.isLast()) {
                                data.add(new BooksDataSet(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
                            }
                        }
                        adapter.notifyItemInserted(data.size() - 1);
                        books_rv.scrollToPosition(data.size() - 1);
                    } else {
                        Toast.makeText(BooksAct.this, "Failed to Add !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BooksAct.this, "Please fill all the details !", Toast.LENGTH_SHORT).show();
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