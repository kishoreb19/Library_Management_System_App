package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.Adapter.BookIssueAdapter;
import com.example.lms.Adapter.BooksDataSet;

import java.util.ArrayList;

public class IssueBook extends AppCompatActivity {
    ImageView img_btn_bck,img_logout;
    RecyclerView rv_issue;
    BookIssueAdapter adapter;
    ArrayList<BooksDataSet>data= new ArrayList<>();

    TextView head,u_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        img_logout = (ImageView)findViewById(R.id.logout_img1);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IssueBook.this,MainActivity.class));
                Toast.makeText(IssueBook.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
            }
        });
        img_btn_bck = (ImageView)findViewById(R.id.imageView2);
        img_btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        head = (TextView) findViewById(R.id.textView5);
        u_email = (TextView) findViewById(R.id.u_email);
        head.setText("Welcome " + getIntent().getStringExtra("type"));
        u_email.setText(getIntent().getStringExtra("email"));
        UserDatabase db = new UserDatabase(this);
        Cursor c = db.getBooksRecords();
        while(c.moveToNext()){
            data.add(new BooksDataSet(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        }
        rv_issue = (RecyclerView) findViewById(R.id.bookIssue_rv);
        rv_issue.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookIssueAdapter(this,data,getIntent().getStringExtra("email"));
        rv_issue.setAdapter(adapter);
    }
}