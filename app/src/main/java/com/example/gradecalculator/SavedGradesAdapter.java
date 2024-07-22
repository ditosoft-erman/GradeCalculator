package com.example.gradecalculator;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedGradesAdapter extends RecyclerView.Adapter<SavedGradesAdapter.SavedGradesViewHolder> {

    private final List<Grade> savedGrades;
    private SharedPreferences prefs;


    public SavedGradesAdapter(List<Grade> savedGrades, SharedPreferences prefs) {
        this.savedGrades = savedGrades;
        this.prefs = prefs;
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
        holder.deleteButton.setOnClickListener(v -> {
            removeGrade(position);
        });
    }



    @Override
    public int getItemCount() {
        return savedGrades.size();
    }


    public void removeGrade(int position) {
        Grade grade = savedGrades.get(position);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(grade.getSubject() + "_midterm_grade");
        editor.remove(grade.getSubject() + "_final_grade");
        editor.apply();

        savedGrades.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, savedGrades.size());
    }

    static class SavedGradesViewHolder extends RecyclerView.ViewHolder {
        private final TextView subjectTextView;
        private final TextView midtermGradeTextView;
        private final TextView finalTermGradeTextView;

        private  final Button deleteButton;

        public SavedGradesViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.subject_text_view);
            midtermGradeTextView = itemView.findViewById(R.id.midterm_grade_text_view);
            finalTermGradeTextView = itemView.findViewById(R.id.final_term_grade_text_view);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}
