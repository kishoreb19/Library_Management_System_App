package com.example.lms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btn3,L_btn;
    EditText L_email,L_pwd;
    TextView L_reg;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn3 = (Button) findViewById(R.id.bck_btn);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        L_reg = (TextView) findViewById(R.id.L_reg);
        L_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegActivity.class);
                startActivity(i);
                finish();
            }
        });
        //Dropdown menu code
        Spinner spinner_login = findViewById(R.id.spinner);
        String arr[] = {"Libranian","Student","Faculty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_spinner,arr);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinner_login.setAdapter(adapter);
        spinner_login.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = spinner_login.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //end
        L_btn = (Button) findViewById(R.id.L_btn);
        L_email = (EditText) findViewById(R.id.L_email);
        L_pwd = (EditText) findViewById(R.id.L_pwd);
        L_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = L_email.getText().toString();
                String pwd = L_pwd.getText().toString();
                UserDatabase db = new UserDatabase(getApplicationContext());
                if(type.length() == 0 || emailid.length() == 0 || pwd.length() == 0){
                    Toast.makeText(LoginActivity.this, "Please fill all the details !", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(db.checkRecord(type,emailid,pwd)){
                        if(type == "Libranian"){
                            startActivity(new Intent(LoginActivity.this,LibranianAct.class));
                            Toast.makeText(LoginActivity.this, "Logged In Successfully !", Toast.LENGTH_SHORT).show();
                        }
                        else if(type == "Student" || type == "Faculty"){
                            Toast.makeText(LoginActivity.this, "Comming Soon !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Failed !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}