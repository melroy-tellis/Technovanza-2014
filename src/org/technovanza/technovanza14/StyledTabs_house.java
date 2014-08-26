package org.technovanza.technovanza14;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.viewpagerindicator.TabPageIndicator;
//import com.gotechno.technovanza13.StyledTabs.EventsAdapter;

public class StyledTabs_house extends SherlockFragmentActivity {
	
	    private static final String[] CONTENT = new String[] {"Overview","Houses","Coins&Prizes","Updates"};
	    public static final String PREFS_NAME="HouseCupFile";
	    String color;
	    public static Activity a;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.simple_tabs3);
	        final String android_id = Secure.getString(getContentResolver(),
	                Secure.ANDROID_ID);
	        Parse.initialize(this, "jWNXwn3j7fmaub8bFMWYy3sRWJgXCMaqCljKOUyn", "AmQgVA2i3Gc45vHfYwqfPcJJzAyvwihvmgYcRx86");
	        a=this;
	        //row.setBackgroundResource(R.drawable.green_house);
	        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            final SharedPreferences.Editor editor = settings.edit();
	        FragmentPagerAdapter adapter = new EventsAdapter(getSupportFragmentManager());
	       
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        ViewPager pager = (ViewPager)findViewById(R.id.pager3);
	        pager.setAdapter(adapter);

	        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator3);
	        indicator.setViewPager(pager);
	        
	        	if(settings.getBoolean("ISNAME",true))
	        	{
	        		ParseQuery<ParseObject> query = ParseQuery.getQuery("HouseCup");
	        		query.whereEqualTo("ID",android_id);
	        		query.getFirstInBackground(new GetCallback<ParseObject>() {
	        		  public void done(ParseObject object, ParseException e) {
	        		    if (object == null) {
	        		      Log.d("score", "The getFirst request failed.");
	        		    } else {
	        		      Log.d("score", "Retrieved the object.");
	        		    }
	        		  }

	    			@Override
	    			public void done(ParseObject obj,
	    					com.parse.ParseException arg1) {
	    				// TODO Auto-generated method stub
	    				 if (obj == null) {
	            		      //Log.d("score", "The getFirst request failed.");
	    					
	            		    } else {
	            		      //Log.d("score", "Retrieved the object.");
	            		    	obj.put("NAME",settings.getString("FBNAME","NULL"));
	            		    	obj.saveInBackground();
	            		    	editor.putBoolean("ISNAME",false);
	            		    	editor.commit();
	            		    }
	    				
	    			}
	        		});
	        		
	        	}
	        
	        	if(settings.getString("HOUSE",null)!=null)
	        	{
	        		color=settings.getString("HOUSE",null);
	        	}
	        	else 
	        	{
	        		if(!isNetworkAvailable())
	    			{
	    				
	    			AlertDialog.Builder adb = new AlertDialog.Builder(this);
	    			adb.setTitle("Attention");
	    	        adb.setMessage(Html.fromHtml("Loading your house preference failed.Connect to the server and try again!"));
	    	        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
	    	            public void onClick(DialogInterface dialog, int which) {
	    	            	
	    	            	             	
	    	            	
	    	             	   
	    	             	   
	    	             	   
	    	                   
	    	              
	    	            }
	    	        });

	    	        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    	            public void onClick(DialogInterface dialog, int which) {
	    	               
	    	            }
	    	        });
	    	     AlertDialog dialog = adb.create();
	     	   dialog.setCanceledOnTouchOutside(false);
	     	   dialog.setCancelable(false);
	     	   dialog.show();
	    		}
	        		else
	        		{
	        			final ProgressDialog pd=new ProgressDialog(this);
	           	   		pd.setMessage("Loading...Please Wait!");
	           	   		pd.setCancelable(false);
	           		pd.show();
	        	
	        		ParseQuery<ParseObject> query = ParseQuery.getQuery("HouseCup");
	        		query.whereEqualTo("ID",android_id);
	        		query.getFirstInBackground(new GetCallback<ParseObject>() {
	        		  public void done(ParseObject object, ParseException e) {
	        		    if (object == null) {
	        		      Log.d("score", "The getFirst request failed.");
	        		    } else {
	        		      Log.d("score", "Retrieved the object.");
	        		    }
	        		  }

	    			@Override
	    			public void done(ParseObject obj,
	    					com.parse.ParseException arg1) {
	    				// TODO Auto-generated method stub
	    				 if (obj == null) {
	            		      //Log.d("score", "The getFirst request failed.");
	    					 pd.dismiss();
	    					 DialogFragment newFragment = new FirstDialogFragment();
	    		    			
	    		    			
	    		    		    newFragment.show(getSupportFragmentManager(), "missiles");
	    		    		    
	            		    } else {
	            		      //Log.d("score", "Retrieved the object.");
	            		    	pd.dismiss();
	            		    	editor.putString("ID",obj.getString("ID"));
	            		    	editor.putString("HOUSE",obj.getString("HOUSE"));
	            		    }
	    				
	    			}
	        		});
	        		
	        	}
	        	}
	        
	        	}      
	    

	   /* class GoogleMusicAdapter extends FragmentPagerAdapter {
	        public GoogleMusicAdapter(FragmentManager fm) {
	            super(fm);
	        }*/
	    private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	    public boolean onOptionsItemSelected(MenuItem item) {
	   		// TODO Auto-generated method stub
	       	switch(item.getItemId()){
	       	case android.R.id.home:
	       		NavUtils.navigateUpFromSameTask(this);
	       		return true;
	       	}
	   		return super.onOptionsItemSelected(item);
	   	}
	        class EventsAdapter extends FragmentPagerAdapter {
	            public EventsAdapter(FragmentManager fm) {
	                super(fm);
	            }
	        @Override
	        public Fragment getItem(int position) {
	            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
	        }

	        @Override
	        public CharSequence getPageTitle(int position) {
	            return CONTENT[position % CONTENT.length];
	        }

	        @Override
	        public int getCount() {
	            return CONTENT.length;
	        }
	    }
	}

