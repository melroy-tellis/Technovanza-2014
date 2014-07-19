package org.technovanza.technovanza14;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
//import com.gotechno.technovanza13.StyledTabs.EventsAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class StyledTabs_house extends SherlockFragmentActivity {
	
	    private static final String[] CONTENT = new String[] {"Overview","Houses","Coins&Prizes","Updates"};

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.simple_tabs3);

	        FragmentPagerAdapter adapter = new EventsAdapter(getSupportFragmentManager());
	       
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        ViewPager pager = (ViewPager)findViewById(R.id.pager3);
	        pager.setAdapter(adapter);

	        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator3);
	        indicator.setViewPager(pager);
	    }

	   /* class GoogleMusicAdapter extends FragmentPagerAdapter {
	        public GoogleMusicAdapter(FragmentManager fm) {
	            super(fm);
	        }*/
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

