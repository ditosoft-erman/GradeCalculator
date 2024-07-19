package com.example.gradecalculator;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gradecalculator.termgrades.TermData;

public class Subject implements Parcelable {
    private final String name;
    private final TermData midtermData;
    private final TermData finalTermData;

    private float midtermGrade;
    private float finalTermGrade;

    public Subject(String name) {
        this.name = name;
        this.midtermData = new TermData();
        this.finalTermData = new TermData();
    }

    protected Subject(Parcel in) {
        name = in.readString();
        midtermData = in.readParcelable(TermData.class.getClassLoader());
        finalTermData = in.readParcelable(TermData.class.getClassLoader());
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(midtermData, flags);
        dest.writeParcelable(finalTermData, flags);
    }
}
