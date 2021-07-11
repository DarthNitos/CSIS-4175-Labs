package com.example.sqlitedatabasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText studentName, collegeName;
    private MyCoreDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentName = findViewById(R.id.editTxtStudentName);
        collegeName = findViewById(R.id.editTxtCollegeName);

        myDB = new MyCoreDatabase(this);
    }

    public void save(View view) {
        myDB.insertData(studentName.getText().toString(), collegeName.getText().toString());
    }

    public void load(View view) {
        myDB.getAllData();
    }
}