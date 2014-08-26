package org.technovanza.technovanza14;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;

public class SecondDialogFragment extends DialogFragment{

	
	
	FragmentManager fm;
	private Point p;
	
	AlertDialog d;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        LayoutInflater adbInflater = LayoutInflater.from(getActivity());
        //View eulaLayout = adbInflater.inflate(R.layout.house_popup_layout, null);
        //dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        //adb.setView(eulaLayout);
        adb.setCancelable(false);
      
        adb.setTitle("Attention");
        adb.setMessage(Html.fromHtml("You aren't connected to internet. Please connect to the internet and try again."));
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	 DialogFragment newFragment = new FirstDialogFragment();
     			
     			
     		    newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
            		
               /* String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipMessage", checkBoxResult);
                // Commit the edits!
                editor.commit();
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://misha.beshkin.lv/android-alertdialog-with-checkbox/")));
                return;*/
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               /* String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipMessage", checkBoxResult);
                // Commit the edits!
                editor.commit();
                return;*/
            	 DialogFragment newFragment = new FirstDialogFragment();
     			
     			
     		    newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
            }
        });
        //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //String skipMessage = settings.getString("skipMessage", "NOT checked");
        //if (!skipMessage.equals("checked"))
            //adb.show();
	
	
	
	//ActionBar ab = getSupportActionBar();
	p = new Point();
    p.x = 100;
    p.y = 100;
	
	    //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
	    // Get the layout inflater
	    //LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    /*builder.setView(inflater.inflate(R.layout.house_popup_layout,null))
	    // Add action buttons
	           .setPositiveButton(R.string.ok_pop, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   //Intent i = new Intent(mActivity, ShareButtonActivity.class);
	            	   //startActivity(i);
	            	   
	            	  // DialogFragment newFragment = new SeconddialogueFragment();
	   				
	   			    //newFragment.show(getFragmentManager(), "missiles");
	            	  
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   FirstDialogFragment.this.getDialog().cancel();
	               }
	           }); 
	    AlertDialog dialog = builder.create();
	     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

	 wmlp.gravity = Gravity.TOP|Gravity.LEFT;
	 wmlp.x = 100;   //x position
	 wmlp.y = 400; 
	 dialog.getWindow().setAttributes(wmlp); //y position

	    return dialog;*/
    AlertDialog dialog = adb.create();
   dialog.setCanceledOnTouchOutside(false);
   dialog.setCancelable(false);
   

    return dialog;
	}
	
	
}

