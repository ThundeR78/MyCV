package fr.wetstein.mycv.database;

import android.provider.BaseColumns;

public final class DatabaseContract {
	
    // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class NewsBase implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_SERVERID = "serverId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_CONTENTTYPE = "contentType";
        public static final String COLUMN_NAME_CREATEDDATE = "createdDate";
        public static final String COLUMN_NAME_STARTDATE = "startDate";
        public static final String COLUMN_NAME_ENDDATE = "endDate";
        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_TAGS = "tags";

        public static final String[] COLUMNS_ARRAY = new String[] { _ID, COLUMN_NAME_SERVERID,
                COLUMN_NAME_TITLE, COLUMN_NAME_CONTENT, COLUMN_NAME_CONTENTTYPE,
                COLUMN_NAME_CREATEDDATE, COLUMN_NAME_STARTDATE, COLUMN_NAME_ENDDATE,
                COLUMN_NAME_PRIORITY, COLUMN_NAME_TAGS };
    }

}