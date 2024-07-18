package com.example.gradecalculator.termgrades;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class TermData implements Parcelable {
    private final List<String[]> lectureData;
    private final List<String[]> labData;

    public TermData() {
        lectureData = new ArrayList<>();
        labData = new ArrayList<>();
    }

    protected TermData(Parcel in) {
        lectureData = new ArrayList<>();
        labData = new ArrayList<>();
        in.readList(lectureData, String[].class.getClassLoader());
        in.readList(labData, String[].class.getClassLoader());
    }

    public static final Creator<TermData> CREATOR = new Creator<TermData>() {
        @Override
        public TermData createFromParcel(Parcel in) {
            return new TermData(in);
        }

        @Override
        public TermData[] newArray(int size) {
            return new TermData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(lectureData);
        dest.writeList(labData);
    }
}
