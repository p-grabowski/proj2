package com.example.logowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZmienHasloActivity extends AppCompatActivity {

    EditText haslo1, haslo2;
    Button zmien;
    DataBaseHelper bazaDanych;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmienhaslo);

        haslo1 = (EditText) findViewById(R.id.haslo1);
        haslo2 = (EditText) findViewById(R.id.haslo2);
        zmien = (Button) findViewById(R.id.zmien);
        id = getIntent().getIntExtra("id",0);


        bazaDanych = new DataBaseHelper(this);
       haslo2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(ZmienHasloActivity.this, "wprowadz takie samo haslo", Toast.LENGTH_SHORT).show();
           }
       });
        zmien.setOnClickListener(new View.OnClickListener() {//button
            @Override
            public void onClick(View v) {

                String haslo1S = haslo1.getText().toString();
                String haslo2S = haslo2.getText().toString();

                if(haslo1S.equals(haslo2S)){//porownanie hasel

                    bazaDanych.upadateUserPass(id, haslo1S);

                    Toast.makeText(ZmienHasloActivity.this, "zmieniles haslo", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ZmienHasloActivity.this, PoZalogowaniuActivity.class);//utworzenie przejscia do nowego activity
                    intent.putExtra("id",id);//przekazanie danych do noweo activity
                    startActivity(intent);                //otwarcie nowego activity
                }else
                    Toast.makeText(ZmienHasloActivity.this, "hasla nie sa takie same", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
