package com.example.gradecalculator;

public class Grade {
    private final String subject;
    private final String midtermGrade;
    private final String finalTermGrade;





    public Grade(String subject, String midtermGrade, String finalTermGrade) {
        this.subject = subject;
        this.midtermGrade = midtermGrade;
        this.finalTermGrade = finalTermGrade;

    }

    public String getSubject() {
        return subject;
    }

    public String getMidtermGrade() {
        return midtermGrade;
    }

    public String getFinalTermGrade() {
        return finalTermGrade;
    }


}
