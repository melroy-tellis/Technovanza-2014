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
import android.text.Html;
import android.util.Log;
import android.view.View;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity   {
	public static final String PREFS_NAME="HouseCupFile";
	TechnoTileFragment mEventsFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeEventsFragment);
	TechnoTileFragment mGalleryFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeGalleryFragment);
	TechnoTileFragment mTwitterFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeTwitterFragment);
	TechnoTileFragment mHousecupFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeHousecupFragment);
	TechnoTileFragment mFacebookFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeFacebookFragment);
	TechnoTileFragment mAboutFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeAboutFragment);
	
	int mEventsFragContainer,mGalleryFragContainer,mHousecupFragContainer,mTwitterFragContainer,mFacebookFragContainer,mAboutFragContainer ;  
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		
		mEventsFragContainer=R.id.frag1;
		mGalleryFragContainer=R.id.frag2;
		mHousecupFragContainer=R.id.frag3;
		mTwitterFragContainer=R.id.frag4 ;
		mFacebookFragContainer=R.id.frag5 ;
		mAboutFragContainer=R.id.frag6 ;
		
		getFragmentManager().beginTransaction().add(mEventsFragContainer, mEventsFragment).add(mGalleryFragContainer, mGalleryFragment).add(mHousecupFragContainer, mHousecupFragment).add(mTwitterFragContainer, mTwitterFragment).add(mFacebookFragContainer, mFacebookFragment).add(mAboutFragContainer, mAboutFragment).commit();
		final SharedPreferences settings= this.getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();
        final String android_id = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);
Parse.initialize(this, "jWNXwn3j7fmaub8bFMWYy3sRWJgXCMaqCljKOUyn", "AmQgVA2i3Gc45vHfYwqfPcJJzAyvwihvmgYcRx86");
       if(settings.getString("HOUSE",null)!=null)
   	{
   	
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
   	  pd.setMessage("Loading your house preference.Please Wait!");
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
       	   } else {
       	     //Log.d("score", "Retrieved the object.");
       	   	editor.putString("ID",obj.getString("ID"));
       	   	editor.commit();
       	   	editor.putString("HOUSE",obj.getString("HOUSE"));
       	   	editor.commit();
       	   	editor.putString("FBNAME",obj.getString("NAME"));
       	   	editor.commit();
       	   	editor.putBoolean("ISNAME",false);
       	   	editor.commit();
       	   	dismisspd(pd);
       	   	
       	   }
}
   	});
   	
   	
   	
   	}
	}
	}
	public void dismisspd(ProgressDialog p)
	{
	p.dismiss();
	}
	
	public void replaceFragment(View view, int draggedType ,int stationaryType)
	{
		int stationaryFragmentContainer = getMemberFragmentContainerOfType(stationaryType);
		int draggedFragmentContainer = getMemberFragmentContainerOfType(draggedType);
		getFragmentManager().beginTransaction().remove(getMemberFragmentOfType(draggedType)).commit();
		getFragmentManager().executePendingTransactions();
		getFragmentManager().beginTransaction().replace(stationaryFragmentContainer, getMemberFragmentOfType(draggedType)).commit();
		getFragmentManager().executePendingTransactions();
		getFragmentManager().beginTransaction().replace(draggedFragmentContainer, getMemberFragmentOfType(stationaryType)).commit();
		getFragmentManager().executePendingTransactions();
		setMemberFragmentContainerOfType(stationaryType, draggedFragmentContainer);
		setMemberFragmentContainerOfType(draggedType, stationaryFragmentContainer) ;
	}
	private boolean isNetworkAvailable() {
		   ConnectivityManager connectivityManager 
		         = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		   NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		   return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	private TechnoTileFragment getMemberFragmentOfType(int type)
	{
		switch(type)
		{
			case TechnoTileFragment.typeEventsFragment:
				return mEventsFragment;
			case TechnoTileFragment.typeGalleryFragment:
				return mGalleryFragment;
			case TechnoTileFragment.typeHousecupFragment:
				return mHousecupFragment;
			case TechnoTileFragment.typeTwitterFragment:
				return mTwitterFragment;
			case TechnoTileFragment.typeFacebookFragment:
				return mFacebookFragment;	
			default:	
				return mAboutFragment;
		}
		
	}
	
	private int getMemberFragmentContainerOfType(int type)
	{
		switch(type)
		{
			case TechnoTileFragment.typeEventsFragment:
				return mEventsFragContainer;
			case TechnoTileFragment.typeGalleryFragment:
				return mGalleryFragContainer;
			case TechnoTileFragment.typeTwitterFragment:
				return mTwitterFragContainer;
			case TechnoTileFragment.typeFacebookFragment:
				return mFacebookFragContainer;
			case TechnoTileFragment.typeHousecupFragment:
				return mHousecupFragContainer;	
			default:	
				return mAboutFragContainer;
	
		}
		
	}
	
	private void setMemberFragmentContainerOfType(int type,int containerID)
	{
		switch(type)
		{
			case TechnoTileFragment.typeEventsFragment:
				mEventsFragContainer = containerID ;
				break;
			case TechnoTileFragment.typeGalleryFragment:
				mGalleryFragContainer = containerID;
				break;
			case TechnoTileFragment.typeHousecupFragment:
				mHousecupFragContainer  = containerID;	
				break;	
			case TechnoTileFragment.typeTwitterFragment:
				mTwitterFragContainer  = containerID;	
				break;
			case TechnoTileFragment.typeFacebookFragment:
				mFacebookFragContainer  = containerID;	
				break;	
			default:	
				mAboutFragContainer  = containerID;
	
		}
		
	}
	

}
