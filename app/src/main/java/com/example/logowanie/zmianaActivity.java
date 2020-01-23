package com.example.logowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class zmianaActivity extends AppCompatActivity {

    EditText username, nazwa;
    Button zatwierdz;
    Button anuluj;
    Button usun;
    RadioGroup radioGroup;
    RadioButton zadmin,zuser,radioButton;
    DataBaseHelper base;
    int id;
    int ranga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zmiana);
        username=findViewById(R.id.zmien_username);
        nazwa=findViewById(R.id.zmien_nazwa);
        zatwierdz=findViewById(R.id.zmien_zatwierdz);//R. zasoby resource!!!
        anuluj=findViewById(R.id.zmien_anuluj);
        zadmin=findViewById(R.id.zadmin);
        zuser=findViewById(R.id.zuser);
usun=findViewById(R.id.zmien_usun);
        base=new DataBaseHelper(this); //inicjalizacja bazy
        id = getIntent().getIntExtra("edit",0);// odebranie danych z poprzedniego activity albo po wymuszonym albo po przycisku zmien
        id =id+1; //id++ sqllite start form 1


        username.setText(base.selectUserById(id).getString(1));
        nazwa.setText(base.selectUserById(id).getString(3));//czwarta kolumna pobiera String

        ranga=base.selectUserById(id).getInt(2);//pobranie rangi uzytkonika z bazy kkolumna 3w select userby id - C-4z bazy

        if (ranga==1){//jesli jest 1 to admin
            zadmin.setChecked(true);
        } else if(ranga==0){
            zuser.setChecked(true);
        } else {
            zuser.setChecked(true);
        }

        username.setEnabled(false);//pole logi

        zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameS=username.getText().toString();// pobiernie tekstu z pola - kontrolki i przypisanie do zmiennej
                String nazwaS=nazwa.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equals("zadmin")){//rozpoznawanie radiobuttonow po nazwie nie po id
                    ranga=1;
                }else if (radioButton.getText().toString().equals("zuser")){
                    ranga=0;
                }
                if (username.equals(null) || nazwa.equals(null)){ //calos dodawanie przez negacje (!przed nazwa !0=1)
                    Toast.makeText(zmianaActivity.this, "nie moze byc puste", Toast.LENGTH_SHORT).show();//jesli user name lub nazwa sa puste
                }else {
                    if(base.upadateUser(id,usernameS,ranga,nazwaS)){
                        Toast.makeText(zmianaActivity.this, "zmininio uzywkonika", Toast.LENGTH_SHORT).show();
                        finish();//zamyka aktywnosc intent konczy
                        startActivity(new Intent(getApplicationContext(), PoZalogowaniuActivity.class));//uruchamia noew okno
                    }
                    else
                        Toast.makeText(zmianaActivity.this, "nie zmieniono uzywkownika", Toast.LENGTH_SHORT).show();
                }
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//zamyka aktywnosc intent konczy
                startActivity(new Intent(getApplicationContext(), PoZalogowaniuActivity.class));//uruchamia noew okno
            }
        });
        usun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (base.deleteUser(id)){//usuwa po kliknieciu
                    Toast.makeText(zmianaActivity.this, "usubniety", Toast.LENGTH_SHORT).show();//jesli usuniety
                    finish();//zamyka aktywnosc intent konczy
                    startActivity(new Intent(getApplicationContext(), PoZalogowaniuActivity.class));//uruchamia noew okno
                }else Toast.makeText(zmianaActivity.this, "nie usubniety", Toast.LENGTH_SHORT).show();//nie wykonalo sie delete user

            }
        });
    }
}
