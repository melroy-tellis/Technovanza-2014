package org.technovanza.technovanza14;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.Parse;



@SuppressLint("ValidFragment")
public class FirstDialogFragment extends DialogFragment{

	
	public  static String TAG = MainActivity.class.getSimpleName();
	public static int count=0;
	FragmentManager fm;
	//Activity a;
	private Point p;
	public static final String PREFS_NAME="HouseCupFile";
	AlertDialog d;
	String name,fbname;
	private boolean check=false;
	private String selection=null;
	//private ParseObject testObjec=null;

	
	
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
        LayoutInflater adbInflater = LayoutInflater.from(getActivity());
        final View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setCancelable(false);
      
        adb.setTitle("Vote for your house");
        //adb.setMessage(Html.fromHtml("You haven't yet registered yourself. Please register to be able to participate in events."));
        adb.setPositiveButton("Done",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	             	
            	 if(!isNetworkAvailable())
                	{
                		 DialogFragment newFragment = new SecondDialogFragment();
             			
             			
             		    newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
                	}
                	else
                	{
                		
               
                		RadioGroup rg = (RadioGroup) eulaLayout.findViewById(R.id.radioHouse);
                		if(rg.getCheckedRadioButtonId()!=-1){
                		    int id= rg.getCheckedRadioButtonId();
                		    View radioButton = rg.findViewById(id);
                		    int radioId = rg.indexOfChild(radioButton);
                		    RadioButton btn = (RadioButton) rg.getChildAt(radioId);
                		    selection = (String) btn.getText();
                		}
                		 DialogFragment newFragment = new FinalHouseFragment(selection,getActivity());
 		    			
 		    			
 		    		    newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
                		/*
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
    	    	            		    		  
    	    	            		    		  
    	    	            		    	  }
    	    	            		      }
    	    	            		      
    	    	            		      obj.saveInBackground();
    	    	            		    } else {
    	    	            		      //Log.d("score", "Retrieved the object.");
    	    	            		    	obj.put("HOUSE",selection);
    	    	            		    	obj.saveInBackground();
    	    	            		    }
    	    						 Intent i= new Intent(StyledTabs_house.a,StyledTabs_house.class);
     		                		startActivity(i);
     		                		StyledTabs_house.a.finish();
    	    						
    	    					}
    	                		});
    	                			
    	                		
    	                		
    	    	                   
    	    	              
    	    	            }
    	    	        });

    	    	        adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	    	            public void onClick(DialogInterface dialog, int which) {
    	    	            	
    	    	            	Intent i= new Intent(getActivity(),StyledTabs_house.class);
    	                		startActivity(i);
    	                		getActivity().finish();	
    	    	               
    	    	            }
    	    	        });
    	    	     AlertDialog dialog1 = adb.create();
    	     	   dialog1.setCanceledOnTouchOutside(false);
    	     	   dialog1.setCancelable(false);
    	     	   dialog1.show();
                		
                		
                	*/	
                		
                	}
             	   
             	   
             	   
                   
              
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               
            }
        });
        
       
	
	p = new Point();
    p.x = 100;
    p.y = 100;
	
	    
    final AlertDialog dialog = adb.create();
   dialog.setCanceledOnTouchOutside(false);
   dialog.setCancelable(false);
   

    return dialog;
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

