package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {
    Button btn4;
    EditText email,name,password;
    TextView R_lgn,head;
    Button reg;
    LinearLayout ll;
    String usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        head = (TextView)findViewById(R.id.textView4);
        head.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
        ll = (LinearLayout)findViewById(R.id.linearLayout4);
        ll.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_right));
        btn4 = (Button) findViewById(R.id.bck_btn);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        R_lgn = (TextView) findViewById(R.id.R_lgn);
        R_lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        Spinner spinner_reg = findViewById(R.id.spinner2);
        String arr[] = {"Librarian","Student","Faculty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_spinner,arr);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner_reg.setAdapter(adapter);


        spinner_reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usertype = spinner_reg.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //end
        email = (EditText) findViewById(R.id.r_email);
        reg =(Button) findViewById(R.id.register_btn);
        name = (EditText) findViewById(R.id.r_name);
        password = (EditText) findViewById(R.id.r_pwd);
        reg = (Button) findViewById(R.id.register_btn);
        CircularEncryption ce = new CircularEncryption();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase d = new UserDatabase(getApplicationContext());
                String emailid = email.getText().toString();
                String user_name = name.getText().toString();
                String user_pwd = password.getText().toString();
                if(emailid.length() ==0 || user_name.length() == 0 || user_pwd.length() == 0){
                    Toast.makeText(RegActivity.this, "Please fill all the details !", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(emailValidator(emailid.toString())) {
                        long x = d.addRecord(usertype, user_name, ce.circularEncryption(emailid, 3, 5), ce.circularEncryption(user_pwd, 3, 5));
                        if (x != -1) {
                            Toast.makeText(RegActivity.this, "Registered Successfully !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegActivity.this, "Registration Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegActivity.this, "Invalid Email !", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    private boolean emailValidator(String e){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if(pattern.matcher(e).matches()){
            return true;
        }else{
            return false;
        }
    }
}