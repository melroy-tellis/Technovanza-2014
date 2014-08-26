package org.technovanza.technovanza14;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class FinalHouseFragment extends DialogFragment{

	
	public  static String TAG = MainActivity.class.getSimpleName();
	public static int count=0;
	FragmentManager fm;
	Activity a;
	private Point p;
	public static final String PREFS_NAME="HouseCupFile";
	AlertDialog d;
	String name,fbname;
	
	private boolean check=false;
	private String selection=null;
	//private ParseObject testObjec=null;
	FinalHouseFragment(String select,Activity a)
	{
		selection=select;
		this.a=a;
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final SharedPreferences settings=getActivity().getSharedPreferences(PREFS_NAME,0);
		final SharedPreferences.Editor editor=settings.edit();
		final String android_id = Secure.getString(getActivity().getContentResolver(),
                Secure.ANDROID_ID);
	//	a=getActivity();
		Parse.initialize(getActivity(), "jWNXwn3j7fmaub8bFMWYy3sRWJgXCMaqCljKOUyn", "AmQgVA2i3Gc45vHfYwqfPcJJzAyvwihvmgYcRx86");
		name=settings.getString("NAME",null);
		fbname=settings.getString("FBNAME",null);
		//testObjec = new ParseObject("HouseCup");
	
                		
                		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
    	    			adb.setTitle("Confirm");
    	    	        adb.setMessage(Html.fromHtml("Are you sure you want to vote for "+selection+". Once voted, it cannot be changed again."));
    	    	        adb.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    	    	            public void onClick(DialogInterface dialog, int which) {
    	    	            	
    	    	            	             	
    	    	            	
    	    	            	editor.putString("ID",android_id);
    	                		editor.commit();
    	                		editor.putString("HOUSE", selection);
    	                		editor.commit();
    	                		
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
    	    	            		      obj=new ParseObject("HouseCup");
    	    	            		      obj.put("ID",android_id);
    	    	            		      obj.put("HOUSE",selection);
    	    	            		      if(fbname!=null)
    	    	            		      {
    	    	            		    	  obj.put("NAME",fbname);
    	    	            		      }
    	    	            		      else
    	    	            		      {
    	    	            		    	  if(name!=null)
    	    	            		    	  {
    	    	            		    		  obj.put("NAME",name);
    	    	            		    	  }
    	    	            		    	  else
    	    	            		    	  {
    	    	            		    		  obj.put("NAME","UNKNOWN");
    	    	            		    		  /*editor.putString("FBNAME","UNKNOWN");
    	    	            		    		 editor.commit();
    	    	            		    		 editor.putString("NAME","UNKNOWN");
    	    	            		    		 editor.commit();*/
    	    	            		    		  
    	    	            		    	  }
    	    	            		      }
    	    	            		      
    	    	            		      obj.saveInBackground();
    	    	            		    } else {
    	    	            		      //Log.d("score", "Retrieved the object.");
    	    	            		    	obj.put("HOUSE",selection);
    	    	            		    	obj.saveInBackground();
    	    	            		    }
    	    						
    	    						
    	    					}
    	                		});
    	                		 Intent i= new Intent(a,StyledTabs_house.class);
  		                		startActivity(i);
  		                		a.finish();
    	                		
    	                		/*LayoutInflater inflater=getActivity().getLayoutInflater();
    	                        View row = inflater.inflate(R.layout.overview, null);
    	                        setBack(row,settings.getString("HOUSE","normal"));
    	                		*/
    	            			//RelativeLayout content = (RelativeLayout)row.findViewById(R.id.rr); 
    	    	             	   
    	    	             	   
    	    	                   
    	    	              
    	    	            }
    	    	        });

    	    	        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	    	            public void onClick(DialogInterface dialog, int which) {
    	    	            	
    	    	            	Intent i= new Intent(a,StyledTabs_house.class);
    	                		startActivity(i);
    	                		a.finish();	
    	    	               
    	    	            }
    	    	        });
    	    	     AlertDialog dialog1 = adb.create();
    	     	   dialog1.setCanceledOnTouchOutside(false);
    	     	   dialog1.setCancelable(false);
    	     	 
                		
                		
                		
                	
    return dialog1;
	}
	public void setBack(View myview,String color)
    {
    	if(color.equals("normal"))
    	{
    		myview.setBackgroundResource(R.drawable.common_back);
    	}
    	else if(color.equals("Green"))
    	{
    		myview.setBackgroundResource(R.drawable.green_house);
    	}
    	else if(color.equals("Red"))
    	{
    		myview.setBackgroundResource(R.drawable.red_house1);
    	}
    	else if(color.equals("Blue"))
    	{
    		myview.setBackgroundResource(R.drawable.blue_house);
    	}
    	else
    	{
    		myview.setBackgroundResource(R.drawable.common_back);
    	}
    }
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}

