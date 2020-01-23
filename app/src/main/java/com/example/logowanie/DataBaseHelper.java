package com.example.logowanie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_name = "Base.db";
    public static final String Table_nazwa = "users_table";
    public static final String C_1 = "_id";
    public static final String C_2 = "username";
    public static final String C_3 = "password";
    public static final String C_4 = "range";         //1 - admin, 0 - user
    public static final String C_5 = "nazwa";
    public static final String C_6 = "defaultPass";   //1 - wymaga zmiany, 0 - nie wymaga zmian


    public DataBaseHelper(Context context) {
        super(context, DB_name, null, 8);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//tworzenie tabeli
        db.execSQL("CREATE TABLE " + Table_nazwa + "( " +
                C_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_2 + " TEXT not null, " +
                C_3 + " TEXT not null, " +
                C_4 + " INTEGER not null," +
                C_5 + " TEXT not null," +
                C_6 + " INTEGER not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//zmianie w bazie
        db.execSQL("DROP TABLE IF EXISTS " + Table_nazwa);// przy dodaniu koluny i zmianie wersii usuwa tabele i tworzy na now
        onCreate(db);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();//mozliwy odczytt z pozomu innej klasy main lub after login
        Cursor cursor = db.query(Table_nazwa, new String[]{C_3}, C_2 + "=?",// "=?" wymuszona wartosc
                new String[]{username}, null, null, null);//zmienna kuror przechowuje wynik zaytania sql
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {//istnieje i przechodzi do 1 record i liczba rekodow jest wieksza 0
            if (password.equals(cursor.getString(0))) {//porownaj haslo z cusror get string columna 0 odpowiedz sql
                return true;
            }
        }
        return false;
    }

    public boolean checkDefaultPass(Integer id){    //jezeli wymaga zmiany to zwraca true
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Table_nazwa, new String[]{C_6},C_1 + "=?",//sprawdza czy haslo jest domyslne, czyli czy wymaga zmiany
                new String[]{id.toString()},null, null, null);
        if (res != null && res.moveToFirst()&& res.getCount()>0) {
            return spr(res.getInt(0));
        }
        return false;
    }

    public boolean checkIsAdmin (String username) {//sprawdza czy uzytkownik jest adminem, na podstawie nazwy uzytkownika
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query(Table_nazwa, new String[]{C_4},C_2 + "=?",
                new String[]{username},null, null, null);
        if (res != null && res.moveToFirst()&& res.getCount()>0) {//jezeli nie zerowy i wyb pierwszt wynik
            return spr(res.getInt(0));
        }
        return false;
    }

    public boolean spr(int a){// konwertuje wartość 1 na true, a 0 na false
        if(a==0)return false; else if(a==1) return true; else return false;
    }

    public boolean addUser(String username, int range, String nazwa) { // dodaje nowego uzytkownika
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();   //tworzy zbiór warości
        contentValues.put(C_2, username);                 //dodawanie danych do zbioru (nazwa kolumny, wartość)
        contentValues.put(C_3, "secure");                 //dla wszystkich domyslne
        contentValues.put(C_4, range);
        contentValues.put(C_5, nazwa);
        contentValues.put(C_6, 1);
        long result = db.insert(Table_nazwa, null, contentValues); //dodanie zbioru do bazy, db.insert w razie błędu zwraca -1
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUserIsExist(String username){  //sprawdz czy istnieje, jesli istanieje zwroc true, jesli nie to false
        SQLiteDatabase db = this.getWritableDatabase();//otwarcie do odczyt zapis
        Cursor res = db.query(Table_nazwa, new String[]{C_1},C_2 + "=?",//znajduje wynik wyszukania
                new String[]{username},null, null, null);
        if (res.getCount() > 0) return true;
        else return false;
    }

    public boolean upadateUserPass(Integer id, String password) { //zmiana samego hasla dla uzytkownika o danym id
        SQLiteDatabase db = this.getWritableDatabase();//otwarcie do odczyt zapis zeby moc update
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_3, password);
        contentValues.put(C_6, 0);
        db.update(Table_nazwa, contentValues, C_1 + "= ?", new String[]{id.toString()});
        return true;
    }


    public int selectIdByUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+C_1+" from "+Table_nazwa+" WHERE "+ C_2 +" = '"+ username +"';", null);
        res.moveToFirst();
        return res.getInt(0);
    }
    public Cursor selectUserById(Integer id) {          //pobiera dane uzytkownika na podstawie id
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(Table_nazwa, new String[]{C_1, C_2, C_4, C_5, C_3}, C_1 + "=?",
                new String[]{id.toString()}, null, null, null);
        res.moveToFirst();
        return res;
    }

    public boolean upadateUser(Integer ID, String username, Integer range, String nazwa) { //zmiana samego user
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_2, username);
        contentValues.put(C_4, range);
        contentValues.put(C_5, nazwa);// nazwisko albo nick

        db.update(Table_nazwa, contentValues, C_1 + "= ?", new String[]{ID.toString()});
        return true;
    }
    public boolean deleteUser(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.delete(Table_nazwa, C_1 + " = ?", new String[] { ID.toString() })>0){//zwraca ilosc usunietych rekordow jesli jest wieksaza od 0 to true
            return true;
        }else return false;//jesli 0 albo blad
    }

    public Cursor pobierzUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Table_nazwa, null);
        return res;
    }

public String sha256(String password){


    MessageDigest md = null;
    StringBuilder sb = new StringBuilder();// zmienna do zmiany tablicy znakow na string
    try {
        md = MessageDigest.getInstance("SHA-256");// jezeli nie znajdzie sha szyfrowania
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        for (byte b : hashInBytes) { //byte by byte
            sb.append(String.format("%02x", b));//kodowane na hexadecimal
        }
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    return Base64.getEncoder().encodeToString(sb.toString().getBytes());// kodowanie do formatu Base64 String
}
    //https://mkyong.com/java/java-sha-hashing-example/#


}
