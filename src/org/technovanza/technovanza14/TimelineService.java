package org.technovanza.technovanza14;

import java.util.List;

import org.technovanza.technovanza14.NewsActivity.TwitterUpdateReceiver;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class TimelineService extends Service {
	
	/**twitter authentication key*/
	public final static String TWIT_KEY = "An6IwStJFbYnAQYlAI5JnKKaH";
   
	/**twitter secret*/
	public final static String TWIT_SECRET = "5Hl4SBzgxL3bnrwFSrD27Qe1DxKKfzjCg2FWwY4SwSwl2VuPOp";
    
	/**twitter object*/
	private Twitter technoTwitter;
	
	/**database helper object*/
	private NewsTimelineDBHelper timelineHelper;
	
	/**timeline database*/
	private SQLiteDatabase timelineDB;
	
   /**shared preferences for user details*/
	private SharedPreferences technoTwitterPrefs;
	
	/**handler for updater*/
	private Handler timelineHandler;
	
	/**updater thread object*/
	private TimelineUpdater timelineUpdater;

	
	/**delay between fetching new tweets*/
	private static int mins = 5;
	private static final long FETCH_DELAY = mins * (60*1000);
	
	private static int noOfNewTweets;
	//debugging tag
	private String LOG_TAG = "TimelineService";
	
	@Override
	public void onCreate()
	{
	    //setup the class 
	    //get preferencess
	    technoTwitterPrefs = getSharedPreferences("Twitter Preferences", 0);
	    //get DB helper
	    timelineHelper = new NewsTimelineDBHelper(this);
	    //get the database
	    timelineDB = timelineHelper.getWritableDatabase();
	    //get user preferences
	    String userToken = technoTwitterPrefs.getString("TECHNO_USER_TOKEN", null);
	    String userSecret = technoTwitterPrefs.getString("TECHNO_TOKEN_SECRET", null);
	    if(userToken!=null)
	    {	
	    	//create new configuration
	    	Configuration twitConf = new ConfigurationBuilder()
	        	.setOAuthConsumerKey(TWIT_KEY)
	        	.setOAuthConsumerSecret(TWIT_SECRET)
	        	.setOAuthAccessToken(userToken)
	        	.setOAuthAccessTokenSecret(userSecret)
	        	.build();
	        	//instantiate new twitter
	    		technoTwitter = new TwitterFactory(twitConf).getInstance();
	   	    		
	    }
	    else
	    {
	    	stopSelf();
	    }
	    


	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	
	/**
	 * TimelineUpdater class implements the runnable interface
	 */
	class TimelineUpdater implements Runnable 
	{

		@Override
		public void run()
		{
		    //check for updates - assume none
			try 
			{	
				new GetTimeline().execute() ;
			} 
			catch (Exception te) 
			{
				Log.e(LOG_TAG, "Exception: " + te);
			}
   		    //delay fetching new updates
			timelineHandler.postDelayed(this, FETCH_DELAY);
			
			
		}
   	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		//get handler
	    timelineHandler = new Handler();
	    //create an instance of the updater class
	    timelineUpdater = new TimelineUpdater();
	     //add to run queue
	    timelineHandler.post(timelineUpdater);
	    //return sticky
	    return START_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
	    super.onDestroy();
	    //stop the updating
	    timelineHandler.removeCallbacks(timelineUpdater);
	    timelineDB.close();
	}
	private class GetTimeline extends AsyncTask<String, String, Boolean>
	{
			
	        @Override
	        protected Boolean doInBackground(String... args)
	        {
	        	try
	        	{
	        		Log.i("TimelineService", "Start downloading");
					noOfNewTweets=technoTwitterPrefs.getInt("NO_OF_NEW_TWEETS", 0);
				    //fetch timeline
					//retrieve the Technovanza timeline tweets as a list
					List<twitter4j.Status> homeTimeline = technoTwitter.getUserTimeline("Technovanza");
					Log.i("TimelineService", "Stop downloading");
					boolean statusChanges=false;
					//iterate through new status updates
					for (twitter4j.Status statusUpdate : homeTimeline) 
					{
					    //call the getValues method of the data helper class, passing the new updates
					    ContentValues timelineValues =  NewsTimelineDBHelper.getValues(statusUpdate);
					    //if the database already contains the updates they will not be inserted
					    timelineDB.insertOrThrow("home", null, timelineValues);
					    //confirm we have new updates
					    noOfNewTweets++;
					    statusChanges = true;
					}
					return statusChanges;				
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
	            	
	            	SharedPreferences.Editor edit = technoTwitterPrefs.edit();
	        		edit.putInt("NO_OF_NEW_TWEETS", noOfNewTweets).commit();
				    //this should be received in the main timeline class otherwise a push notification is displayed
				    getApplicationContext().sendOrderedBroadcast(
							new Intent("TWITTER_UPDATES"), 
							null,
							new BroadcastReceiver() {

								@Override
								public void onReceive(Context context, Intent intent) {

									
									final Intent restartNewsActivtyIntent = new Intent(getApplicationContext(),
											NewsActivity.class);
									restartNewsActivtyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									if (getResultCode()!=Activity.RESULT_OK)
									{
										final PendingIntent pendingIntent = PendingIntent.getActivity(context, getResultCode(), restartNewsActivtyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
										NotificationCompat.Builder notification = new  NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_launcher).setContentText(technoTwitterPrefs.getInt("NO_OF_NEW_TWEETS", 0)+" new tweets received").setContentTitle("TechnoTwitter").setAutoCancel(true).setContentIntent(pendingIntent);
										NotificationManager newNotificationManager=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
										newNotificationManager.notify(1, notification.build());
									}
								}
							}, 
							null, 
							0, 
							null, 
							null);
	                    
	            }
	          }
	         
	         
	}

}
