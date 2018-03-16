package com.example.jacco.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    JournalEntry retrievedEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // load in data from when it's called
        Intent intent = getIntent();
        retrievedEntry = (JournalEntry) intent.getSerializableExtra("clicked_entry");

        String retrievedTitle = retrievedEntry.getTitle();
        String retrievedContent = retrievedEntry.getContent();
        String retrievedMood = retrievedEntry.getMood();
        String retrievedTime = retrievedEntry.getTimestamp();

        TextView title = findViewById(R.id.title);
        TextView content = findViewById(R.id.content);
        TextView mood = findViewById(R.id.mood);
        TextView time = findViewById(R.id.date);

        title.setText(retrievedTitle);
        content.setText(retrievedContent);
        mood.setText(retrievedMood);
        time.setText(retrievedTime);
    }

    public void editButtonClicked(View view) {
        // editEntry callen
        Intent intent = new Intent(DetailActivity.this, InputActivity.class);
        intent.putExtra("edit_entry", retrievedEntry);
        startActivity(intent);
    }
}
