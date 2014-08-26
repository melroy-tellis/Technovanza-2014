package org.technovanza.technovanza14;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.Bitmap;

public class NewsActivity extends Activity
{
	
	/**developer account key for this app*/
	public final static String TWIT_KEY = "An6IwStJFbYnAQYlAI5JnKKaH";
	/**developer secret for the app*/
	public final static String TWIT_SECRET = "5Hl4SBzgxL3bnrwFSrD27Qe1DxKKfzjCg2FWwY4SwSwl2VuPOp";
	/**Twitter instance*/
	private Twitter technoTwitter;
	/**request token for accessing user account*/
	private RequestToken technoTwitterRequestToken; 
	/**shared preferences to store user details*/
	private SharedPreferences technoTwitterPrefs;
	/**app url*/
	public final static String TWIT_URL = "oauth://www.technovanza.org";
	/**string to store OAuth verifier*/
	String technoOAuthVerifier;
    /**main view for the home timeline*/
	private ListView homeTimeline;
    /**database helper for update data*/
	private NewsTimelineDBHelper timelineHelper;
    /**update database*/
	private SQLiteDatabase timelineDB;
    /**cursor for handling data*/
	private Cursor timelineCursor;
    /**adapter for mapping data*/
	private TechnoTimelineAdapter timelineAdapter;
	
