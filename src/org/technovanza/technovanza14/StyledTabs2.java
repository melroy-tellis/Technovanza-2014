package org.technovanza.technovanza14;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.viewpagerindicator.TabPageIndicator;

public class StyledTabs2 extends SherlockFragmentActivity {
	
private static String[] CONTENT;
int width;
    int posi;
    int j;
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_tabs2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
    	Bundle b = i.getExtras();
    	 posi = b.getInt("pos");
    	 j=b.getInt("position");
    	 Log.d("POSITION", ""+posi);
    	
    	int k=0;
    	switch(posi){
    	case 0:k=4;
    		break;
    	case 1:k=2;
    		break;
    	case 2:k=2;
    		break;
    	case 3:k=6;
    		break;
    	case 4:k=8;
    		break;
    	case 5:k=7;
    		break;
    	case 6:k=6;
    		break;
    	case 7:k=8;
    		break;
    	case 8:k=4;
    		break;
    	}
    	CONTENT = new String[k];
    	for(int m=0;m<k;m++){
    		CONTENT[m]="";
    	}
    	
        FragmentPagerAdapter adapter = new EventsAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);
        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager,j);

		  Display newDisplay = getWindowManager().getDefaultDisplay(); 
		   width = newDisplay.getWidth();
		   
		  
        
    }
    /*
    @SuppressLint("NewApi") @Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// TODO Auto-generated method stub
       	switch(item.getItemId()){
       //	case android.R.id.home:
       	
       	//	return true;
       	}
   		return super.onOptionsItemSelected(item);
   	}
    */
    @Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		// TODO Auto-generated method stub
       	switch(item.getItemId()){
       	case android.R.id.home:
       		NavUtils.navigateUpFromSameTask(this);
       		return true;
       	case R.id.share:
       		Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
		//	intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend Restaurant "+info.get(4)+" . Must visit :)");
			startActivity(Intent.createChooser(intent, "Share with"));	
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
            return TabFragment2.newInstance(position,width,posi);
        }

        @SuppressLint("DefaultLocale")
		@Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}

