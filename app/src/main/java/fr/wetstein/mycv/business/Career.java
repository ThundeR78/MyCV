package fr.wetstein.mycv.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ThundeR on 06/07/2014.
 */
public class Career {

    public String name;
    public String link;
    public String address;
    public Double latitude;
    public Double longitude;
    public String function;
    public String logo;
    public Date dateBegin;
    public Date dateEnd;
    public String type;
    public List<String> listTask;

    public Career() {
        listTask = new ArrayList<String>();
    }
}
