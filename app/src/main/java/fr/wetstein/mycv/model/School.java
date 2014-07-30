package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class School extends Place implements Parcelable {
    public static final String TAG = "School";

    public int id;
    public String link;
    public int logo;
    public List<Study> listStudy;

    public School() {
        super();
        listStudy = new ArrayList<Study>();
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    private School(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeInt(id);
        dest.writeInt(logo);
        dest.writeString(link);
        dest.writeList(listStudy);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);

        id = in.readInt();
        logo = in.readInt();
        link = in.readString();
        listStudy = new ArrayList<Study>();
        in.readList(listStudy, null);
    }
}
