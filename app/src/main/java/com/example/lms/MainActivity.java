package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LinearLayout ll;
    Button btn1,btn2,avail_books_btn;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.lgn_btn);
        btn2 = (Button) findViewById(R.id.reg_btn);
        avail_books_btn = (Button)findViewById(R.id.avail_books_btn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainActivity.this,RegActivity.class);
                startActivity(i2);
            }
        });
        avail_books_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,BooksAvailable.class);
                startActivity(i);
            }
        });
        img = (ImageView) findViewById(R.id.imageView);
        Animation scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        Animation translate = AnimationUtils.loadAnimation(this,R.anim.translate);
        ll = (LinearLayout) findViewById(R.id.linearLayout);
        ll.startAnimation(translate);
        img.startAnimation(scale);
//        UserDatabase d = new UserDatabase(this);
//        String s[] = d.getLibranians();
//        CircularEncryption ce = new CircularEncryption();
//        Log.d("EM",ce.circularEncryption(s[1],-3,-5));
    }
}