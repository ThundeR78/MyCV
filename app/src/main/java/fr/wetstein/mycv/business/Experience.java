package fr.wetstein.mycv.business;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class Experience implements Parcelable {

    public String name;
    public String link;
    public String address;
    public Double latitude;
    public Double longitude;
    public String function;
    public int logo;
    public Date dateBegin;
    public Date dateEnd;
    public String type;
    public List<String> listTask;

    public Experience() {
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
        dest.writeString(name);
        dest.writeInt(logo);
        dest.writeString(function);
        dest.writeString(link);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong((dateBegin != null) ? dateBegin.getTime() : 0);
        dest.writeLong((dateEnd != null) ? dateEnd.getTime() : 0);
        dest.writeString(type);
        dest.writeStringList(listTask);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        logo = in.readInt();
        function = in.readString();
        link = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        dateBegin = new Date(in.readLong());
        dateEnd = new Date(in.readLong());
        type = in.readString();
        listTask = new ArrayList<String>();
        in.readStringList(listTask);
    }
}
