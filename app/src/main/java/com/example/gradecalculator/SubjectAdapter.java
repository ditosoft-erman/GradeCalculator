package com.example.gradecalculator;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradecalculator.termgrades.FinalTerm;
import com.example.gradecalculator.termgrades.Midterm;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private final List<Subject> subjects;
    private final MainActivity mainActivity;

    public SubjectAdapter(List<Subject> subjects, MainActivity mainActivity) {
        this.subjects = subjects;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjects.get(position);
        holder.subjectTextView.setText(subject.getName());

        holder.subjectTextView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(position);
            return true;
        });

        holder.midtermButton.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, Midterm.class);
            intent.putExtra("subject", subject);
            intent.putExtra("subjectName", subject.getName());
            mainActivity.startActivity(intent);
        });

        holder.finalTermButton.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, FinalTerm.class);
            intent.putExtra("subject", subject);
            intent.putExtra("subjectName", subject.getName());
            mainActivity.startActivity(intent);
        });
    }


    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Delete Subject")
                .setMessage("Are you sure you want to delete this subject?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    removeSubject(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void removeSubject(int position) {
        subjects.remove(position);
        notifyItemRemoved(position);
        mainActivity.saveSubjects();
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectTextView;
        public Button midtermButton;
        public Button finalTermButton;

        public SubjectViewHolder(View itemView) {
            super(itemView);
            subjectTextView = itemView.findViewById(R.id.textViewSubjectName);
            midtermButton = itemView.findViewById(R.id.button_midterm);
            finalTermButton = itemView.findViewById(R.id.button_final);

            if (subjectTextView == null || midtermButton == null || finalTermButton == null) {
                throw new IllegalStateException("One or more views are null");
            }
        }
    }
}