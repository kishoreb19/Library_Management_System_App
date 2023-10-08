package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.Adapter.BookIssueHistoryAdapter;
import com.example.lms.Adapter.BookIssueHistoryDataSet;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ImageView img_logout;
    Button records_back;
    TextView records_user_email;
    RecyclerView history_rv;
    ArrayList<BookIssueHistoryDataSet> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        records_user_email = (TextView) findViewById(R.id.records_user_email);
        records_user_email.setText(getIntent().getStringExtra("history_user_email_head"));
        img_logout = (ImageView) findViewById(R.id.logout_img02);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this,MainActivity.class));
                Toast.makeText(HistoryActivity.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
            }
        });
        records_back = (Button) findViewById(R.id.records_back);
        records_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        history_rv = (RecyclerView) findViewById(R.id.history_rv);
        UserDatabase db = new UserDatabase(HistoryActivity.this);
        Cursor c = db.showHistoryRecords();
        while(c.moveToNext()){
            data.add(new BookIssueHistoryDataSet(c.getString(0),c.getString(1),c.getString(2)));
        }
        BookIssueHistoryAdapter bookIssueHistoryAdapter = new BookIssueHistoryAdapter(HistoryActivity.this,data);
        history_rv.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        history_rv.setAdapter(bookIssueHistoryAdapter);

    }
}