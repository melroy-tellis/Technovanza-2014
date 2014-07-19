package org.technovanza.technovanza14;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class MainActivity extends Activity   {
		
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
