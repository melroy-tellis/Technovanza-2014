package org.technovanza.technovanza14;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.technovanza.technovanza14.TextCaptcha.TextOptions;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class signup extends Activity {

	String name;
	String id;
	String college;
	String bdate;
	String password;
	String confirmpassword;
	String captcha;
	ImageView im;
	Button btn;
	TextView ans;
	
	
	InputStream is=null;
	String result=null;
	String line=null;
	int code;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       final EditText e_id=(EditText) findViewById(R.id.editText4);
        final EditText e_name=(EditText) findViewById(R.id.editText1);
        final EditText e_college=(EditText) findViewById(R.id.editText2);
        final EditText e_bdate=(EditText) findViewById(R.id.editText3);
        final EditText e_password=(EditText) findViewById(R.id.editText5);
        final EditText e_confirmPassword=(EditText) findViewById(R.id.editText6);
        final EditText e_captcha=(EditText) findViewById(R.id.editText7);
       im = (ImageView)findViewById(R.id.imageView1);
        btn = (Button)findViewById(R.id.button1);
        ans = (TextView)findViewById(R.id.textView1);
    	Display display = getWindowManager().getDefaultDisplay(); 
		@SuppressWarnings("deprecation")
		final
		int screenWidth = display.getWidth();
		final int screenHeight = display.getHeight();
        
        Captcha c = new TextCaptcha(screenWidth,screenHeight/8,5,TextOptions.NUMBERS_AND_LETTERS);
        im.setImageBitmap(c.image);
		im.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
		ans.setText(c.answer);
        btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Captcha c = new MathCaptcha(300, 100, MathOptions.PLUS_MINUS_MULTIPLY); 
				Captcha c = new TextCaptcha(screenWidth,screenHeight/8,5, TextOptions.NUMBERS_AND_LETTERS);
				im.setImageBitmap(c.image);
				im.setLayoutParams(new LinearLayout.LayoutParams(c.width *2, c.height *2));
				ans.setText(c.answer);
			}
		});
        
       
        Button register=(Button) findViewById(R.id.button2);
        
        register.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				
			id = e_id.getText().toString();
			name = e_name.getText().toString();
			college = e_college.getText().toString();
			bdate = e_bdate.getText().toString();
			password = e_password.getText().toString();
			captcha=e_captcha.getText().toString();
			confirmpassword = e_confirmPassword.getText().toString();
			Log.d("mohit",""+id+""+name+""+college+""+bdate+""+password);
			if(id.equals("") || name.equals("") || college.equals("") || bdate.equals("") || password.equals("") || confirmpassword.equals(""))
				Toast.makeText(getBaseContext(), "All Fields are Mandatory",
						Toast.LENGTH_SHORT).show();
			else          
			insert();
		}
	});
    }
 
    public void insert()
    {
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 
    if(password.equals(confirmpassword) && captcha.equals(ans.getText().toString())){	
    	
   	nameValuePairs.add(new BasicNameValuePair("id",id));
   	nameValuePairs.add(new BasicNameValuePair("name",name));

   	nameValuePairs.add(new BasicNameValuePair("password",password));
   	nameValuePairs.add(new BasicNameValuePair("college",college));

   	nameValuePairs.add(new BasicNameValuePair("bdate",bdate));
   
   	
   	
   	
   	
   	try
    	   {
		    HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://androidproject.url.ph/virtualLibrary//insert.php");
	       
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
            {
		Toast.makeText(getBaseContext(), "Inserted Successfully",
			Toast.LENGTH_SHORT).show();
            
            
            Intent i=new Intent(signup.this,loginActivity.class);
            startActivity(i);
            
            }
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
    else
    {
    	Toast.makeText(getApplicationContext(), "Password did not match or captcha did not match",
			Toast.LENGTH_LONG).show();
	
    	
    }
    }
    

}