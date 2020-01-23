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

public class dodajUzywkownika extends AppCompatActivity {
EditText dodaj_username, dodaj_nazwa;
Button dodaj_zatwierdz;
Button dodaj_anuluj;
RadioGroup radioGroup;
RadioButton radioButton;
int range;
int id;
DataBaseHelper base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_uzywkownika);
        dodaj_username=findViewById(R.id.dodaj_username);//poletext user name
        dodaj_nazwa=findViewById(R.id.dodaj_nazwa);// pole textowe z nick
        dodaj_zatwierdz=findViewById(R.id.dodaj_zatwierdz);//przycik akceptuj
        dodaj_anuluj=findViewById(R.id.dodaj_anuluj);// przycisk cofnij
        base = new DataBaseHelper(this);//z main - init basa
        id = getIntent().getIntExtra("id",0);// odebranie danych z poprzedniego activity albo po wymuszonym albo po przycisku zmien


        dodaj_zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=dodaj_username.getText().toString();// pobiernie tekstu z pola - kontrolki i przypisanie do zmiennej
                String nazwa=dodaj_nazwa.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);
                 if (radioButton.getText().toString().equals("admin")){//rozpoznawanie radiobuttonow po nazwie nie po id
                     range=1;
                 }else if (radioButton.getText().toString().equals("user")){
                     range=0;
                 }
                if (username.equals(null) || nazwa.equals(null)){//calos dodawanie przez negacje (!przed nazwa !0=1)

                    Toast.makeText(dodajUzywkownika.this, "nie moze byc puste", Toast.LENGTH_SHORT).show();//jesli user name lub nazwa sa puste
                }else {
                    if(base.addUser(username, range, nazwa)   ) {
                        Intent intent = new Intent(dodajUzywkownika.this, PoZalogowaniuActivity.class);//utworzenie przejscia do nowego activity
                        intent.putExtra("id", id); //przekazanie danych do noweo activity
                        finish();
                        startActivity(intent);
                        Toast.makeText(dodajUzywkownika.this, "dodano uzywkonika", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(dodajUzywkownika.this, "nie dodano uzywkownika", Toast.LENGTH_SHORT).show();
                }
            }
        });
dodaj_anuluj.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();//zamyka aktywnosc intent konczy
        startActivity(new Intent(getApplicationContext(), PoZalogowaniuActivity.class));//uruchamia noew okno
    }
});
    }
}
/*public class MyAndroidAppActivity extends Activity {

  private RadioGroup radioSexGroup;
  private RadioButton radioSexButton;
  private Button btnDisplay;

  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	addListenerOnButton();

  }

  public void addListenerOnButton() {

	radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
	btnDisplay = (Button) findViewById(R.id.btnDisplay);

	btnDisplay.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {

		        // get selected radio button from radioGroup
			int selectedId = radioSexGroup.getCheckedRadioButtonId();

			// find the radiobutton by returned id
		        radioSexButton = (RadioButton) findViewById(selectedId);

			Toast.makeText(MyAndroidAppActivity.this,
				radioSexButton.getText(), Toast.LENGTH_SHORT).show();

		}

	});

  }
}
*/
