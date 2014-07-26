package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ThundeR on 25/07/2014.
 */
public class Place implements Parcelable {
    public static final String TAG = "Place";

    public String name;
    public String address;
    public Double latitude;
    public Double longitude;
    public Date dateBegin;
    public Date dateEnd;
    public float pin;

    public Place() {

    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    private Place(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong((dateBegin != null) ? dateBegin.getTime() : 0);
        dest.writeLong((dateEnd != null) ? dateEnd.getTime() : 0);
        dest.writeFloat(pin);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        dateBegin = new Date(in.readLong());
        dateEnd = new Date(in.readLong());
        pin = in.readFloat();
    }
}
