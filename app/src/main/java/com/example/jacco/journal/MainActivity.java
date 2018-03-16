package com.example.jacco.journal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateData();

        ListView listview = findViewById(R.id.listview);
        listview.setOnItemClickListener(new ListClickListener());
        listview.setOnItemLongClickListener(new ListLongClickListener());
    }

    public void addButtonClicked(View view) {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

    private class ListClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Cursor entry = (Cursor) adapterView.getItemAtPosition(i);
            String title = entry.getString(entry.getColumnIndex(EntryDatabase.COLUMN_TITLE));
            String content = entry.getString(entry.getColumnIndex(EntryDatabase.COLUMN_CONTENT));
            String mood = entry.getString(entry.getColumnIndex(EntryDatabase.COLUMN_MOOD));
            String time = entry.getString(entry.getColumnIndex(EntryDatabase.COLUMN_TIME));

            JournalEntry journalEntry = new JournalEntry(title,content,mood,time);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("clicked_entry", journalEntry);
            startActivity(intent);
        }
    }

    private class ListLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
            int entry = cursor.getInt(cursor.getColumnIndex("_id"));
            db.delete(entry);

            updateData();

            return true;
        }
    }

    // update data that's shown
    public void updateData() {
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();
        EntryAdapter adapter = new EntryAdapter(MainActivity.this,cursor);
        ListView listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }
}