	/**Broadcast receiver for when new updates are available*/
	private BroadcastReceiver technoStatusReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		technoTwitterPrefs = getSharedPreferences("Twitter Preferences", 0);
			//find out if the user preferences are set
			if(technoTwitterPrefs.getString("TECHNO_USER_TOKEN", null)==null)
			{
				//no user preferences so prompt to sign in
				setContentView(R.layout.news_activity_initial_layout);
				//get a twitter instance for authentication
				technoTwitter = new TwitterFactory().getInstance();
		        //pass developer key and secret
				technoTwitter.setOAuthConsumer(TWIT_KEY, TWIT_SECRET);
		       //setup button for click listener
				Button signIn = (Button)findViewById(R.id.signin);
				signIn.setOnClickListener(new OnClickListener()
												{
													@Override
													public void onClick(View view)
													{
														//take user to twitter authentication web page to 
														//allow app access to their twitter account
														new GetTechnoTwitterToken().execute();
													}
		    	
												});
			}
			else setupTimeline();
	}
	
	private class GetTechnoTwitterToken extends AsyncTask<String, String, String>
	{
		Dialog auth_dialog = new Dialog(NewsActivity.this);
		String oauth_url;
		ProgressDialog progress;
      	@Override
        protected void onPreExecute()
      	{
            super.onPreExecute();
            progress = new ProgressDialog(NewsActivity.this);
            progress.setMessage("Retrieving authentication request token");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
      	}
		@Override
        protected String doInBackground(String... args)
        {
        	try 
        	{
        		technoTwitterRequestToken = technoTwitter.getOAuthRequestToken(TWIT_URL);
        		oauth_url = technoTwitterRequestToken.getAuthorizationURL();
        	} 
        	catch (TwitterException e) 
        	{
        		e.printStackTrace();
        	}
        	return oauth_url;
        }
        @Override
        protected void onPostExecute(String oauth_url)
        {
        	progress.hide();
        	if(oauth_url != null)
        	{	
        		auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        		auth_dialog.setContentView(R.layout.auth_dialog);
        		WebView web = (WebView)auth_dialog.findViewById(R.id.webv);
        		web.getSettings().setJavaScriptEnabled(true);
        		web.loadUrl(oauth_url);
        		web.setWebViewClient(new WebViewClient() 
          							{
                  						boolean authComplete = false;
                  						@Override
                  						public void onPageStarted(WebView view, String url, 
                  								Bitmap favicon)
                  						{
                  							super.onPageStarted(view, url, favicon);
                  						}
                  						@Override
                  						public void onPageFinished(WebView view, String url)
                  						{
                  							super.onPageFinished(view, url);
                  							if (url.contains("oauth_verifier") && authComplete == false)
                  							{
                  								authComplete = true;
                  								Log.e("Url",url);
                  								Uri uri = Uri.parse(url);
                  								technoOAuthVerifier = uri.getQueryParameter("oauth_verifier");
                  								auth_dialog.cancel();
                  								new GetTechnoTwitterAccessToken().execute();
                  							}
                  							else if(url.contains("denied"))
                  							{
                  								auth_dialog.cancel();
                  								Toast.makeText(NewsActivity.this, "Sorry !, " +
                  										"Permission Denied", Toast.LENGTH_SHORT).show();
                  							}
                  						}
          							});
        		auth_dialog.show();
        		auth_dialog.setCancelable(true);
        		
        	}
        	else
        	{
                 Toast.makeText(NewsActivity.this, "Sorry !, Network Error or Invalid Credentials", Toast.LENGTH_SHORT).show();
        	}
         }
    }
		
	private class GetTechnoTwitterAccessToken extends AsyncTask<String, String, Boolean>
	{
			ProgressDialog progress;
	      	@Override
	        protected void onPreExecute()
	      	{
	            super.onPreExecute();
	            progress = new ProgressDialog(NewsActivity.this);
	            progress.setMessage("Retrieving access token and user information");
	            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progress.setIndeterminate(true);
	            progress.show();
	      	}
	        @Override
	        protected Boolean doInBackground(String... args)
	        {
	        	try
	        	{
	        		AccessToken accessToken = technoTwitter.getOAuthAccessToken(technoTwitterRequestToken, technoOAuthVerifier);
	        		SharedPreferences.Editor edit = technoTwitterPrefs.edit();
	        		edit.putString("TECHNO_USER_TOKEN", accessToken.getToken());
	        		edit.putString("TECHNO_TOKEN_SECRET", accessToken.getTokenSecret());
	        		User technoTwitterUser = technoTwitter.showUser(accessToken.getUserId());
	        		String profile_url = technoTwitterUser.getOriginalProfileImageURL();
	        		edit.putString("NAME", technoTwitterUser.getName());
	        		edit.putString("IMAGE_URL", profile_url);
	        		edit.commit();
	        		return true;
	        	}
	        	catch (TwitterException e)
	        	{	        
	        		e.printStackTrace();
	        	}
	            return false;
	         }
	         @Override
	         protected void onPostExecute(Boolean response)
	         {
	            if(response)
	            {
	            	progress.hide();
	            	NewsActivity.this.startService(new Intent(getApplicationContext(),TimelineService.class));
	            	NewsActivity.this.setupTimeline();
	                    
	            }
	          }
	         
	         
	}
	
	/**
	 * setupTimeline displays the Technovanza Twitter timeline
	 */
	@SuppressWarnings("deprecation")
	private void setupTimeline() 
	{
		try
		{
				
				setContentView(R.layout.timeline_layout);
				homeTimeline=(ListView)findViewById(R.id.homeList);
				//get the timeline
				//instantiate database helper
				timelineHelper = new NewsTimelineDBHelper(getApplicationContext());
				//get the database
				timelineDB = timelineHelper.getReadableDatabase();
				//query the database, most recent tweets first
				timelineCursor = timelineDB.query
				    ("home", null, null, null, null, null, "update_time DESC");
				
				//manage the updates using a cursor
				this.startManagingCursor(timelineCursor);
				//instantiate adapter
				timelineAdapter = new TechnoTimelineAdapter(getApplicationContext(), timelineCursor);
				//this will make the app populate the new update data in the timeline view
				homeTimeline.setAdapter(timelineAdapter);
				SharedPreferences.Editor edit = technoTwitterPrefs.edit();
				edit.putInt("NO_OF_NEW_TWEETS", 0).commit();
		}
	    catch(Exception te)
	    {
	    	Log.e("NewsActivity", "Failed to fetch timeline: " + te.getMessage());
	    }
		
	}
			
	/**
	 * Class to implement Broadcast receipt for new updates
	 */
	class TwitterUpdateReceiver extends BroadcastReceiver
	{

		@SuppressWarnings({ "deprecation" })
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			
			Toast.makeText(NewsActivity.this, "New tweets have been downloaded ", Toast.LENGTH_SHORT).show();
			if(isOrderedBroadcast()) setResultCode(Activity.RESULT_OK); 
			int rowLimit = 100;
			if(DatabaseUtils.queryNumEntries(timelineDB, "home")>rowLimit)
			{
			    String deleteQuery = "DELETE FROM home WHERE "+BaseColumns._ID+" NOT IN " +
			        "(SELECT "+BaseColumns._ID+" FROM home ORDER BY "+"update_time DESC " +
			        "limit "+rowLimit+")";  
			    timelineDB.execSQL(deleteQuery);
			}
			timelineCursor = timelineDB.query("home", null, null, null, null, null, "update_time DESC");
			startManagingCursor(timelineCursor);
			timelineAdapter = new TechnoTimelineAdapter(context, timelineCursor);
			homeTimeline.setAdapter(timelineAdapter);
		 }
	  
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
	    try
	    {
	        //Unregister the new tweets BroadcastReceiver
	    	unregisterReceiver(technoStatusReceiver);
	    }
	    catch(Exception se)
	    {
	    	Log.e("NewsActivity", "Unable to unregister receiver"+se.getMessage());
	    }
	}
	@Override
	public void onResume()
	{	super.onResume();
	    try
	    {
	    	//instantiate receiver class for finding out when new updates are available
	    	technoStatusReceiver = new TwitterUpdateReceiver();
	    	//register for updates
	    	registerReceiver(technoStatusReceiver, new IntentFilter("TWITTER_UPDATES"));
	    }
	    catch(Exception se)
	    {
	    	Log.e("NewsActivity", "unable to stop Service or receiver");
	    }
	}
	
	
			
	@Override
	public void onDestroy()
	{
	    super.onDestroy();
	    //Close the database
	    try
	    {
	    	timelineDB.close();
	    }
	    catch(Exception e)
	    {
	    	Log.e("Twitter",e.getMessage());
	    }
	 }
	
 }
