package com.example.studentmanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GRADE = "grade";




    public DBHelper(MainActivity context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//     Query

        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT," +
                KEY_GRADE + " TEXT" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

//        By using db.execSQL will execute Query

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // In upgrade method also By using db.execSQL will execute Query

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);

    }

    public  void addStudent(StudentModal studentModal){
        SQLiteDatabase db = this.getWritableDatabase();
//        by contentValue will going to store the data in SqlLite Helper
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, studentModal.getName());
        values.put(KEY_GRADE, studentModal.getGrade());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();



    }

    @SuppressLint("Range")
    public List<StudentModal> getAllStudents() {
        List<StudentModal> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                StudentModal student = new StudentModal();
                student.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                student.setGrade(cursor.getString(cursor.getColumnIndex(KEY_GRADE)));
                students.add(student);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return students;
    }

    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + "=?", new String[]{String.valueOf(studentId)});
        db.close();
    }
}
