package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, gradeEditText;
    private Button addButton;
    private ListView studentListView;

    private DBHelper dbHelper;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        gradeEditText = findViewById(R.id.gradeEditText);
        addButton = findViewById(R.id.addButton);
        studentListView = findViewById(R.id.studentListView);

        dbHelper = new DBHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String grade = gradeEditText.getText().toString();

                if (!name.isEmpty() && !grade.isEmpty()) {
                    StudentModal student = new StudentModal();
                    student.setName(name);
                    student.setGrade(grade);

                    dbHelper.addStudent(student);

                    refreshStudentList();
                    clearInputs();
                } else {
                    Toast.makeText(MainActivity.this, "Name and grade cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refreshStudentList();
    }

    private void refreshStudentList() {
        List<StudentModal> students = dbHelper.getAllStudents();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertStudentListToStringArray(students));
        studentListView.setAdapter(adapter);
    }

    private void clearInputs() {
        nameEditText.setText("");
        gradeEditText.setText("");
    }

    private String[] convertStudentListToStringArray(List<StudentModal> students) {
        String[] studentArray = new String[students.size()];
        for (int i = 0; i < students.size(); i++) {
            StudentModal student = students.get(i);
            studentArray[i] = student.getName() + " - " + student.getGrade();
        }
        return studentArray;
    }
    }
