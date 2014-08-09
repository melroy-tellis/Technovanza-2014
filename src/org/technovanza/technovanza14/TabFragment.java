package org.technovanza.technovanza14;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public final class TabFragment extends Fragment {
	 public static final String ARG_PLANET_NUMBER = "planet_number";

	String Contents[][] = { { "Real Steel: Robowars", "VJ Robotics Challenge(VRC)", "Wall-E","Monster Arena 3.0"},
			{ "Fast & Furious","Climb-e-Rope",},
			{ "Bridge the Gap"," Drive Thru"},
			{ "TPP","HSW", "X-CON","RCMO","Junior XCON","Contraption "},
			{ "Unicus", "Hire", "Wallstreet", "Consultant", "Freakonomics", "Biz-Quiz", "S.C.A.M", "Webpreneur"},
			{ "C-way", "Java Guru", "Technohunt", "Ultimate Coder", "Mission SQL", "AI Wars","Code Swap" },
			{ "Algocrack", "Code in X", "Cryptext", "Myst", "VSM", "Forex"},
			{ "Tricky Tronics","LAN gaming","Text-o-Mania", "Click N' Capture", "Google Junkie", "Techno Quiz","Tech-Charades",""},
			{ "Laser CS", "Aqua Soccer", "Techno Drift","Master Of Puppets"} };
							
	static int images[] = {R.drawable.autobots,R.drawable.imech, R.drawable.ibuild, R.drawable.eureka, R.drawable.manageria, R.drawable.icode,R.drawable.onlineevnets, R.drawable.impulse, R.drawable.fun_events};
	int pos=0;
	public TabFragment()
	{}
	/*public TabFragment newInstance(int pos) {
		TabFragment fragment = new TabFragment();
		Log.d("position", "the position is "+pos);
		Bundle args = new Bundle();
		args.putInt("position", pos);
		fragment.setArguments(args);
		return fragment;
	}
*/


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  pos = getArguments().getInt(ARG_PLANET_NUMBER);
		 //  Bundle args = new Bundle();
			//args.putInt("position", pos);
			
		LinearLayout ll = new LinearLayout(inflater.getContext());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
				R.layout.simple_list_modified, Contents[pos]){
			@Override
	        public View getView(int position, View convertView,
	                ViewGroup parent) {
	            View view =super.getView(position, convertView, parent);

	            TextView textView=(TextView) view.findViewById(android.R.id.text1);
	            textView.setGravity(Gravity.CENTER);
	            textView.setTextAppearance(getActivity(), R.style.boldText);
	          
	            textView.setTextColor(Color.WHITE);
	            return view;
	        }	
} ;
		ll.setBackgroundResource(images[pos]);
		switch (pos) {
		case 0:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		case 1:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		case 2:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		case 3:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		case 4:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		case 5:
			adapter = new ArrayAdapter<String>(inflater.getContext(),
					R.layout.simple_list_modified, Contents[pos]);
			ll.setBackgroundResource(images[pos]);
			break;
		default:	adapter = new ArrayAdapter<String>(inflater.getContext(),
				R.layout.simple_list_modified, Contents[pos]);
				ll.setBackgroundResource(images[pos]);
			break;
			
			
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT); 
		ll.setLayoutParams(params);
		
		ListView lv = new ListView(getActivity());
		lv.setId(android.R.id.list);
		lv.setLayoutParams(params);
		//lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setAdapter(adapter);
		 lv.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> parent, View view,
			     int position, long id) {
			    // Send the URL to the host activity
				   Log.d("mohit",Contents[pos][position]);
					
					Intent i=new Intent(getActivity(), StyledTabs2.class);
					Bundle b=new Bundle();
					b.putInt("pos", pos);
					b.putInt("position", position);
					i.putExtras(b);
					startActivity(i);
 
			   }
			  });
		
		
		
		
		ll.addView(lv);
		
		return ll;
	}
	public void onListItemClick (ListView l, View v, int position, long id)
	  {
			//Toast.makeText(getActivity(),Contents[pos][position]+" selected ", Toast.LENGTH_SHORT).show();
				  }

}
