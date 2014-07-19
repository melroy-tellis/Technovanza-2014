
package org.technovanza.technovanza14;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Tabfragment_house extends Fragment{
	    private static final String KEY_CONTENT = "Tabfragment_house:Content";

	    public static Tabfragment_house newInstance(String content) {
	        Tabfragment_house fragment = new Tabfragment_house();

	        StringBuilder builder = new StringBuilder();
	        for (int i = 0; i < 20; i++) {
	            builder.append(content).append(" ");
	        }
	        builder.deleteCharAt(builder.length() - 1);
	        fragment.mContent = builder.toString();

	        return fragment;
	    }

	    private String mContent = "???";

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
	            mContent = savedInstanceState.getString(KEY_CONTENT);
	        }
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        TextView text = new TextView(getActivity());
	        text.setGravity(Gravity.CENTER);
	        text.setText(mContent);
	        text.setTextSize(20 * getResources().getDisplayMetrics().density);
	        text.setPadding(20, 20, 20, 20);

	        LinearLayout layout = new LinearLayout(getActivity());
	        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        layout.setGravity(Gravity.CENTER);
	        layout.addView(text);

	        return layout;
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putString(KEY_CONTENT, mContent);
	    }
	}
