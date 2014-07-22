package org.technovanza.technovanza14;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;

public class TechnoTileFragment extends Fragment implements OnClickListener,OnLongClickListener,OnDragListener{
	static final int typeEventsFragment=0,typeGalleryFragment=1,typeHousecupFragment=2,typeTwitterFragment=3,typeFacebookFragment=4,typeAboutFragment=5 ;
	private int type;	
	
	public static TechnoTileFragment newTechnoTileFragmentInstance(int type)
	{
		TechnoTileFragment TechnoTileFragmentInstance=new TechnoTileFragment() ;
		TechnoTileFragmentInstance.type=type ;
		return TechnoTileFragmentInstance ;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		LinearLayout eventsLinearLayout=new LinearLayout(this.getActivity().getApplicationContext());
		switch(type)
		{
			case typeEventsFragment:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.events_fragment_layout, container, false);
				break;
			case typeGalleryFragment:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.media_fragment_layout, container, false);
				break;
			case typeTwitterFragment:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.news_fragment_layout, container, false);
				break;
			case typeFacebookFragment:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.facebook_fragment_layout, container, false);
				break;
			case typeHousecupFragment:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.housecup_fragment_layout, container, false);
				break;
			default:
				eventsLinearLayout=(LinearLayout)inflater.inflate(R.layout.about_fragment_layout, container, false);
				break;
				
		}
		eventsLinearLayout.setOnClickListener(this);
		eventsLinearLayout.setOnLongClickListener(this);
		eventsLinearLayout.setOnDragListener(this);
		return eventsLinearLayout;

	}
	@Override
	public void onClick(View view) {
		switch(view.getId())
		{
			case R.id.events_frag:
				this.getActivity().startActivity(new Intent(this.getActivity(),StyledTabs.class));
				break;
			case R.id.news_frag:
				this.getActivity().startActivity(new Intent(this.getActivity(),NewsActivity.class));
				break;	
			case R.id.media_frag:
				this.getActivity().startActivity(new Intent(this.getActivity(),GalleryView.class));
				break;
			case R.id.housecup_frag:
				this.getActivity().startActivity(new Intent(this.getActivity(),StyledTabs_house.class));
				break;
			case R.id.facebook_frag:
				this.getActivity().startActivity(new Intent(this.getActivity(),HelloFacebookSampleActivity.class));
				break;
		}
		
		
		
		
	}
	@Override
	public boolean onDrag(View view, DragEvent dragevent) {
		switch(dragevent.getAction())
		{
						
			case DragEvent.ACTION_DROP:
				int draggedFragmentType=dragevent.getClipData().getItemAt(0).getIntent().getIntExtra("DragFragType",typeAboutFragment);
				((MainActivity)this.getActivity()).replaceFragment(view, draggedFragmentType, type);
				return true;
			
			default:
				return true;
				
		}
		
	}
		
	@Override
	public boolean onLongClick(View view) {
		Intent intent = new Intent();
		intent.putExtra("DragFragType", type);
		Item draggedFragmentType=new Item(intent);
		ClipData draggedFragmentData=new ClipData(new ClipDescription("Dragged Fragment Type", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}), draggedFragmentType);
		DragShadowBuilder mDragShadowBuilder=new DragShadowBuilder(view);
		view.startDrag(draggedFragmentData, mDragShadowBuilder, view, 0);
		return true;
	}

}
