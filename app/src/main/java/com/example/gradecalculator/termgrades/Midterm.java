package com.example.gradecalculator.termgrades;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;
import com.example.gradecalculator.R;

public class Midterm extends TermGrade {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btnCalculateGrades = findViewById(R.id.btnCalculateGrades);
        Button addRowLectureButton = findViewById(R.id.addRowLectureButton);
        Button addRowLaboratoryButton = findViewById(R.id.addRowLaboratoryButton);
        Button btnSaveGrade = findViewById(R.id.btnSaveGrade);



        btnCalculateGrades.setOnClickListener(v -> calculateGrades());

        addRowLectureButton.setOnClickListener(v -> addRowToTable(lectureTableLayout, "", "", ""));
        addRowLaboratoryButton.setOnClickListener(v -> addRowToTable(labTableLayout, "", "", ""));

        btnSaveGrade.setOnClickListener(v -> saveGrade());
    }

    private void saveGrade() {
        String[] existingSubjects = getIntent().getStringArrayExtra("existingSubjects");




        String tvcg = tvCalculatedGrades.getText().toString();
        String result = tvcg.replace("Calculated Grade: ", "");



        SharedPreferences prefs = getSharedPreferences("grades", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(subject.getName() + "_midterm_grade", result);
        editor.apply();


        Toast.makeText(this, "Midterm Grade Saved ", Toast.LENGTH_SHORT).show();
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
