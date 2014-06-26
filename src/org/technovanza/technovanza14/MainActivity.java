package org.technovanza.technovanza14;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;


public class MainActivity extends Activity   {
		
	static final int typeEventsFragment=0,typeMediaFragment=1,typeNewsFragment=2,typeAboutFragment=3 ;
	
	TechnoTileFragment mEventsFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeEventsFragment);
	TechnoTileFragment mMediaFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeMediaFragment);
	TechnoTileFragment mNewsFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeNewsFragment);
	TechnoTileFragment mAboutFragment=TechnoTileFragment.newTechnoTileFragmentInstance(TechnoTileFragment.typeAboutFragment);
	
	int mEventsFragContainer,mMediaFragContainer,mNewsFragContainer,mAboutFragContainer ;  
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		
		mEventsFragContainer=R.id.frag1;
		mMediaFragContainer=R.id.frag2;
		mNewsFragContainer=R.id.frag3;
		mAboutFragContainer=R.id.frag4 ;
		
		getFragmentManager().beginTransaction().add(mEventsFragContainer, mEventsFragment).add(mMediaFragContainer, mMediaFragment).add(mNewsFragContainer, mNewsFragment).add(mAboutFragContainer, mAboutFragment).commit();
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
	
	private TechnoTileFragment getMemberFragmentOfType(int type)
	{
		switch(type)
		{
			case TechnoTileFragment.typeEventsFragment:
				return mEventsFragment;
			case TechnoTileFragment.typeMediaFragment:
				return mMediaFragment;
			case TechnoTileFragment.typeNewsFragment:
				return mNewsFragment;	
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
			case TechnoTileFragment.typeMediaFragment:
				return mMediaFragContainer;
			case TechnoTileFragment.typeNewsFragment:
				return mNewsFragContainer;	
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
			case TechnoTileFragment.typeMediaFragment:
				mMediaFragContainer = containerID;
				break;
			case TechnoTileFragment.typeNewsFragment:
				mNewsFragContainer  = containerID;	
				break;
			default:	
				mAboutFragContainer  = containerID;
	
		}
		
	}
	

}
