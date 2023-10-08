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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.Adapter.UserDataAdapter;
import com.example.lms.Adapter.UserDataSet;

import java.util.ArrayList;

public class LibranianAct extends AppCompatActivity {
    ArrayList<UserDataSet> data = new ArrayList<>();
    RecyclerView rv;
    Button bck_btn,book_btn,history_btn;
    TextView h_email;
    ImageView img_logout;
    ImageButton location_btn;
    String userEmailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libranian);
        img_logout = (ImageView)findViewById(R.id.logout_img2);
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibranianAct.this,MainActivity.class));
                Toast.makeText(LibranianAct.this, "Logout Successful !", Toast.LENGTH_SHORT).show();
            }
        });
        h_email = (TextView) findViewById(R.id.textView10);
        userEmailId = getIntent().getStringExtra("email");
        h_email.setText(userEmailId);
        UserDatabase obj = new UserDatabase(this);
        Cursor c = obj.getUserRecords();
        CircularEncryption ce = new CircularEncryption();
        while(c.moveToNext()){
            data.add(new UserDataSet(c.getString(0),c.getString(1),ce.circularEncryption(c.getString(2), -3, -5)));
        }
        rv = (RecyclerView) findViewById(R.id.users_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        UserDataAdapter adapter = new UserDataAdapter(this,data);
        rv.setAdapter(adapter);
        bck_btn = (Button)findViewById(R.id.back_btn);
        bck_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
        bck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        book_btn = (Button)findViewById(R.id.book_btn);
        location_btn = (ImageButton)findViewById(R.id.location_btn);
        location_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
        book_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translate));
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibranianAct.this,SetLocationActivity.class));
            }
        });
        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LibranianAct.this,BooksAct.class));
            }
        });
        history_btn = (Button) findViewById(R.id.records);
        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LibranianAct.this,HistoryActivity.class);
                i.putExtra("history_user_email_head",userEmailId);
                startActivity(i);
            }
        });
    }
}