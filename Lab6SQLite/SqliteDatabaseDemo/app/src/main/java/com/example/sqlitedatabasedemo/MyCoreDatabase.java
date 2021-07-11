package com.example.sqlitedatabasedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyCoreDatabase extends SQLiteOpenHelper {
    static final private int DB_VERSION = 1;
    static final private String DB_NAME = "Education";
    static final private String DB_TABLE = "students";

    Context context;
    SQLiteDatabase myDB;

    public MyCoreDatabase(Context anyContext) {
        super(anyContext, DB_NAME, null, DB_VERSION);

        context = anyContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE + " (_id integer primary key autoincrement, studentName text, collegeName text);");

        Log.i("Database", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        onCreate(db);
    }

    public void insertData(String s1, String s2) {
        myDB = getWritableDatabase();

        myDB.execSQL("INSERT INTO " + DB_TABLE + " (studentName, collegeName) VALUES ('" + s1 + "','" + s2 +"');");

        Toast.makeText(context, "Data was saved successfully", Toast.LENGTH_SHORT).show();
    }

    public void getAllData() {
        myDB = getReadableDatabase();

        Cursor cursor = myDB.rawQuery("SELECT * FROM " + DB_TABLE, null);

        StringBuilder stringBuilder = new StringBuilder();

        while(cursor.moveToNext()) {
            String studentName = cursor.getString(1);
            String collegeName = cursor.getString(2);

            stringBuilder.append(studentName + " " + collegeName + "\n");
        }

        Toast.makeText(context, stringBuilder.toString(), Toast.LENGTH_LONG).show();
    }
}
