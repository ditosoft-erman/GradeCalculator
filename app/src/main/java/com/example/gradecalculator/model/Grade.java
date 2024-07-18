// Grade.java
package com.example.gradecalculator.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grades")
public class Grade {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subject;
    private double midtermGrade;
    private double finalTermGrade;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(double midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public double getFinalTermGrade() {
        return finalTermGrade;
    }

    public void setFinalTermGrade(double finalTermGrade) {
        this.finalTermGrade = finalTermGrade;
    }
}
