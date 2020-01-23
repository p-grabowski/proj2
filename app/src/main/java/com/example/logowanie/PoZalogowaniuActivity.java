package com.example.logowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PoZalogowaniuActivity extends AppCompatActivity {
    DataBaseHelper base;
    TextView textView;
    String username;
    String isadmin;
    Integer id;
    Button wyloguj,dodaj,zmien_haslo;
    ListView mojaLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pozalogowaniu);
        base = new DataBaseHelper(this);
        textView = findViewById(R.id.text);
        wyloguj=findViewById(R.id.button);
        dodaj=findViewById(R.id.po_dodaj);
        zmien_haslo=findViewById(R.id.po_zmien_haslo);
        mojaLista=findViewById(R.id.lista);


        id = getIntent().getIntExtra("id",0);//odebranie danych z poprzedniego activity

        username=base.selectUserById(id).getString(1);//pobranie username by id z bazy


        if (base.checkIsAdmin(base.selectUserById(id).getString(1))){
            wypelnijliste();//lista tylko dla admina
            dodaj.setEnabled(true);// jesli admin butto is activ- zrob przycisk aktywny
            isadmin="Admin";
        }else {
            dodaj.setEnabled(false);
            isadmin = "User";//szary- nie mozna uzyc
        }

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoZalogowaniuActivity.this, dodajUzywkownika.class);//utworzenie przejscia do nowego activity
                intent.putExtra("id", id); //przekazanie danych do noweo activity
                finish();
                startActivity(intent); //run
            }
        });

        zmien_haslo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoZalogowaniuActivity.this, ZmienHasloActivity.class);//utworzenie przejscia do nowego activity
                intent.putExtra("id", id); //przekazanie danych do noweo activity
                finish();
                startActivity(intent); //run
            }
        });


        mojaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idx) {
                Intent intent = new Intent(PoZalogowaniuActivity.this, zmianaActivity.class);
                intent.putExtra("edit", position);//przekazanie danych z pozytion na nowe okno
                //intent.putExtra("id", id); //przekazanie danych do noweo activity
                startActivity(intent);
                finish();
            }
        });

        /*
                listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AfterLoginActivity.this, EditUserActivity.class);
                intent.putExtra("edit", position);
                startActivity(intent);
                finish();
            }
        });
         */

        textView.setText(isadmin + ": " + username);//przypisanie tesktu do textView(rola uzytkownika + nazwa uzytkonika)
        wyloguj.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();//zamyka aktywnosc intent konczy
        startActivity(new Intent(getApplicationContext(), MainActivity.class));//uruchamia main activity -logowanie
    }
});
    }

    @Override
    public void onBackPressed() {//obsluguje przycisk cofania
        super.onBackPressed();
        finish();//zamyka aktywnosc intent konczy
        startActivity(new Intent(getApplicationContext(), MainActivity.class)); //uruchamia main activity -logowanie
    }

    public void wypelnijliste(){
        Cursor usersCursor = base.pobierzUsers();
        wypelnienielisty PunktyAdapter = new wypelnienielisty(PoZalogowaniuActivity.this, usersCursor);
        mojaLista.setAdapter(PunktyAdapter);
        registerForContextMenu(mojaLista);
    }
}