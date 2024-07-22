package com.example.gradecalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedGrades extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SavedGradesAdapter adapter;
    private List<Grade> savedGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_grades);



        recyclerView = findViewById(R.id.recyclerViewSavedGrades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the saved grades list
        savedGrades = loadSavedGrades();

        adapter = new SavedGradesAdapter(savedGrades);
        recyclerView.setAdapter(adapter);

        // Set the back button functionality
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> finish());
    }

    private List<Grade> loadSavedGrades() {
        List<Grade> grades = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("grades", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("_midterm_grade") || key.endsWith("_final_grade"))  {
                String subjectName = key.split("_")[0];
                String midtermGrade = prefs.getString(subjectName + "_midterm_grade", "0.00%");
                String finalTermGrade = prefs.getString(subjectName + "_final_grade", "0.00%");

                Grade grade = new Grade(subjectName, midtermGrade, finalTermGrade);
                grades.add(grade);
            }
        }
        return grades;
    }
}
