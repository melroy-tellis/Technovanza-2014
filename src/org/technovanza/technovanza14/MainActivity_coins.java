package org.technovanza.technovanza14;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_coins extends Activity {
	private LinearLayout lay;
	HorizontalListView listview;
	private Double highest;
	
	private Double highest1;
	private Double highest2;
	private int[] grossheight;
	private int[] netheight;
	private int[] netheight2;
	/*first array is red phoenix, second is green griffins and third blue dragons
	 ...please make sure the values are Double by just writing .0..no need to change names of variables and copy paste the xml files i have sent*/
	private Double[] grossSal= { 100.0 };
	private Double[] netSal= {  200.0};
	private Double[] netSal2= {300.0};
	private String[] datelabel= {"adh"};


	private ProgressDialog pDialog;

	 	JSONParser jParser = new JSONParser();
	
	ArrayList<HashMap<String, String>> productsList;
	ArrayList<Integer> p1 = new ArrayList<Integer>();
	ArrayList<Integer> p2 = new ArrayList<Integer>();
	ArrayList<Integer> p3 = new ArrayList<Integer>();
	ArrayList<String> date_list = new ArrayList<String>();
	// url to get all products list
	private static String url_all_products = "http://technotest.bugs3.com/vinil/get_all_products2.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "coins_table";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_Points = "points";
	private static final String TAG_Point2 = "point2";
	private static final String TAG_Point3 = "point3";
	private static final String TAG_date = "date";
	

	
	String [] date_array;
	Double p1_array[],p2_array[],p3_array[];  //final arrays which will contain the data
	
	
	// products JSONArray
	JSONArray products = null;

	public void onCreate(Bundle savedInstance) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
			        new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		if(isNetworkAvailable())
		{
			
			Log.d("internet","internet");
			
	//fetchesthe data
			load();
			
			/*new LoadAllProducts().execute();
			try
			{
				Thread.sleep(2000);
			}
			catch(Exception e){}*/
		}
		else {Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
				
		}
		if(grossSal[0]==100)
			load();//new LoadAllProducts().execute();
		super.onCreate(savedInstance);
		setContentView(R.layout.main_coins);
		lay = (LinearLayout) findViewById(R.id.linearlay);
		listview = (HorizontalListView) findViewById(R.id.listview);
		
		List<Double> b = Arrays.asList(grossSal);
		if(grossSal[0]==100)
			new LoadAllProducts().execute();
		highest = (Collections.max(b));
		b = Arrays.asList(netSal);
		highest1 = (Collections.max(b));
		b = Arrays.asList(netSal2);
		highest2 = (Collections.max(b));
		highest = highest>highest1?highest:highest1;
		highest = highest>highest2?highest:highest2;
		netheight2 = new int[netSal.length];
		netheight = new int[netSal.length];
		grossheight = new int[grossSal.length];
		Log.d("done4","done4");
		// updateSizeInfo();

	}
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	    //return activeNetworkInfo != null;
	    
	}
	public class bsAdapter extends BaseAdapter {
		Activity cntx;
		String[] array;

		public bsAdapter(Activity context, String[] arr) {
			// TODO Auto-generated constructor stub
			this.cntx = context;
			this.array = arr;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return array.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return array[position];
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return array.length;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			

			View row = null;
			LayoutInflater inflater = cntx.getLayoutInflater();
			row = inflater.inflate(R.layout.simplerow_coins, null);

			DecimalFormat df = new DecimalFormat("#.##");
			final TextView title = (TextView) row.findViewById(R.id.title);
			TextView tvcol1 = (TextView) row.findViewById(R.id.colortext01);
			TextView tvcol2 = (TextView) row.findViewById(R.id.colortext02);

			TextView gt = (TextView) row.findViewById(R.id.text01);
			TextView nt = (TextView) row.findViewById(R.id.text02);
			TextView tvcol3 = (TextView) row.findViewById(R.id.colortext03);
			TextView nt2 = (TextView) row.findViewById(R.id.text03);

			tvcol1.setHeight(grossheight[position]);
			tvcol2.setHeight(netheight[position]);
			tvcol3.setHeight(netheight2[position]);

			title.setText(datelabel[position]);

			gt.setText(df.format(grossSal[position]));
			nt.setText(df.format(netSal[position]));
			nt2.setText(df.format(netSal2[position]));
			
			
			/*
			 * tvcol1.setOnClickListener(new OnClickListener() {
			 * 
			 * public void onClick(View v) { Toast.makeText(MainActivity.this,
			 * "Month/Year: "
			 * +title.getText().toString()+"\nGross Sal: "+grossSal[position],
			 * Toast.LENGTH_SHORT).show(); } });
			 * 
			 * tvcol2.setOnClickListener(new OnClickListener() {
			 * 
			 * public void onClick(View v) { Toast.makeText(MainActivity.this,
			 * "Month/Year: "
			 * +title.getText().toString()+"\nNet Sal: "+netSal[position],
			 * Toast.LENGTH_SHORT).show(); } });
			 */
			
			return row;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		updateSizeInfo();
	}

	private void updateSizeInfo() {

		/**
		 * This is onWindowFocusChanged method is used to allow the chart to
		 * update the chart according to the orientation. Here h is the integer
		 * value which can be updated with the orientation
		 */
		int h;
		if (getResources().getConfiguration().orientation == 1) {
			h = (int) (lay.getHeight() * 0.5);
			if (h == 0) {
				h = 200;
			}
		} else {
			h = (int) (lay.getHeight() * 0.3);
			if (h == 0) {
				h = 130;
			}
		}
		for (int i = 0; i < netSal.length; i++) {
			netheight2[i] = (int) ((h * netSal2[i]) / highest);
			
			netheight[i] = (int) ((h * netSal[i]) / highest);
			grossheight[i] = (int) ((h * grossSal[i]) / highest);
			System.out.println("net width[i] " + netheight[i]
					+ "gross width[i] " + grossheight[i]);
		}
		listview.setAdapter(new bsAdapter(this, datelabel));
	}
	void load()
	{	
		if(isNetworkAvailable())
	
		{
		
		Log.d("internet","internet");
		Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
		
		// Check your log cat for JSON reponse
		Log.d("All Products: ", json.toString());
		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				products = json.getJSONArray(TAG_PRODUCTS);
				String points="";
				// looping through All Products
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);

					// Storing each json item in variable
					String id = c.getString(TAG_PID);
					String name = c.getString(TAG_NAME);
					points = c.getString(TAG_Points);
					String point2 = c.getString(TAG_Point2);
					String point3 = c.getString(TAG_Point3);
					String date = c.getString(TAG_date);
					
					p1.add(Integer.parseInt(points));
					p2.add(Integer.parseInt(point2));
					p3.add(Integer.parseInt(point3));
					date_list.add(name);
					
					
				}
				Log.d("finished", "finished");
				p1_array=new Double[p1.size()];
				p2_array=new Double[p2.size()];
				p3_array=new Double[p3.size()];
				
				date_array=date_list.toArray(new String[date_list.size()]);
				int x;
				for (int i = 0; i < p1_array.length; i++) {
					x=(int)p1.get(i);
					p1_array[i] = (double) x;
				    x=(int)p2.get(i);
				    p2_array[i] = (double) x;
				    x=(int)p3.get(i);
				    p3_array[i] = (double) x;
				}
				System.out.println(p1_array[0]+date_array[0]);
				Log.d("finished2", "finished");
				
				/*grossSal=p2_array;
				netSal=p3_array;
				netSal2=p1_array;
				datelabel=date_array;*/
			} else {

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		}
		else {Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
		p1_array=new Double[1];p1_array[0]=(double)515;
		p2_array=new Double[1];p2_array[0]=(double)770;
		p3_array=new Double[1];p3_array[0]=(double)675;
		date_array=new String[1];date_array[0]="Sudoku";
		
	}
		
		grossSal=new Double[p1_array.length];;
		netSal=new Double[p1_array.length];;
		netSal2=new Double[p1_array.length];
		datelabel=new String[p1_array.length];
		Log.d("done2","done2");
		for(int i=0;i<p1_array.length;i++)
		{
			grossSal[i]=p2_array[i];
			netSal[i]=p3_array[i];
			netSal2[i]=p1_array[i];
			datelabel[i]=date_array[i];
		}
		Log.d("done","done");
	}
	class LoadAllProducts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity_coins.this);
			pDialog.setMessage("Loading Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			if(isNetworkAvailable())
				
			{
				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = 
					        new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					}
			Log.d("internet","internet");
			Toast.makeText(getApplicationContext(), "loading", Toast.LENGTH_SHORT).show();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());
			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_PRODUCTS);
					String points="";
					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_PID);
						String name = c.getString(TAG_NAME);
						points = c.getString(TAG_Points);
						String point2 = c.getString(TAG_Point2);
						String point3 = c.getString(TAG_Point3);
						String date = c.getString(TAG_date);
						
						p1.add(Integer.parseInt(points));
						p2.add(Integer.parseInt(point2));
						p3.add(Integer.parseInt(point3));
						date_list.add(name);
						
						
					}
					Log.d("finished", "finished");
					p1_array=new Double[p1.size()];
					p2_array=new Double[p2.size()];
					p3_array=new Double[p3.size()];
					
					date_array=date_list.toArray(new String[date_list.size()]);
					int x;
					for (int i = 0; i < p1_array.length; i++) {
						x=(int)p1.get(i);
						p1_array[i] = (double) x;
					    x=(int)p2.get(i);
					    p2_array[i] = (double) x;
					    x=(int)p3.get(i);
					    p3_array[i] = (double) x;
					}
					System.out.println(p1_array[0]+date_array[0]);
					Log.d("finished2", "finished");
					
					/*grossSal=p2_array;
					netSal=p3_array;
					netSal2=p1_array;
					datelabel=date_array;*/
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			}
			else {Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
			p1_array=new Double[1];p1_array[0]=(double)515;
			p2_array=new Double[1];p2_array[0]=(double)770;
			p3_array=new Double[1];p3_array[0]=(double)675;
			date_array=new String[1];date_array[0]="Sudoku";
			
		}
			
			grossSal=new Double[p1_array.length];;
			netSal=new Double[p1_array.length];;
			netSal2=new Double[p1_array.length];
			datelabel=new String[p1_array.length];
			Log.d("done2","done2");
			for(int i=0;i<p1_array.length;i++)
			{
				grossSal[i]=p2_array[i];
				netSal[i]=p3_array[i];
				netSal2[i]=p1_array[i];
				datelabel[i]=date_array[i];
			}
			Log.d("done","done");
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread


		}

	}
}
