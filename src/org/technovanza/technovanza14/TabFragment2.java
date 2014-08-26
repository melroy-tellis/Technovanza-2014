	package org.technovanza.technovanza14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
	public class TabFragment2 extends Fragment{
		ImageButton ib;
		TextView etv;
		ImageButton share;
		Button registerButton;
		static int width;
		String event;
		static int x,y;
		ProgressDialog pDialog;
		JSONParser jsonParser=new JSONParser();
		private static String url_register_event = "http://technovanza.org/app_register_event.php";
		private static final String TAG_SUCCESS = "success";
		
 public static TabFragment2 newInstance(int pos,int w,int posi) {
			TabFragment2 fragment = new TabFragment2();
			Log.d("position", "the position is "+pos);
			Bundle args = new Bundle();
			args.putInt("position", pos);
			width=w;
			x=posi;
			
			fragment.setArguments(args);
			Log.d("EVENTTTTTTTTTTTTTTTT","Inside the newInstance");
			return fragment;
		}
		
		String s;
		ArrayList<String> groupItem;
		ArrayList<Object> childItem;
		Map<String,Integer> eventIdMap = new HashMap<String,Integer>();
		//String variable for current event : 
		String currentEvent;
		String Contents[][] = { { "Real Steel: Robowars", "VJ Robotics Challenge(VRC)", "Wall-E","Monster Arena 3.0"},
				{ "Fast & Furious","Climb-e-Rope",},
				{ "Bridge the Gap"," Drive Thru"},
				{ "TPP","HSW", "X-CON","RCMO","Junior XCON","Contraption "},
				{ "Unicus", "Hire", "Wallstreet", "Consultant", "Freakonomics", "Biz-Quiz", "S.C.A.M", "Webpreneur"},
				{ "C-way", "Java Guru", "Technohunt", "Ultimate Coder", "Mission SQL", "AI Wars","Code Swap" },
				{ "Algocrack", "Code in X", "Cryptext", "Myst", "VSM", "Forex"},
				{ "Tricky Tronics","LAN gaming","Text-o-Mania", "Click N' Capture", "Google Junkie", "Techno Quiz","Tech-Charades",""},
				{ "Laser CS", "Aqua Soccer", "Techno Drift","Master Of Puppets"} };
		
		String TeamEvents[] = {"Real Steel: Robowars","VRC(VJ Robotics Challenge)", "Wall-E","Monster Arena 3.0","Fast & Furious","Climb-e-Rope", "Bridge the Gap"," Drive Thru","Technohunt","Code Swap","TPP","HSW", "X-CON","Junior XCON","Consultant","Webpreneur","Hire"};
		String edata[];	
		boolean flag=false; 
		View myView;
@Override
public void onCreate(Bundle s)
		{
	super.onCreate(s);
	setHasOptionsMenu(true);
		}
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
						Bundle savedInstanceState) {
			
			eventIdMap.put("aiwars", 1);
			eventIdMap.put("algocrack", 2);
			eventIdMap.put("bizquiz", 3);
			eventIdMap.put("bridge-the-gap", 4);
			eventIdMap.put("climb-e-rope", 5);
			eventIdMap.put("code-in-x", 6);
			eventIdMap.put("codeswap", 7);
			eventIdMap.put("consultant", 8);
			eventIdMap.put("cryptext", 9);
			eventIdMap.put("c-way", 10);
			eventIdMap.put("drive-thru", 11);
			eventIdMap.put("fast-furious", 12);
			eventIdMap.put("forex", 13);
			eventIdMap.put("freakonomics", 14);
			eventIdMap.put("hire", 15);
			eventIdMap.put("hsw", 16);
			eventIdMap.put("java-guru", 17);
			eventIdMap.put("junior-xcon", 18);
			eventIdMap.put("mission-sql", 19);
			eventIdMap.put("monster-arena", 20);
			eventIdMap.put("myst", 21);
			eventIdMap.put("rcmo", 22);
			eventIdMap.put("robotics", 23);
			eventIdMap.put("robowars", 24);
			eventIdMap.put("scam", 25);
			eventIdMap.put("technohunt", 26);
			eventIdMap.put("tpp", 27);
			eventIdMap.put("ultimate-coder", 28);
			eventIdMap.put("unicus", 29);
			eventIdMap.put("vsm", 30);
					// TODO Auto-generated method stub
					groupItem = new ArrayList<String>();
					childItem= new ArrayList<Object>();
				if(x<7){
					myView = inflater.inflate(R.layout.activity_event, container,false);
					registerButton=(Button)myView.findViewById(R.id.btn1);
					registerButton.setOnClickListener(eventButtonListener);
					if(!isAlreadyRegistered()) {
						registerButton.setEnabled(false);
					}
				}
				else {
					myView = inflater.inflate(R.layout.activity_event, container,false);
				}
				share=(ImageButton)myView.findViewById(R.id.sh);
				share.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
					intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend this event "+event+" . Must try :)");
						startActivity(Intent.createChooser(intent, "Share with"));	
				
					}
				});
			
				
				ib=(ImageButton)myView.findViewById(R.id.iv1);
			ib.setOnClickListener(imageButtonListener);
			//ib.setBackground(states);
			ExpandableListView expandbleLis=(ExpandableListView)myView.findViewById(R.id.elv);
			  expandbleLis.setDividerHeight(2);
			  expandbleLis.setClickable(true);
			  expandbleLis.setIndicatorBounds(width-75, width);
			  expandbleLis.setCacheColorHint(Color.TRANSPARENT);
			  
			  setGroupData();
			  //setChildGroupData(edata);
			//RelativeLayout r1=(RelativeLayout)myView.findViewById(R.id.rr);
			  //r1.setBackgroundResource(R.drawable.back600);
	
			  etv=(TextView)myView.findViewById(R.id.tv1);
	 y = getArguments().getInt("position");
	
	etv.setText(Contents[x][y]);
	event=Contents[x][y];
	/*if(Arrays.asList(TeamEvents).contains(Contents[x][y])) {
		teamEventToast();
	}*/
	//ib.setBackgroundResource(R.drawable.icengines);
	Log.d("mesg",""+y);
	switch(x)
	{
		case 0:{		//Autobots
			getRegisterButton();
			switch(y)
			{
			case 0:	{	//robowars
				edata=getResources().getStringArray(R.array.robowars);
				flag=true;
				ib.setBackgroundResource(R.drawable.robowars1);
				currentEvent="robowars";
				//teamEventToast();
				break;
				}
		case 1:	{	//VCR
			edata=getResources().getStringArray(R.array.VRC);
			flag=true;
			ib.setBackgroundResource(R.drawable.vrc);
			currentEvent="robotics";
			//teamEventToast();
			break;
			}
		case 2:	{	//
			edata=getResources().getStringArray(R.array.walle);
			flag=true;
			ib.setBackgroundResource(R.drawable.walle);
			currentEvent="wall-e";
			//teamEventToast();
			break;
			}
		case 3:	{	//
			edata=getResources().getStringArray(R.array.monsterarena);
			flag=true;
			ib.setBackgroundResource(R.drawable.monsterarena);
			//teamEventToast();
			currentEvent="monster-arena";
			break;
			}
		}
		  break;

		
		}
		
		case 1:			//IMECH
		{	getRegisterButton();
			switch(y)
			{
			case 0:	{	//
				edata=getResources().getStringArray(R.array.fastandfurious);
				flag=true;
				ib.setBackgroundResource(R.drawable.fastnfurious);
				//teamEventToast();
				currentEvent="fast-furious";
				break;
				}

			case 1: {
				edata=getResources().getStringArray(R.array.ClimbeRope);
				flag=true;
				ib.setBackgroundResource(R.drawable.rope_climbing);
				//teamEventToast();
				currentEvent="climb-e-rope";
				break;
				}
			}
		break;
		}
		case 2:			//IBUILD
		{	getRegisterButton();
			switch(y)
			{
			case 0: {
				edata=getResources().getStringArray(R.array.BridgetheGap);
				flag=true;
				ib.setBackgroundResource(R.drawable.bridgethegap2);
				currentEvent="bridge-the-gap";
				//teamEventToast();
				break;
				}
			case 1:{      //drive thru
				edata=getResources().getStringArray(R.array.drive_thru);
				flag=true;
				ib.setBackgroundResource(R.drawable.drive_thru);
				currentEvent="drive-thru";
				//teamEventToast();
				break;
				}
			}
		break;
		}
		
		
		
				case 3:{		//Eureka
			
			getRegisterButton();
			switch(y)
			{	
			case 0:	{	
					edata=getResources().getStringArray(R.array.tpp);
					flag=true;
					ib.setBackgroundResource(R.drawable.tpp);
					//teamEventToast();
					currentEvent="tpp";
					break;
					}
			
			case 1:	{	
				edata=getResources().getStringArray(R.array.hsw);
				flag=true;
				ib.setBackgroundResource(R.drawable.hsw);
				//teamEventToast();
				currentEvent="hsw";
				break;
				}
			case 2:	{	
				edata=getResources().getStringArray(R.array.xcon);
				flag=true;
				ib.setBackgroundResource(R.drawable.xcon);
				currentEvent="x-con";
				//teamEventToast();
				
				break;
				}
			
			case 3:	{	
				edata=getResources().getStringArray(R.array.rcmo);
				flag=true;
				ib.setBackgroundResource(R.drawable.rcmo);
				currentEvent="rcmo";
				break;
				}
			case 4:	{	
				edata=getResources().getStringArray(R.array.junior_xcon);
				flag=true;
				ib.setBackgroundResource(R.drawable.junior_xcon1);
				currentEvent="junior-xcon";
				//teamEventToast();
				break;
				}
			case 5:	{	
				edata=getResources().getStringArray(R.array.contraption);
				flag=true;
				ib.setBackgroundResource(R.drawable.contraption);
				currentEvent="NONE";
				break;
				}

			}break;
				}
		
		case 4:{		//MANAGERIA
			getRegisterButton();
			switch(y)
			{
			case 0:	{	
					edata=getResources().getStringArray(R.array.UNICUS);
					flag=true;
					ib.setBackgroundResource(R.drawable.unicus);
					currentEvent="unicus";
					break;
					}
			
			case 1:	{	
				edata=getResources().getStringArray(R.array.HIRE);
				flag=true;
				ib.setBackgroundResource(R.drawable.hire);
				currentEvent="hire";
				//teamEventToast();
				break;
				}
			case 2:	{	
				edata=getResources().getStringArray(R.array.Wallstreet);
				flag=true;
				ib.setBackgroundResource(R.drawable.wallstreet);
				currentEvent="wallstreet";
				break;
				}
			case 3:	{	
				edata=getResources().getStringArray(R.array.Consultant);
				flag=true;
				ib.setBackgroundResource(R.drawable.consultant);
				currentEvent="consultant";
				//teamEventToast();
				break;
				}
			case 4:	{	
				edata=getResources().getStringArray(R.array.Freakonomics);
				flag=true;
				ib.setBackgroundResource(R.drawable.freak);
				currentEvent="freakonomics";
				break;
				}
			case 5:	{	
				edata=getResources().getStringArray(R.array.Bizquiz);
				flag=true;
				ib.setBackgroundResource(R.drawable.bizquiz);
				currentEvent="bizquiz";
				break;
				}
			case 6:	{	
				edata=getResources().getStringArray(R.array.SCAM);
				flag=true;
				ib.setBackgroundResource(R.drawable.scam);
				currentEvent="scam";
				break;
				}
			case 7:	{	
				edata=getResources().getStringArray(R.array.webpreneur);
				flag=true;
				ib.setBackgroundResource(R.drawable.webpreneur);
				currentEvent="webpreneur";
				break;
				}
		}break;

		}
		case 5:{		//i code
			getRegisterButton();
			switch(y)
			{
			case 0:	{	//c way
					edata=getResources().getStringArray(R.array.c_way);
					flag=true;
					ib.setBackgroundResource(R.drawable.cway1);
					currentEvent="c-way";
					break;
					}
			case 1:	{	//java guru
				edata=getResources().getStringArray(R.array.javaguru);
				flag=true;
				ib.setBackgroundResource(R.drawable.java1);
				currentEvent="java-guru";
				break;
				}
			case 2:	{	//technohunt
				edata=getResources().getStringArray(R.array.technohunt);
				flag=true;
				ib.setBackgroundResource(R.drawable.technohunt1);
				//teamEventToast();
				currentEvent="technohunt";
				break;
				}
			case 3:	{	//ultimate code
				edata=getResources().getStringArray(R.array.ultimate_coder);
				flag=true;
				ib.setBackgroundResource(R.drawable.ultimate_coder1);
				currentEvent="ultimate-coder";
				break;
				}
			case 4:	{	//mission sql
				edata=getResources().getStringArray(R.array.mission_sql);
				flag=true;
				ib.setBackgroundResource(R.drawable.missionsq1);
				currentEvent="mission-sql";
				break;
				}
			case 5:	{	//ai wars
				edata=getResources().getStringArray(R.array.ai_wars);
				flag=true;
				ib.setBackgroundResource(R.drawable.aiwars1);
				currentEvent="aiwars";
				break;
				}
			case 6:	{	//codeswap
				edata=getResources().getStringArray(R.array.codeswap);
				flag=true;
				ib.setBackgroundResource(R.drawable.codeswap1);
				//teamEventToast();
				currentEvent="codeswap";
				break;
				}
				}
			break;
				}
		
		case 6:{		//Online
			getRegisterButton();
			switch(y)
			{
			case 0:	{	//algocrack
					edata=getResources().getStringArray(R.array.algocrack);
					flag=true;
					ib.setBackgroundResource(R.drawable.algocrack_converted1);
					currentEvent="algocrack";
					break;
					}
			case 1:	{	//codeinx
				edata=getResources().getStringArray(R.array.codeinx);
				flag=true;
				ib.setBackgroundResource(R.drawable.codeinx);
				currentEvent="code-in-x";
				break;
				}
			
			case 2:	{	//crypttext
				edata=getResources().getStringArray(R.array.cryptext);
				flag=true;
				ib.setBackgroundResource(R.drawable.cryptext1);
				currentEvent="cryptext";
				break;
				}
			case 3:	{	//myst
				edata=getResources().getStringArray(R.array.myst);
				flag=true;
				ib.setBackgroundResource(R.drawable.myst1);
				currentEvent="myst";
				break;
				}
			case 4:	{	//vsm
				edata=getResources().getStringArray(R.array.vsm);
				flag=true;
				ib.setBackgroundResource(R.drawable.vsm1_white);
				currentEvent="vsm";
				break;
				}
			case 5:	{	//forex
				edata=getResources().getStringArray(R.array.forex);
				flag=true;
				ib.setBackgroundResource(R.drawable.forex1);
				currentEvent="forex";
				break;
					}
			
				}
			break;
				}
		case 7:{	//Impulse
			switch(y){
			case 0:{
				edata=getResources().getStringArray(R.array.TrickyTronics);
				flag=true;
				ib.setBackgroundResource(R.drawable.tricky_tronix);
				currentEvent="NONE";
				break;
			}
			case 1:{
				edata=getResources().getStringArray(R.array.lan_gamimg);
				flag=true;
				ib.setBackgroundResource(R.drawable.langaming);
				currentEvent="NONE";
				break;
			}
			case 2:{
				edata=getResources().getStringArray(R.array.TextoMania);
				flag=true;
				ib.setBackgroundResource(R.drawable.text_o_mania);
				currentEvent="NONE";
				break;
			}
			case 3:{
				edata=getResources().getStringArray(R.array.ClickNCapture);
				flag=true;
				ib.setBackgroundResource(R.drawable.clickncapture);
				currentEvent="NONE";
				break;
			}
			case 4:{
				edata=getResources().getStringArray(R.array.GoogleJunkie);
				flag=true;
				ib.setBackgroundResource(R.drawable.googlejunkie);
				currentEvent="NONE";
				break;
			}
			case 5:{
				edata=getResources().getStringArray(R.array.TechQuiz);
				flag=true;
				ib.setBackgroundResource(R.drawable.tech_quiz);
				currentEvent="NONE";
				break;
			}
			case 6:{
				edata=getResources().getStringArray(R.array.tweet2win);
				flag=true;
				ib.setBackgroundResource(R.drawable.tweettowin);
				currentEvent="NONE";
				break;
			}
			case 7:{
				edata=getResources().getStringArray(R.array.TechCharades);
				flag=true;
				ib.setBackgroundResource(R.drawable.tech_charades);
				currentEvent="NONE";
				break;
			}
			}
			break;
		}
		
		case 8:{		//Fun events
					switch(y)
					{
					case 0:	{	
							edata=getResources().getStringArray(R.array.laser_cs);
							flag=true;
							ib.setBackgroundResource(R.drawable.lasercs);
							currentEvent="NONE";
							break;
							}
					case 1:	{	
						edata=getResources().getStringArray(R.array.Aquasoccer);
						flag=true;
						ib.setBackgroundResource(R.drawable.aquasoccer);
						currentEvent="NONE";
						break;
						}
					case 2:	{	
						edata=getResources().getStringArray(R.array.TechnoDrift);
						flag=true;
						ib.setBackgroundResource(R.drawable.technodrift);
						currentEvent="NONE";
						break;
						}
					case 3:	{	//
						edata=getResources().getStringArray(R.array.masterofpuppets);
						flag=true;
						ib.setBackgroundResource(R.drawable.mop);
						currentEvent="NONE";
						break;
						}
					}break;
				}
		
		default:	{
			setChildGroupData(edata);
			ib.setBackgroundResource(R.drawable.icode);
			break;
		}
		
		
	}
	
	setChildGroupData(edata);


	NewAdapter mNewAdapter = new NewAdapter(groupItem, childItem,x,y);
	mNewAdapter
	  .setInflater(
	    (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
	    getActivity());
	expandbleLis.setAdapter(mNewAdapter);
		
		
	return myView;		
		}	
		public void setGroupData() {
			  groupItem.add("Introduction"); 
			  groupItem.add("Gameplay");
			  groupItem.add("Rules");
			  groupItem.add("Contact Us");
			  groupItem.add("More");
			 }	
		 public void setChildGroupData(String e[]) {
			  
			 ArrayList<String> child = new ArrayList<String>();
			 if(flag)
			 {	
				child.add(e[0]);
				 childItem.add(child);
				 child = new ArrayList<String>();
				 
				 child.add(e[1]);
				 childItem.add(child);
				 child = new ArrayList<String>();
				 
				 child.add(e[2]);
				 childItem.add(child);
				 child = new ArrayList<String>();
				 
				 child.add(e[3]);
				 childItem.add(child);
				 flag=false;
				 
				 child = new ArrayList<String>();
				 //String temp="bot specification,judging criteria,tutorials,and much more \n.....on web link";
				 //after adding links to all events files,replace it with-:child.add(e[4]);
				
				 child.add(e[4]);
				 childItem.add(child);
				 
				 
			 }	
			 
			 		 }
		 
		 private void teamEventToast(){
			 Toast.makeText(getActivity(), "This is a team event.  Please register your team on the website...", Toast.LENGTH_LONG).show();
		 }
		 
		 private boolean isAlreadyRegistered() {
				//check of User data already exists...
		    	String path=getActivity().getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + Data.userDataFileName;
		    	Log.i("MYLOG","File name while retrieving : " + path);
		    	File file=new File(path);
		    	if(file.exists()) {
		    		return true;
		    	}
		    	return false;
		}
		 
		 public void getRegisterButton(){
			 /*rl = (RelativeLayout)getActivity().findViewById(R.id.rr);
			 Button register = new Button(getActivity());
			 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			 params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			 params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			 register.setLayoutParams(params);
			 register.setText("Register");
			 rl.addView(register);*/
		 }
		 @Override
		 	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		 	   inflater.inflate(R.menu.main2, menu);
		 }
		 
		 
		 public boolean onOptionsItemSelected(MenuItem item) {
		 	   // handle item selection
		 	   switch (item.getItemId()) {
		 	 	case R.id.share:
		       		Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "I would like to recommend this event "+event+" . Must try :)");
					startActivity(Intent.createChooser(intent, "Share with"));	
					return true;
		       	}
		return true;
		 	   // return super.onOptionsItemSelected(item);
		 	   }
		 
	
		 private boolean isTeamEvent(String eventName) {
			 for(String eventArrayName:TeamEvents) {
				 if (eventName.equalsIgnoreCase(eventArrayName)) {
					 return true;
				 }
			 }
			 return false;
		 }
		
		 private OnClickListener eventButtonListener = new OnClickListener() {
		        public void onClick(View v) {
		        	//check if current event is a team event
		        	if (isTeamEvent(etv.getText().toString())) {
		        		teamEventToast();
		        	}
		        	Log.i("MYLOG",etv.getText().toString());
		        	AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());     
		    		alt_bld.setMessage("Are you sure you want to register for " + etv.getText().toString());
		    		alt_bld.setCancelable(false);
		    		alt_bld.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {
		    			//super.onBackPressed();
		    			@Override
		    			public void onClick(DialogInterface dialog, int which) {
		    				//check if currentEvent!=NONE bcz then no registration is required.
		    				if(!currentEvent.equals("NONE")) {
		    					registerEventToDb regTask=new registerEventToDb(currentEvent,eventIdMap.get(currentEvent));
		    					Log.i("MYLOG","");
					        	regTask.execute();
		    				}
		    			}
		    		});
		    		alt_bld.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { // TODO Auto-generated method stub dialog.cancel();
		    		} }); 
		    		alt_bld.show();
		        	
		        	
		        }
		};
		private OnClickListener imageButtonListener = new OnClickListener() {
	        
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
	        	Log.d("MYLOG","imageclicked");
				Dialog d=new Dialog(getActivity());
				d.setContentView(R.layout.dialog);
				d.setTitle(etv.getText());
				ImageView iv=(ImageView)d.findViewById(R.id.iv1);
				//iv.setBackground(ib.getBackground());
				iv.setBackgroundDrawable(ib.getBackground());
				
				d.show();
	        }};
	        
	        
	        class registerEventToDb extends AsyncTask<String, String, String> {
				 
		        /**
		         * Before starting background thread Show Progress Dialog
		         * */
				private String eventName;
				private int eId;
				private UserData userObj;
				private int successValue;
				public registerEventToDb(String eName,int eventId) {
					eventName=eName;
					eId=eventId;
				}
		        @Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(getActivity());
		            pDialog.setMessage("Registering you for " + eventName);
		            pDialog.setIndeterminate(false);
		            pDialog.setCancelable(false);
		            pDialog.show();
		        }
		 
		        /**
		         * Creating product
		         * */
		        protected String doInBackground(String... args) {
		            // Building Parameters
		        	ObjectInputStream objIStream;
					try {
						objIStream = new ObjectInputStream(new FileInputStream(new File(getActivity().getFilesDir()+File.separator+Data.userDataFileName)) );
						userObj=(UserData)objIStream.readObject();
						objIStream.close();

					} catch (StreamCorruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        		        	
		            List<NameValuePair> params = new ArrayList<NameValuePair>();
		            
		            params.add(new BasicNameValuePair("username", userObj.technoID));
		            params.add(new BasicNameValuePair("eventname",eventName ));
		            params.add(new BasicNameValuePair("eventid",Integer.toString(eId)));
		            // getting JSON Object
		            // Note that create product url accepts POST method
		            Log.i("MYLOG","making httprequest...");
		            JSONObject json = jsonParser.makeHttpRequest(url_register_event,"POST", params);
		 
		 
		            // check for success tag
		            try {
		                successValue = json.getInt(TAG_SUCCESS);
		 
		                if (successValue == 1) {
		                	Log.i("MYLOG","Success...");
		                } else {
		                	Log.i("MYLOG","Failure...");
		                	
		                    // failed to create product
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		            }
		            return null;
		        }
		 
		        /**
		         * After completing background task Dismiss the progress dialog
		         * **/
		        protected void onPostExecute(String file_url) {
		            // dismiss the dialog once done
		            pDialog.dismiss();
		            if(successValue==1){
		            	Toast.makeText(getActivity(), "Thank you for registering for " + eventName, Toast.LENGTH_SHORT).show();
		            }else if(successValue==2) {
		            	Toast.makeText(getActivity(), "You have already registered for " + eventName, Toast.LENGTH_SHORT).show();
		            }else {
		            	Toast.makeText(getActivity(), "Sorry, an error occurred while registering..", Toast.LENGTH_SHORT).show();
		            }
		        }
		    }

			
		}
		    //View v = findViewById(R.id.rr);
			//gestureDetector = new GestureDetectorCompat(this, this);
		    //v.setOnTouchListener(this);
					/* private void onLeftSwipe() {
			    go=new Intent("com.gotechno.technovanza13.eventsdisplay");
				Bundle b=new Bundle();
				b.putInt("pos", x);
				b.putInt("position", (y-1));
				go.putExtras(b);
				startActivity(go);
				finish();
			}

			private void onRightSwipe() {
			    go=new Intent("com.gotechno.technovanza13.eventsdisplay");
				Bundle b=new Bundle();
				b.putInt("pos", x);
				b.putInt("position", (y+1)%7);
				go.putExtras(b);
				startActivity(go);
				finish();
			}


			private static final int SWIPE_MIN_DISTANCE = 5;
	        private static final int SWIPE_THRESHOLD_VELOCITY = 10;

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return true;
			}



			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				// TODO Auto-generated method stub
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE &&
	                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	           //From Right to Left
					 onRightSwipe();
	           return true;
	       }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
	                    Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	           //From Left to Right
	    	  
	    	   onLeftSwipe();
	           return true;
	       }
	      
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}



			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}



			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(gestureDetector.onTouchEvent(event)){
					return true;
				}
				return false;
			}
		
	*/

		
	


