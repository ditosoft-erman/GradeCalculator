package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSubjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);

        EditText editTextSubjectName = findViewById(R.id.subjectNameEditText);
        Button buttonCreateSubject = findViewById(R.id.createSubjectButton);
        Button buttonBack = findViewById(R.id.button_back);

        // Retrieve existing subject names from the intent
        String[] existingSubjects = getIntent().getStringArrayExtra("existingSubjects");

        buttonCreateSubject.setOnClickListener(v -> {
            String subjectName = editTextSubjectName.getText().toString().trim();
            if (!subjectName.isEmpty()) {
                if (existingSubjects != null && isDuplicate(subjectName, existingSubjects)) {
                    // Notify the user about the duplicate name
                    Toast.makeText(AddSubjectActivity.this, "Subject name already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to create the subject
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("subjectName", subjectName);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                    // Show a notification for successful creation
                    Toast.makeText(AddSubjectActivity.this, "Subject created successfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Notify the user about the empty name
                Toast.makeText(AddSubjectActivity.this, "Please enter a subject name", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBack.setOnClickListener(v -> finish());
    }

    private boolean isDuplicate(String name, String[] existingSubjects) {
        for (String subject : existingSubjects) {
            if (subject.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}