package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class Company extends Place implements Parcelable {
    public static final String TAG = "Company";

    public String website;
    public String phone;
    public String email;
    public int logo;
    public String desc;
    public String ceo;
    public int nbEmployees;
    public String twitter;
    //public List<Product> listProduct;

    public Company() {
        super();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    private Company(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(logo);
        dest.writeString(desc);
        dest.writeString(ceo);
        dest.writeInt(nbEmployees);
        dest.writeString(twitter);
    }

    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        website = in.readString();
        phone = in.readString();
        email = in.readString();
        logo = in.readInt();
        desc = in.readString();
        ceo = in.readString();
        nbEmployees = in.readInt();
        twitter = in.readString();
    }
}
