package fr.wetstein.mycv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.wetstein.mycv.database.DatabaseContract.NewsBase;
import fr.wetstein.mycv.model.News;


public class DatabaseManager extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;	//To increment if database schema change
	public static final String DATABASE_NAME = "MyCV.db";
	
	private static final String SQL_CREATE_NEWS =
        "CREATE TABLE "+ DatabaseContract.NewsBase.TABLE_NAME +" (" +
        		NewsBase._ID +" INTEGER PRIMARY KEY,"+
        		NewsBase.COLUMN_NAME_SERVERID +" TEXT,"+
        		NewsBase.COLUMN_NAME_TITLE +" TEXT,"+
        		NewsBase.COLUMN_NAME_CONTENT +" INTEGER,"+
        		NewsBase.COLUMN_NAME_CONTENTTYPE +" TEXT,"+
                NewsBase.COLUMN_NAME_CREATEDDATE +" DATETIME,"+
                NewsBase.COLUMN_NAME_STARTDATE +" DATETIME,"+
        		NewsBase.COLUMN_NAME_ENDDATE +" DATETIME,"+
        		NewsBase.COLUMN_NAME_PRIORITY +" INTEGER,"+
        		NewsBase.COLUMN_NAME_TAGS +" TEXT"+
        " )";

    private static final String SQL_DELETE_NEWS =
        "DROP TABLE IF EXISTS " + NewsBase.TABLE_NAME;

	
    //Constructor
	public DatabaseManager(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL(SQL_CREATE_NEWS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is to simply to discard the data and start over
        db.execSQL(SQL_DELETE_NEWS);

        onCreate(db);
    }	
    
    /* NEWS */
	//Adding News
    public int insertNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        //Values
        ContentValues values = createContentValuesWithNews(news);
     
        // Inserting Row
        int id = (int) db.insert(NewsBase.TABLE_NAME, null, values);
        db.close();

        return id;
    }
    
    //Getting single News
    public News getNews(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = NewsBase._ID + "=?";
        String[] arrayArguments = new String[] { String.valueOf(id) };
        
        //Get results
        Cursor cursor = db.query(NewsBase.TABLE_NAME, NewsBase.COLUMNS_ARRAY, selection, arrayArguments, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Convert result in object News
        News news = createNewsWithCursor(cursor);
        
        cursor.close();
        db.close();
        
        return news;
    }

    //Getting single News
    public News getNewsByServerId(String serverId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = NewsBase.COLUMN_NAME_SERVERID+ "=?";
        String[] arrayArguments = new String[] { String.valueOf(serverId) };

        //Get results
        Cursor cursor = db.query(NewsBase.TABLE_NAME, NewsBase.COLUMNS_ARRAY, selection, arrayArguments, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //Convert result in object News
        News news = createNewsWithCursor(cursor);

        cursor.close();
        db.close();

        return news;
    }

    private ContentValues createContentValuesWithNews(News news) {
        ContentValues values = new ContentValues();
        if (news != null) {
            values.put(NewsBase.COLUMN_NAME_SERVERID, news.serverId);
            values.put(NewsBase.COLUMN_NAME_TITLE, news.title);
            values.put(NewsBase.COLUMN_NAME_CONTENT, news.content);
            values.put(NewsBase.COLUMN_NAME_CONTENTTYPE, news.contentType);
            values.put(NewsBase.COLUMN_NAME_CREATEDDATE, news.createdDate != null ? news.createdDate.getTime() : null);
            values.put(NewsBase.COLUMN_NAME_STARTDATE, news.startDate != null ? news.startDate.getTime() : null);
            values.put(NewsBase.COLUMN_NAME_ENDDATE, news.endDate != null ? news.endDate.getTime() : null);
            values.put(NewsBase.COLUMN_NAME_PRIORITY, news.priority);
            values.put(NewsBase.COLUMN_NAME_TAGS, TextUtils.join(",", news.listTag));
        }

        return values;
    }

    private News createNewsWithCursor(Cursor cursor) {
        News news = new News();
        news.id = cursor.getInt(0);
        news.serverId = cursor.getString(1);
        news.title = cursor.getString(2);
        news.content = cursor.getString(3);
        news.contentType = cursor.getString(4);
        news.createdDate = new Date(cursor.getLong(5));
        news.startDate = new Date(cursor.getLong(6));
        news.endDate = new Date(cursor.getLong(7));
        news.priority = cursor.getInt(8);
        String tags = cursor.getString(9);
        if (tags != null && tags.length() > 0)
            news.listTag = new ArrayList<String>(Arrays.asList(tags.split(",")));

        return news;
    }
    
    //Getting All News
    public List<News> getAllNews() {
       List<News> newsList = new ArrayList<News>();
       // Select All Query
       String selectQuery = "SELECT * "+
       		"FROM " + NewsBase.TABLE_NAME + " " +
            "ORDER BY "+ NewsBase.COLUMN_NAME_PRIORITY +" DESC, "+ NewsBase.COLUMN_NAME_CREATEDDATE +" DESC";
    
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
       
       //Looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
               News news = createNewsWithCursor(cursor);

               // Adding news to list
               if (news != null)
                   newsList.add(news);
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       
       // return news list
       return newsList;
    }
    
    //Getting All News by Tag
    public List<News> getAllNewsByTag(String tag) {
       List<News> newsList = new ArrayList<News>();
    	   
       // Select All Query
       String selectQuery = "SELECT * " +
       		"FROM " + NewsBase.TABLE_NAME + " " +
       		"WHERE " + NewsBase.COLUMN_NAME_TAGS +" = '%"+ tag + "%'" +
            "ORDER BY "+ NewsBase.COLUMN_NAME_PRIORITY +" DESC, "+ NewsBase.COLUMN_NAME_CREATEDDATE +" DESC";
    
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);
      
       //Looping through all rows and adding to list
       if (cursor.moveToFirst()) {
           do {
        	   //Create new News with values
               News news = createNewsWithCursor(cursor);

               //Adding news to list
               if (news != null)
                   newsList.add(news);
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       
       // return news list
       return newsList;
    }
    
    //Getting news Count
    public int getNewsCount() {
        String countQuery = "SELECT COUNT(*) FROM " + NewsBase.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();

        return count;
    }
    
    //Updating single News
    public int updateNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        //Values
        ContentValues values = createContentValuesWithNews(news);
     
        String selection = NewsBase._ID + "=?";
        String[] arrayArguments = new String[] { String.valueOf(news.id) };
        
        //Updating row
        int nbRowUpdated = db.update(NewsBase.TABLE_NAME, values, selection, arrayArguments);
        db.close();
        
        return nbRowUpdated;
    }

    public void insertOrUpdateListNews(List<News> listNews) {
        try {
            //Create Map with all News
            List<News> allNews = getAllNews();
            Map<String, News> mapNews = new HashMap<String, News>();
            for (News n : allNews)
                mapNews.put(n.serverId, n);

            //Handle each News
            for (News n : listNews) {
                if (!mapNews.containsKey(n.serverId)) {
                    insertNews(n);
                } else {
                    n.id = mapNews.get(n.serverId).id;
                    updateNews(n);

                    mapNews.remove(n.serverId);
                }
            }

            //Delete News deleted on server
            allNews = new ArrayList<News>(mapNews.values());
            for (News n : allNews)
                deleteNews(n);
        } catch (SQLiteException e) {
            Log.e("DATABASE", "SQLiteException : " + e.getMessage());
        } catch (Exception e) {
            Log.e("DATABASE", "Exception : " + e.getMessage());
        }
    }
    
    //Deleting single news
    public void deleteNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        String selection = NewsBase._ID + "=?";
        String[] arrayArguments = new String[] { String.valueOf(news.id) };
        
        db.delete(NewsBase.TABLE_NAME, selection, arrayArguments);
        db.close();
    }
    
    //Deleting single news
    public void deleteAllNews() {
        SQLiteDatabase db = this.getWritableDatabase();     
        
        db.delete(NewsBase.TABLE_NAME, null, null);
        db.close();
    }

    //Closing DB
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}