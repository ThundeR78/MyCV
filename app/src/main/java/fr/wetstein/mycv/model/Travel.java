package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThundeR on 25/07/2014.
 */
public class Travel implements Parcelable {
    public static final String TAG = "Travel";

    public String country;
    public String context;
    public Date dateBegin;
    public Date dateEnd;
    public List<Place> listPlace;

    public Travel() {
        listPlace = new ArrayList<Place>();
    }

    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    private Travel(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(context);
        dest.writeLong((dateBegin != null) ? dateBegin.getTime() : 0);
        dest.writeLong((dateEnd != null) ? dateEnd.getTime() : 0);
        dest.writeTypedList(listPlace);
    }

    public void readFromParcel(Parcel in) {
        country = in.readString();
        context = in.readString();
        dateBegin = new Date(in.readLong());
        dateEnd = new Date(in.readLong());
        listPlace = new ArrayList<Place>();
        in.readTypedList(listPlace, Place.CREATOR);
    }
}
