package org.technovanza.technovanza14;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
 
public class loginActivity extends Activity {
	 private static final String PERMISSION = "publish_actions";


	    public static final String PREFS_NAME="HouseCupFile";

	String password;
	String id;
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
	    private LoginButton loginButton;
	    private GraphUser user;
	    private boolean canPresentShareDialog;
	    private boolean canPresentShareDialogWithPhotos;

	
	    SharedPreferences.Editor editor;
		  SharedPreferences settings;
		
	
	
	
	
	
	
	  
	
	
	 private UiLifecycleHelper uiHelper;

	    private Session.StatusCallback callback = new Session.StatusCallback() {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            onSessionStateChange(session, state, exception);
	        }
	    };

	    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.d("HelloFacebook", "Success!");
	        }
	    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                loginActivity.this.user = user;
               
                
                  updateUI();
                // It's possible that we were waiting for this.user to be populated in order to post a
                // status update.
             //   handlePendingAction();
            }
        });

    //    setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
      final EditText e_id=(EditText) findViewById(R.id.editText1);
        final EditText e_password=(EditText) findViewById(R.id.editText3);
        Button login=(Button) findViewById(R.id.button1);
        TextView signup=(TextView) findViewById(R.id.tvCreate);
        
        login.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
			id = e_id.getText().toString();
			password = e_password.getText().toString();
			if(id.equals("") || password.equals(""))
				Toast.makeText(getBaseContext(), "All Fields are Mandatory",
						Toast.LENGTH_SHORT).show();
			else
				validate();
		
		}
	});
        
        signup.setOnClickListener(new View.OnClickListener() {
			
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    				
    		Intent register=new Intent(loginActivity.this,signup.class);
    		startActivity(register);
    		}
    	});
   
    
    
    
        // Can we present the share dialog for regular links?
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        // Can we present the share dialog for photos?
        canPresentShareDialogWithPhotos = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.PHOTOS);
 
    
    
    
    
    
    
    
    
    
    }
 
    public void validate()
    {
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
   	nameValuePairs.add(new BasicNameValuePair("id",id));
   	nameValuePairs.add(new BasicNameValuePair("password",password));
         	try
    	   {
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//login.php");
	        
	       
     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	HttpResponse  response  = httpclient.execute(httppost); 
	     HttpEntity entity = response.getEntity();
         is = entity.getContent();
         Log.d("mohit",entity.toString());
         Log.d("mohit",is.toString());
         Log.e("pass 1", "connection success ");
	}
        catch(Exception e)
	{
        	Log.e("Fail 1", e.toString());
	    	Toast.makeText(getApplicationContext(), "Invalid IP Address",
			Toast.LENGTH_LONG).show();
	}     
      
       try
        {
            BufferedReader reader = new BufferedReader
			(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
	    {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
	    Log.e("pass 2", "connection success ="+result);
	}
        catch(Exception e)
	{
            Log.e("Fail 2", e.toString());
	}     
    
	try
	{
            JSONObject json_data = new JSONObject(result);
            code=(json_data.getInt("code"));
			Log.d("mohit","code="+code);
            if(code==1)
            { settings=this.getSharedPreferences("preference",0);
            editor=settings.edit(); 
            
            	   editor.putString("id",id);
                   editor.putString("name",id);
                   editor.putString("password", password);
                  editor.commit();
                
                  Intent i=new Intent(loginActivity.this,MainActivity.class);
                  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(i);
                  finish();     }
            else
            {
		 Toast.makeText(getBaseContext(), "Sorry, Try Again",
			Toast.LENGTH_LONG).show();
            }
	}
	catch(Exception e)
	{
            Log.e("Fail 3", e.toString());
	}
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);

        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }
    public static Intent getOpenFacebookIntent(Context context) {

		   try {
		    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/136220994671"));
		   } catch (Exception e) {
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/136220994671"));
		   }
	}

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        updateUI();
    }

    private void updateUI() {
        Session session = Session.getActiveSession();
        boolean enableButtons = (session != null && session.isOpened());
  
        if (enableButtons && user != null) {
        	SharedPreferences setting=this.getSharedPreferences("preference",0);
        	SharedPreferences.Editor edit=setting.edit(); 
        	edit.putString("id",user.getFirstName()+" "+user.getLastName());
            edit.putString("name",id);
            edit.putString("graph",user.getId());
            edit.commit();
            SharedPreferences settings=getSharedPreferences(PREFS_NAME,0);
            SharedPreferences.Editor editor=settings.edit();
            if(settings.getString("FBNAME",null)==null)
            {
            editor.putString("FBNAME",user.getFirstName()+" "+user.getLastName());
            editor.putBoolean("ISNAME",true);
            editor.commit();
            }
            Intent i=new Intent(loginActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish(); 
      //      profilePictureView.setProfileId(user.getId());
      //      greeting.setText(getString(R.string.hello_user, user.getFirstName()));
      } else {
      //      profilePictureView.setProfileId(null);
        //  Toast.makeText(getApplicationContext(),"Login UnSuccessful : Try Again",     Toast.LENGTH_SHORT).show();
          
      }
    }
    private void handlePendingAction() {
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
      
    }

    public interface GraphObjectWithId extends GraphObject {
        String getId();
    }

 
 

}