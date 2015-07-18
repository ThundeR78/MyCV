package fr.wetstein.mycv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThundeR on 05/08/2014.
 */
public class News implements Parcelable {

    @SerializedName("_id")
    public int id;
    @SerializedName("id")
    public String serverId;
    public String title;
    public String content;
    public String contentType;  //TEXT,HTML,MARKDOWN
    @SerializedName("created")
    public Date createdDate;
    public Date startDate;
    public Date endDate;
    public int priority;
    @SerializedName("tags")
    public List<String> listTag;

    public News() {
        listTag = new ArrayList<String>();
    }

    public enum Type {
        TEXT, MARKDOWN, HTML
    }

    public Type getType() {
        return contentType.equals("HTML") ? Type.HTML :
                contentType.equals("MARKDOWN") ? Type.MARKDOWN :
                        Type.TEXT;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    private News(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(serverId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(contentType);
        dest.writeInt(priority);
        dest.writeStringList(listTag);
        dest.writeLong(createdDate != null ? createdDate.getTime() : 0);
        dest.writeLong(startDate != null ? startDate.getTime() : 0);
        dest.writeLong(endDate != null ? endDate.getTime() : 0);
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        serverId = in.readString();
        title = in.readString();
        content = in.readString();
        contentType = in.readString();
        priority = in.readInt();
        listTag = new ArrayList<String>();
        in.readStringList(listTag);
        createdDate = new Date(in.readLong());
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
    }
}
