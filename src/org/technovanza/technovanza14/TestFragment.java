package org.technovanza.technovanza14;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public final class TestFragment extends Fragment{
    private static final String KEY_CONTENT = "TestFragment:Content";
    public static final String PREFS_NAME="HouseCupFile";
    static String color="normal";
   

    public static TestFragment newInstance(String content) {
        TestFragment fragment = new TestFragment();
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            builder.append(content).append(" ");
        }
        builder.deleteCharAt(builder.length() - 1);
        fragment.mContent = builder.toString();
       
        return fragment;
    }
    String r="RED PHOENIX\n1.Electronics and Tele-Communication Engineering\n2.Information Technology\n3.Mechanical Engineering\n4.Textile Engineering";
    String g="GREEN GRIFFIN\n1.Electronics Engineering\n2.MCA\n3.Civil Engineering\n4.Chemsa";
    String b="BLUE DRAGON\n1.Electrical Engineering\n2.Computers Engineering\n3.Production Engineering\n4.Master of Technology";
    String array[]={r,g,b};
    
    private TextView txtStatus;
    private ImageView imageView;
    
    
    int i=0;
    int imgid[]={R.drawable.red,R.drawable.green,R.drawable.blue};
    RefreshHandler refreshHandler=new RefreshHandler();
    
     	@SuppressLint("HandlerLeak")
		class RefreshHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            TestFragment.this.updateUI();
        }
        public void sleep(long delayMillis){
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    public void updateUI(){
    
    	int currentInt=1;
        if(currentInt==1){
            refreshHandler.sleep(3000);
            //txtStatus.setText(String.valueOf(currentInt));
            if(i<imgid.length){
                imageView.setImageResource(imgid[i]);
                txtStatus.setText(array[i]);
                i++;
            }
            if(i==3) i=0;
        }
        
    	
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         final SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
         final SharedPreferences.Editor editor = settings.edit();
         final String android_id = Secure.getString(getActivity().getContentResolver(),
                 Secure.ANDROID_ID);
 		Parse.initialize(getActivity(), "jWNXwn3j7fmaub8bFMWYy3sRWJgXCMaqCljKOUyn", "AmQgVA2i3Gc45vHfYwqfPcJJzAyvwihvmgYcRx86");
 		String color1=settings.getString("HOUSE",null);
        if(color1!=null)
    	{
    		color=settings.getString("HOUSE",null);
    	}
    	else
    	{
    		color="normal";
    	}
        

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }
    public void setBack(View myview,String color)
    {
    	if(color.equals("normal"))
    	{
    		myview.setBackgroundResource(R.drawable.common_back);
    	}
    	else if(color.equals("Green Griffin"))
    	{
    		myview.setBackgroundResource(R.drawable.green_house);
    	}
    	else if(color.equals("Red Phoenix"))
    	{
    		myview.setBackgroundResource(R.drawable.red_house1);
    	}
    	else if(color.equals("Blue Dragon"))
    	{
    		myview.setBackgroundResource(R.drawable.blue_house);
    	}
    	else
    	{
    		myview.setBackgroundResource(R.drawable.common_back);
    	}
    }
    
    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	

    	if(mContent.charAt(0)=='O'){
			myView = inflater.inflate(R.layout.overview, container,false);
			RelativeLayout content = (RelativeLayout)myView.findViewById(R.id.rr); 
			setBack(myView,color);
			//myView.setBackgroundResource(R.drawable.green_house);
			Button ib1=(Button)myView.findViewById(R.id.coins);
			
			
			ib1.setOnClickListener(ib1Listener);
			
			
		}
		else if(mContent.charAt(0)=='H'){
			myView = inflater.inflate(R.layout.houses, container,false);
			txtStatus=(TextView)myView.findViewById(R.id.new3);
			imageView=(ImageView)myView.findViewById(R.id.new2);
			setBack(myView,color);
			updateUI();
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					updateUI();
				}
			});
		}
		else if(mContent.charAt(0)=='C'){
			myView = inflater.inflate(R.layout.basket_coins, container,false);
    		setBack(myView,color);
		}
		else if(mContent.charAt(0)=='U')
			{myView = inflater.inflate(R.layout.updatetab_house, container,false);
			setBack(myView,color);
			Button ib=(Button)myView.findViewById(R.id.buttonhouse);
			ib.setOnClickListener(ibListener);
			}
		
    	
    	return myView;
    }
    private OnClickListener ibListener = new OnClickListener() {
        
		public void onClick(View v) {
			//Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
			if(isNetworkAvailable())
			{
				
			Log.d("internet","internet");
			Intent i=new Intent(getActivity(),Housecup_updates.class);
			startActivity(i);
			}
			else
			{
				Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
			}
        }};
        private OnClickListener ib1Listener = new OnClickListener() {
            
    		public void onClick(View v) {
    			//Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
    			if(isNetworkAvailable())
    			{
    				
    			Log.d("internet","internet");
    			Intent i=new Intent(getActivity(),MainActivity_coins.class);
    			startActivity(i);
    			}
    			else
    			{
    				Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
    			}
            }};
           
            
    	private boolean isNetworkAvailable()
    	{
    		ConnectivityManager connectivityManager 
    	         = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
    	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
    	        return true;
    	    }
    	    return false;
    	      
    	}
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
}
