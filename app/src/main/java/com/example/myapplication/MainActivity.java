package com.example.myapplication;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText eventTitleEditText, eventDateEditText;
    private Button addEventButton;
    private ListView eventListView;
    private DatabaseHelper db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "Error: User not found. Please log in again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        eventTitleEditText = findViewById(R.id.eventTitleEditText);
        eventDateEditText = findViewById(R.id.eventDateEditText);
        addEventButton = findViewById(R.id.addEventButton);
        eventListView = findViewById(R.id.eventListView);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = eventTitleEditText.getText().toString().trim();
                String date = eventDateEditText.getText().toString().trim();

                if (title.isEmpty() || date.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.insertEvent(userId, title, date)) {
                    Toast.makeText(MainActivity.this, "Event added", Toast.LENGTH_SHORT).show();
                    loadEvents();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add event", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadEvents();
    }

    private void loadEvents() {
        Cursor cursor = db.getUserEvents(userId);

        String[] from = new String[]{db.getColumnEventTitle(), db.getColumnEventDate()};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, from, to, 0);
        eventListView.setAdapter(adapter);
    }
}

