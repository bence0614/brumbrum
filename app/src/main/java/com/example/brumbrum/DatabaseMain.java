package com.example.brumbrum;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brumbrum.database.CustomAdapter;
import com.example.brumbrum.database.DatabaseHelper;

import java.util.ArrayList;

public class DatabaseMain extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelper myDB;
    ArrayList<String> score_id, score_value;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewscores);
        recyclerView = findViewById(R.id.recyclerView);

        myDB = new DatabaseHelper(DatabaseMain.this);
        score_id = new ArrayList<>();
        score_value = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(DatabaseMain.this, score_id, score_value);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DatabaseMain.this));
    }
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "NO data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                score_id.add(cursor.getString(0));
                score_value.add(cursor.getString(1));
            }
        }
    }
}
