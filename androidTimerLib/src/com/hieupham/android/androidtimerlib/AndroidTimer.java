package com.hieupham.android.androidtimerlib;

import java.util.Calendar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * This class implement a custom mechanism basing on Handler to handle some task
 * that want to use a timer 
 * @author hieupham
 *
 */
public class AndroidTimer {
	private static final String TAG = "AndroidTimer";
	private static final int DEFAULT_INTERVAL = 1000; // 1s
	
	private Handler mTimerHandler = null;
	private int mTimerIntervalInMilliseconds = DEFAULT_INTERVAL;
	private OnTickListener mListener = null;
	private Boolean mIsTimerRunning = false;
	
	// Must create AndroidTimer instance via Builder
	private AndroidTimer() {
	}
	
	private Runnable mTimerRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mTimerHandler == null) {
				NullTimerHandlerException.throwException();
				return;
			}
			
			if (mListener != null) {
				mListener.onTick(Calendar.getInstance().getTimeInMillis());
			}
			
			mTimerHandler.postDelayed(mTimerRunnable, mTimerIntervalInMilliseconds);
		}
	};
	
	/**
	 * Start the timer
	 */
	public void start() {
		synchronized (mIsTimerRunning) {
			if (mIsTimerRunning) {
				TriggerTimerException.throwTriggerStartingTimerException();
				return;
			} else if (mTimerHandler == null){
				NullTimerHandlerException.throwException();
				return;
			} else {
				mIsTimerRunning = true;
				mTimerHandler.post(mTimerRunnable);
				Log.d(TAG, "Timer is started");
			}
		}
	}
	
	/**
	 * Stop the timer
	 */
	public void stop() {
		synchronized (mIsTimerRunning) {
			if (!mIsTimerRunning) {
				TriggerTimerException.throwTriggerStoppingTimerException();
				return;
			} else if (mTimerHandler == null){
				NullTimerHandlerException.throwException();
				return;
			} else {
				mIsTimerRunning = false;
				mTimerHandler.removeCallbacks(mTimerRunnable);
				Log.d(TAG, "Timer is stopped");
			}
		}
	}
	
	public static class Builder {
		private int mTimerIntervalInMilliseconds = DEFAULT_INTERVAL;
		private Looper mLooper = null;
		private OnTickListener mListener;
		
		/**
		 * Builder AndroidTimer instance basing on properties
		 */
		public AndroidTimer build() {
			AndroidTimer timer = new AndroidTimer();
			if (mLooper == null) {
				timer.mTimerHandler = new Handler();
			} else {
				timer.mTimerHandler = new Handler(mLooper);
			}
			timer.mListener = mListener;
			timer.mTimerIntervalInMilliseconds = mTimerIntervalInMilliseconds;
			return timer;
		}
		
		/**
		 * Set listener to builder
		 * @param listener the listener will observer onTick event
		 * @return current building Builder
		 */
		public Builder listener(OnTickListener listener) {
			mListener = listener;
			return this;
		}
		
		/**
		 * Set timer interval to builder
		 * @param timerIntervalInMilliseconds timer interval in milliseconds
		 * @return current building Builder
		 */
		public Builder timerIntervalInMilliseconds(int timerIntervalInMilliseconds) {
			mTimerIntervalInMilliseconds = timerIntervalInMilliseconds;
			return this;
		}
		
		/**
		 * Set timer interval to builder
		 * @param timerIntevalInSeconds timer interval in seconds
		 * @return current building Builder
		 */
		public Builder timerIntervalInSeconds(int timerIntevalInSeconds) {
			mTimerIntervalInMilliseconds = timerIntevalInSeconds * 1000;
			return this;
		}
		
		/**
		 * Set the looper to schedule the timer, if it is null, the default looper will be used
		 * @param looper a Looper
		 * @return current building Builder
		 */
		public Builder looper(Looper looper) {
			mLooper = looper;
			return this;
		}
	}
	
	public interface OnTickListener {
		public void onTick(long timestampInMilliseconds);
	}
}
