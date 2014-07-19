package org.technovanza.technovanza14;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TechnoTimelineAdapter extends SimpleCursorAdapter 
{
	
    /**twitter developer key*/
	public final static String TWIT_KEY = "An6IwStJFbYnAQYlAI5JnKKaH";
    /**twitter developer secret*/
	public final static String TWIT_SECRET = "5Hl4SBzgxL3bnrwFSrD27Qe1DxKKfzjCg2FWwY4SwSwl2VuPOp";
      
    /**strings representing database column names to map to views*/
	/*static final String[] from = { "update_text", "user_screen", 
    "update_time", "user_img" };*/
	static final String[] from = { "update_text", "user_screen", 
	    "update_time" };
   
	/**view item IDs for mapping database record values to*/
	/*static final int[] to = { R.id.updateText, R.id.userScreen, 
    R.id.updateTime, R.id.userImg };*/
	static final int[] to = { R.id.updateText, R.id.userScreen, 
	    R.id.updateTime };  
	
	/**
	 * constructor sets up adapter, passing 'from' data and 'to' views
	 * @param context
	 * @param c
	 */
	public TechnoTimelineAdapter(Context context, Cursor c)
	{
		super(context, R.layout.status_layout, c, from, to);
		
	}
	
	/*
	 * Bind the data to the visible views
	 */
	@Override
	public void bindView(View row, Context context, Cursor cursor)
	{
	    super.bindView(row, context, cursor);
	    /*try 
	    {
	        //get profile image
	    	//URL profileURL = 
	        //new URL(cursor.getString(cursor.getColumnIndex("user_img")));
	  
	        //set the image in the view for the current tweet
	    	/*ImageView profPic = (ImageView)row.findViewById(R.id.userImg);
	    	profPic.setImageDrawable(Drawable.createFromStream
	        ((InputStream)profileURL.getContent(), ""));
	    }
	    catch(Exception te)
	    {
	    	Log.e(LOG_TAG, te.getMessage());
	    }*/
	    //get the update time
	    long createdAt = cursor.getLong(cursor.getColumnIndex("update_time"));
	    //get the update time view
	    TextView textCreatedAt = (TextView)row.findViewById(R.id.updateTime);
	    //adjust the way the time is displayed to make it human-readable
	    textCreatedAt.setText(DateUtils.getRelativeTimeSpanString(createdAt)+" ");
	}
		
	
	}
