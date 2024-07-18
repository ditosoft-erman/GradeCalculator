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

import androidx.appcompat.app.AppCompatActivity;

import com.example.gradecalculator.R;
import com.example.gradecalculator.Subject;
import com.example.gradecalculator.util.GradeCalculator;
import com.example.gradecalculator.util.InputFilterMinMax;

public abstract class TermGrade extends AppCompatActivity {
    protected SharedPreferences sharedPreferences;
    protected TableLayout lectureTableLayout;
    protected TableLayout labTableLayout;
    protected TextView tvCalculatedGrades;
    protected Subject subject;

    protected abstract String getSharedPrefsName();

    protected abstract String getGradeKey();

    protected abstract String getLectureRowsKey();

    protected abstract String getLabRowsKey();

    protected abstract int getLayoutResourceId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        // Initialize views
        lectureTableLayout = findViewById(R.id.lectureTableLayout);
        labTableLayout = findViewById(R.id.labTableLayout);
        tvCalculatedGrades = findViewById(R.id.tvCalculatedGrades);
        Button btnCalculateGrades = findViewById(R.id.btnCalculateGrades);
        Button btnAddLectureRow = findViewById(R.id.addRowLectureButton);
        Button btnAddLabRow = findViewById(R.id.addRowLaboratoryButton);
        Button btnBack = findViewById(R.id.button_back);

        // Get subject data
        subject = getIntent().getParcelableExtra("subject");
        if (subject == null) {
            throw new IllegalArgumentException("Subject data is missing");
        }

        // Load data if available
        sharedPreferences = getSharedPreferences(getSharedPrefsName(), MODE_PRIVATE);
        loadData();

        // Set listeners
        btnCalculateGrades.setOnClickListener(v -> calculateGrades());
        btnAddLectureRow.setOnClickListener(v -> addRowToTable(lectureTableLayout, "", "", ""));
        btnAddLabRow.setOnClickListener(v -> addRowToTable(labTableLayout, "", "", ""));
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    protected void addRowToTable(TableLayout tableLayout, String score, String overallScore, String weight) {
        TableRow row = new TableRow(this);

        EditText etScore = new EditText(this);
        etScore.setText(score);
        etScore.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        EditText etOverallScore = new EditText(this);
        etOverallScore.setText(overallScore);
        etOverallScore.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        EditText etWeight = new EditText(this);
        etWeight.setText(weight);
        etWeight.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        etWeight.setFilters(new InputFilter[]{new InputFilterMinMax(0, 100)});

        Button buttonRemove = new Button(this);
        buttonRemove.setText(R.string.remove_button_text);
        buttonRemove.setOnClickListener(v -> {
            tableLayout.removeView(row);
            calculateGrades(); // Trigger calculation after removing row
        });

        row.addView(etScore);
        row.addView(etOverallScore);
        row.addView(etWeight);
        row.addView(buttonRemove);

        tableLayout.addView(row);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateGrades();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etScore.addTextChangedListener(textWatcher);
        etOverallScore.addTextChangedListener(textWatcher);

        calculateGrades(); // Trigger calculation after adding row
    }

    protected void calculateGrades() {
        GradeCalculator gradeCalculator = new GradeCalculator(lectureTableLayout, labTableLayout);
        double lectureGrade = gradeCalculator.calculateTableGrade(lectureTableLayout);
        double labGrade = gradeCalculator.calculateTableGrade(labTableLayout);
        double overallGrade = (2.0 / 3.0) * lectureGrade + (1.0 / 3.0) * labGrade;
        tvCalculatedGrades.setText(getString(R.string.calculated_grade_format, overallGrade));
    }

    protected void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getGradeKey(), tvCalculatedGrades.getText().toString());
        editor.putString(getLectureRowsKey(), getTableData(lectureTableLayout));
        editor.putString(getLabRowsKey(), getTableData(labTableLayout));
        editor.apply();
    }

    protected void loadData() {
        tvCalculatedGrades.setText(sharedPreferences.getString(getGradeKey(), ""));
        loadTableData(sharedPreferences.getString(getLectureRowsKey(), ""), lectureTableLayout);
        loadTableData(sharedPreferences.getString(getLabRowsKey(), ""), labTableLayout);
    }

    private String getTableData(TableLayout tableLayout) {
        StringBuilder tableData = new StringBuilder();
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            StringBuilder rowData = new StringBuilder();
            for (int j = 0; j < row.getChildCount() - 1; j++) { // Exclude the remove button
                EditText editText = (EditText) row.getChildAt(j);
                String input = editText.getText().toString().trim();
                rowData.append(input).append(",");
            }
            if (rowData.length() > 0) {
                rowData.deleteCharAt(rowData.length() - 1);
                tableData.append(rowData).append(";");
            }
        }
        return tableData.toString();
    }

    private void loadTableData(String data, TableLayout tableLayout) {
        String[] rows = data.split(";");
        for (String row : rows) {
            if (!row.isEmpty()) {
                String[] columns = row.split(",");
                if (columns.length == 3) {
                    addRowToTable(tableLayout, columns[0], columns[1], columns[2]);
                } else {
                    addRowToTable(tableLayout, "", "", "");
                }
            }
        }
    }
}
