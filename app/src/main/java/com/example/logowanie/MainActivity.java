package com.example.logowanie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText login, password;
    Button submit;
    DataBaseHelper bazaDanych;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.main_username);
        password = findViewById(R.id.main_password);
        submit = findViewById(R.id.main_submit);

        bazaDanych = new DataBaseHelper(this);

        if(bazaDanych.checkUserIsExist("admin")==false) {
            bazaDanych.addUser("admin", 1,"Administrator");
            bazaDanych.addUser("user1", 0, "Uzytkownik 1");
            bazaDanych.addUser("user2", 0, "Uzytkownik 2");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginS = login.getText().toString();  //pobranie loginu z pola Edittext
                String passwordS = password.getText().toString();   //pobranie hasla z pola Edittext

                if(bazaDanych.login(loginS,passwordS)){
                    if(bazaDanych.checkDefaultPass(bazaDanych.selectIdByUsername(loginS))) { //sprawdzenie czy uzytkownik ma domyslne haslo
                        Intent intent = new Intent(MainActivity.this, ZmienHasloActivity.class);//utworzenie przejscia do nowego activity
                        intent.putExtra("id", bazaDanych.selectIdByUsername(login.getText().toString())); //przekazanie danych do noweo activity
                        startActivity(intent); //run
                        finish();
                    }else {
                        Intent intent = new Intent(MainActivity.this, PoZalogowaniuActivity.class);
                        intent.putExtra("id", bazaDanych.selectIdByUsername(login.getText().toString()));
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(MainActivity.this, "zalogowano", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(MainActivity.this, "zly login lub haslo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}