package com.example.gradecalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Subject> subjects;
    private SubjectAdapter subjectAdapter;
    private SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS_KEY = "subjects";

    private ActivityResultLauncher<Intent> addSubjectLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewSubjects = findViewById(R.id.recyclerViewSubjects);
        Button buttonAddSubject = findViewById(R.id.button_add_subject);

        sharedPreferences = getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

        subjects = loadSubjects();

        subjectAdapter = new SubjectAdapter(subjects, this);
        recyclerViewSubjects.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSubjects.setAdapter(subjectAdapter);

        addSubjectLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String subjectName = result.getData().getStringExtra("subjectName");
                        if (subjectName != null && !subjectName.isEmpty()) {
                            subjects.add(new Subject(subjectName));
                            subjectAdapter.notifyItemInserted(subjects.size() - 1);
                            saveSubjects();
                        }
                    }
                }
        );

        buttonAddSubject.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddSubjectActivity.class);

            intent.putExtra("existingSubjects", subjectsToStringArray());
            addSubjectLauncher.launch(intent);
        });
    }

    private String[] subjectsToStringArray() {
        String[] subjectNames = new String[subjects.size()];
        for (int i = 0; i < subjects.size(); i++) {
            subjectNames[i] = subjects.get(i).getName();
        }
        return subjectNames;
    }

    protected void saveSubjects() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFS_KEY, subjectsToString());
        editor.apply();
    }

    private List<Subject> loadSubjects() {
        String subjectsString = sharedPreferences.getString(SHARED_PREFS_KEY, "");
        List<Subject> subjectList = new ArrayList<>();
        if (!subjectsString.isEmpty()) {
            String[] subjectNames = subjectsString.split(",");
            for (String name : subjectNames) {
                subjectList.add(new Subject(name));
            }
        }
        return subjectList;
    }

    private String subjectsToString() {
        StringBuilder subjectsString = new StringBuilder();
        for (Subject subject : subjects) {
            if (subjectsString.length() > 0) {
                subjectsString.append(",");
            }
            subjectsString.append(subject.getName());
        }
        return subjectsString.toString();
    }
}
