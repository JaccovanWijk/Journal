package com.example.jacco.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputActivity extends AppCompatActivity {

    int[] images = {R.id.sad,R.id.okay,R.id.happy,R.id.great};
    String[] moods = {"Sad","Okay","Happy","Great"};
    String mood;
    JournalEntry retrievedEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // load in data from when it's called
        Intent intent = getIntent();
        retrievedEntry = (JournalEntry) intent.getSerializableExtra("edit_entry");

        if (retrievedEntry != null) {
            editEntry(retrievedEntry);
        } else {
            mood = "";
        }
    }

    public void addEntry (View view) {
        EditText title = findViewById(R.id.title);
        EditText content = findViewById(R.id.content);

        String aTitle = title.getText().toString();
        String theContent = content.getText().toString();
        String timestamp = new SimpleDateFormat("dd-MM-yyyy hh:mm").format(new Date());

        System.out.println(timestamp);
        System.out.println(mood);
        // create new entry and insert it in database
        JournalEntry entry = new JournalEntry(aTitle, theContent, mood, timestamp);
        EntryDatabase.getInstance(this).insert(entry);

        Intent intent = new Intent(InputActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void moodClicked(View view) {
        ImageButton button = (ImageButton) view;

        int id = button.getId();
        int amountImages = 4;
        for (int i = 0; i < amountImages; i++) {
            if(id == images[i]) {
                mood = moods[i];

                //set border
                button.setBackgroundResource(R.drawable.resizedborder);
            } else if(id != images[i]){
                //remove border
                ImageButton wrongButton = findViewById(images[i]);
                wrongButton.setBackgroundResource(0);
            }
        }
    }

    // edits entry when clicked on edit button detailedactivity
    public void editEntry(JournalEntry entry) {
        EditText title = findViewById(R.id.title);
        EditText content = findViewById(R.id.content);

        System.out.println(title);
        //set title and content
        title.setText(entry.getTitle());
        content.setText(entry.getContent());

        //set mood
        mood = entry.getMood();
        int amountImages = 4;
        for (int i = 0; i < amountImages; i++) {
            ImageButton button = findViewById(images[i]);
            if (mood.equals(moods[i])) {
                button.setBackgroundResource(R.drawable.resizedborder);
            } else {
                button.setBackgroundResource(0);
            }
        }

        // delete so you won't get multiple copies
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        db.delete(entry.getId());
    }
}
