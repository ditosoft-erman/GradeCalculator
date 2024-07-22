package com.example.gradecalculator.termgrades;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gradecalculator.R;
import com.example.gradecalculator.Subject;
import com.example.gradecalculator.util.GradeCalculator;
import com.example.gradecalculator.util.InputFilterMinMax;

public class FinalTerm extends TermGrade {

    @Override
    protected String getSharedPrefsName() {
        return "final_term_data";
    }

    @Override
    protected String getGradeKey() {
        return "final_term_grade";
    }

    @Override
    protected String getLectureRowsKey() {
        return "final_term_lecture_rows";
    }

    @Override
    protected String getLabRowsKey() {
        return "final_term_lab_rows";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.finalterm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btnSaveGrade = findViewById(R.id.btnSaveGrade);

        btnSaveGrade.setOnClickListener(v -> {
            saveGrade();
        });
    }

    protected void saveGrade(){
        String tvcg = tvCalculatedGrades.getText().toString();
        String result = tvcg.replace("Calculated Grade: ", "");



        SharedPreferences prefs = getSharedPreferences("grades", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(subject.getName() + "_final_grade", result);
        editor.apply();


        Toast.makeText(this, "Final Grade Saved ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void calculateGrades() {
        GradeCalculator gradeCalculator = new GradeCalculator(lectureTableLayout, labTableLayout);
        double lectureGrade = gradeCalculator.calculateTableGrade(lectureTableLayout);
        double labGrade = gradeCalculator.calculateTableGrade(labTableLayout);
        double overallGrade = (2.0 / 3.0) * lectureGrade + (1.0 / 3.0) * labGrade;
        tvCalculatedGrades.setText(getString(R.string.calculated_grade_format, overallGrade));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(subject.getName() + "_overall_grade", (float) overallGrade);
        editor.apply();

    }
}
