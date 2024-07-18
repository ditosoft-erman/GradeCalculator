package com.example.gradecalculator.termgrades;

import android.os.Bundle;
import android.widget.Button;
import com.example.gradecalculator.R;

public class Midterm extends TermGrade {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btnCalculateGrades = findViewById(R.id.btnCalculateGrades);
        Button addRowLectureButton = findViewById(R.id.addRowLectureButton);
        Button addRowLaboratoryButton = findViewById(R.id.addRowLaboratoryButton);

        btnCalculateGrades.setOnClickListener(v -> calculateGrades());

        addRowLectureButton.setOnClickListener(v -> addRowToTable(lectureTableLayout, "", "", ""));
        addRowLaboratoryButton.setOnClickListener(v -> addRowToTable(labTableLayout, "", "", ""));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected String getSharedPrefsName() {
        return subject.getName() + "_midterm_prefs";
    }

    @Override
    protected String getGradeKey() {
        return "midterm_grade";
    }

    @Override
    protected String getLectureRowsKey() {
        return "midterm_lecture_rows";
    }

    @Override
    protected String getLabRowsKey() {
        return "midterm_lab_rows";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.midterm;
    }
}