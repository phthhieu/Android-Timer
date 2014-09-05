package com.hieupham.android.androidtimer.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hieupham.android.androidtimerlib.AndroidTimer;

public class AndroidTimerSampleActivity extends Activity implements AndroidTimer.OnTickListener {

	private TextView mTimerTv;
	private AndroidTimer mTimer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_timer_sample);
        mTimerTv = (TextView) findViewById(R.id.timerTv);
        mTimer = new AndroidTimer.Builder()
        			.listener(this)
        			.looper(Looper.getMainLooper())
        			.timerIntervalInSeconds(1)
        			.build();
        
        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button stopBtn = (Button) findViewById(R.id.stopBtn);
        
        startBtn.setOnClickListener(onButtonClickedListener);
        stopBtn.setOnClickListener(onButtonClickedListener);
    }
    
    private OnClickListener onButtonClickedListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.startBtn) {
				mTimer.start();
			} else {
				mTimer.stop();
			}
		}
	};


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.android_timer_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	public void onTick(long timestampInMilliseconds) {
		mTimerTv.setText(getString(R.string.timer) + timestampInMilliseconds);
	}
}
