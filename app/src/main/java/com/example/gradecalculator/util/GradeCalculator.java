package com.example.gradecalculator.util;

import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GradeCalculator {
    private final TableLayout lectureTableLayout;
    private final TableLayout labTableLayout;

    public GradeCalculator(TableLayout lectureTableLayout, TableLayout labTableLayout) {
        this.lectureTableLayout = lectureTableLayout;
        this.labTableLayout = labTableLayout;
    }

    public double calculateTableGrade(TableLayout tableLayout) {
        double totalWeightedScore = 0;
        double totalWeight = 0;

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);

            EditText etScore = (EditText) row.getChildAt(0);
            EditText etOverallScore = (EditText) row.getChildAt(1);
            EditText etWeight = (EditText) row.getChildAt(2);

            String scoreStr = etScore.getText().toString().trim();
            String overallScoreStr = etOverallScore.getText().toString().trim();
            String weightStr = etWeight.getText().toString().trim();

            if (!scoreStr.isEmpty() && !overallScoreStr.isEmpty() && !weightStr.isEmpty()) {
                try {
                    double score = Double.parseDouble(scoreStr);
                    double overallScore = Double.parseDouble(overallScoreStr);
                    double weight = Double.parseDouble(weightStr);

                    if (score > overallScore) {
                        totalWeightedScore += 0; // If score exceeds overall, the weighted score is 0
                    } else {
                        totalWeightedScore += (score / overallScore) * weight;
                    }
                    totalWeight += weight;

                } catch (NumberFormatException e) {
                    // Handle invalid number format
                }
            }
        }

        if (totalWeight == 0) {
            return 0;
        }

        return (totalWeightedScore / totalWeight) * 100;
    }
}
