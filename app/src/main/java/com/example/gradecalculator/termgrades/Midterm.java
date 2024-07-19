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
        // Initialize sums for lecture and lab scores
        double lectureScoreSum = 0;
        double labScoreSum = 0;
        int lectureCount = lectureTableLayout.getChildCount() - 1;
        int labCount = labTableLayout.getChildCount() - 1;

        // Calculate total lecture scores
        for (int i = 1; i < lectureCount + 1; i++) {
            TableRow row = (TableRow) lectureTableLayout.getChildAt(i);
            try {
                double score = Double.parseDouble(((EditText) row.getChildAt(0)).getText().toString());
                double weight = Double.parseDouble(((EditText) row.getChildAt(2)).getText().toString());
                lectureScoreSum += score * weight / 100;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        for (int i = 1; i < labCount + 1; i++) {
            TableRow row = (TableRow) labTableLayout.getChildAt(i);
            try {
                double score = Double.parseDouble(((EditText) row.getChildAt(0)).getText().toString());
                double weight = Double.parseDouble(((EditText) row.getChildAt(2)).getText().toString());
                labScoreSum += score * weight / 100;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }


        double totalWeight = (lectureCount > 0 ? 2.0 / 3.0 : 0) + (labCount > 0 ? 1.0 / 3.0 : 0);
        double weightedGrade = (lectureScoreSum * 2.0 / 3.0 + labScoreSum * 1.0 / 3.0) / totalWeight;


        String calculatedGrade = String.format("%.1f", weightedGrade);


        SharedPreferences prefs = getSharedPreferences("grades", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(subject.getName() + "_midterm_grade", calculatedGrade);
        editor.apply();


        Toast.makeText(this, "Midterm Grade Saved", Toast.LENGTH_SHORT).show();
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
