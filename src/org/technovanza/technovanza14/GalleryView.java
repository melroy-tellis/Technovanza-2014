package org.technovanza.technovanza14;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

@SuppressWarnings("deprecation")
public class GalleryView extends Activity implements OnItemSelectedListener,
		ViewFactory {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.gallery);

		mSwitcher = (ImageSwitcher) findViewById(R.id.imgswitcher);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
		g.setOnItemSelectedListener(this);
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		mSwitcher.setImageResource(mImageIds[position]);
	}

	public void onNothingSelected(AdapterView<?> parent) {
	}

	
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return i;
	}

	private ImageSwitcher mSwitcher;

	public class ImageAdapter extends BaseAdapter {
		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mThumbIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			i.setImageResource(mThumbIds[position]);
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			//i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

		private Context mContext;
	}

	private Integer[] mThumbIds = {R.drawable.tech1,R.drawable.tech2,
			R.drawable.tech3,R.drawable.tech4,
			R.drawable.tech5,R.drawable.tech6,
			R.drawable.tech7,R.drawable.tech8,
			R.drawable.tech9,R.drawable.tech10,
			R.drawable.tech11,R.drawable.tech12,
			R.drawable.tech13,R.drawable.tech14,
			R.drawable.tech15,R.drawable.tech16,
			R.drawable.tech17,R.drawable.tech18,
			R.drawable.tech19,R.drawable.tech20,
			R.drawable.tech21,R.drawable.tech22,
			R.drawable.tech23,
			};
	private Integer[] mImageIds = {R.drawable.tech1,R.drawable.tech2,
			R.drawable.tech3,R.drawable.tech4,
			R.drawable.tech5,R.drawable.tech6,
			R.drawable.tech7,R.drawable.tech8,
			R.drawable.tech9,R.drawable.tech10,
			R.drawable.tech11,R.drawable.tech12,
			R.drawable.tech13,R.drawable.tech14,
			R.drawable.tech15,R.drawable.tech16,
			R.drawable.tech17,R.drawable.tech18,
			R.drawable.tech19,R.drawable.tech20,
			R.drawable.tech21,R.drawable.tech22,
			R.drawable.tech23,
			};
	public void mainpage(View view)
    {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }

}
