package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class Study implements Parcelable {

    public String name;
    public String option;
    public String date;
    public List<String> listCertif;
    public School school;

    public Study() {
        super();
        listCertif = new ArrayList<String>();
    }

    public static final Creator<Study> CREATOR = new Creator<Study>() {
        @Override
        public Study createFromParcel(Parcel in) {
            return new Study(in);
        }

        @Override
        public Study[] newArray(int size) {
            return new Study[size];
        }
    };

    private Study(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(option);
        dest.writeString(date);
        dest.writeStringList(listCertif);
        dest.writeParcelable(school, flags);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        option = in.readString();
        date = in.readString();
        listCertif = new ArrayList<String>();
        in.readStringList(listCertif);
        school = in.readParcelable(School.class.getClassLoader());
    }
}
