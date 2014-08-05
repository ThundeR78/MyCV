package fr.wetstein.mycv.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by ThundeR on 05/08/2014.
 */
public class News {

    public String id;
    public String title;
    public String content;
    public String contentType;
    public int priority;
    public Date startDate;
    public Date endDate;
    @SerializedName("tags")
    public List<String> listTag;

}
