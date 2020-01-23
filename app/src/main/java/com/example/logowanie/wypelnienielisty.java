package com.example.logowanie;

    import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

    public class wypelnienielisty extends CursorAdapter {

        public wypelnienielisty(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.komorka_listy, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView nazwa = view.findViewById(R.id.itemnazwa);
            TextView username = view.findViewById(R.id.itemUsername);

            //Wyodrębnij dane z kursora
            String nazwaS = cursor.getString(cursor.getColumnIndexOrThrow("nazwa"));//z rezultatu zapytania wynik nazwa(naglowek tabeli)
            String usernameS = cursor.getString(cursor.getColumnIndexOrThrow("username"));//

            nazwa.setText(nazwaS); //ustaw nazwaString do pola textowego
            username.setText(usernameS);

        }
    }

/*
TodoDatabaseHandler handler = new TodoDatabaseHandler(this);
// Get access to the underlying writeable database
SQLiteDatabase db = handler.getWritableDatabase();
// Query for items from the database and get a cursor back
Cursor todoCursor = db.rawQuery("SELECT  * FROM todo_items", null);

Attaching the Adapter to a ListView

Now, we can use the CursorAdapter in the Activity to display an array of items into the ListView:

// Find ListView to populate
ListView lvItems = (ListView) findViewById(R.id.lvItems);
// Setup cursor adapter using cursor from last step
TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor);
// Attach cursor adapter to the ListView
lvItems.setAdapter(todoAdapter);
This will then trigger the CursorAdapter iterating through the result set and populating the list. We can change the cursor to update the adapter at any time with:

// Switch to new cursor and update contents of ListView
todoAdapter.changeCursor(todoCursor);
 */

/*
public class TodoCursorAdapter extends CursorAdapter {
  public TodoCursorAdapter(Context context, Cursor cursor) {
      super(context, cursor, 0);
  }

  // The newView method is used to inflate a new view and return it,
  // you don't bind any data to the view at this point.
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
      return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
  }

  // The bindView method is used to bind all data to a given view
  // such as setting the text on a TextView.
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
      // Find fields to populate in inflated template
      TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
      TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
      // Extract properties from cursor
      String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
      int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
      // Populate fields with extracted properties
      tvBody.setText(body);
      tvPriority.setText(String.valueOf(priority));
  }
}
 */