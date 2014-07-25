package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class Experience extends Place implements Parcelable {
    public static final String TAG = "Experience";

    public String link;
    public String function;
    public int logo;
    public String type;
    public List<String> listTask;

    public Experience() {
        super();
        listTask = new ArrayList<String>();
    }

    public static final Parcelable.Creator<Experience> CREATOR = new Parcelable.Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    private Experience(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(logo);
        dest.writeString(function);
        dest.writeString(link);
        dest.writeString(type);
        dest.writeStringList(listTask);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        logo = in.readInt();
        function = in.readString();
        link = in.readString();
        type = in.readString();
        listTask = new ArrayList<String>();
        in.readStringList(listTask);
    }
}
