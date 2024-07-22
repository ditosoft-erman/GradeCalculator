package com.example.gradecalculator;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedGradesAdapter extends RecyclerView.Adapter<SavedGradesAdapter.SavedGradesViewHolder> {

    private final List<Grade> savedGrades;

    public SavedGradesAdapter(List<Grade> savedGrades) {
        this.savedGrades = savedGrades;
    }



    @NonNull
    @Override
    public SavedGradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_grade_item, parent, false);
        return new SavedGradesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedGradesViewHolder holder, int position) {
        Grade grade = savedGrades.get(position);
        holder.subjectTextView.setText(grade.getSubject());
        holder.midtermGradeTextView.setText("Midterm: " + grade.getMidtermGrade());
        holder.finalTermGradeTextView.setText("Final Term: " + grade.getFinalTermGrade());
    }

    @Override
    public int getItemCount() {
        return savedGrades.size();
    }

    static class SavedGradesViewHolder extends RecyclerView.ViewHolder {
        private final TextView subjectTextView;
        private final TextView midtermGradeTextView;
        private final TextView finalTermGradeTextView;

        public SavedGradesViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subject_text_view);
            midtermGradeTextView = itemView.findViewById(R.id.midterm_grade_text_view);
            finalTermGradeTextView = itemView.findViewById(R.id.final_term_grade_text_view);
        }
    }
}
