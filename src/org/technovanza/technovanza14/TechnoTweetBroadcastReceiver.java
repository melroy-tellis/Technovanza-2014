package org.technovanza.technovanza14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TechnoTweetBroadcastReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			context.getApplicationContext().startService(new Intent(context.getApplicationContext(),TimelineService.class));
		}
		else if(intent.getAction().equals(Intent.ACTION_SHUTDOWN))
		{
			try
			{
				context.getApplicationContext().stopService(new Intent(context,TimelineService.class));
			}
			catch(Exception e)
			{
				Log.i("SHUTDOWN", "Failed to stop TimelineService:"+e.getMessage());
			}
		}

	}

}
