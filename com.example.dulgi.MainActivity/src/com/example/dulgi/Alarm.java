package com.example.dulgi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

class Alarm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int mId;
	boolean mIsUse;
	long mTime;
	ArrayList <Integer> mDaily;
	String mSlogan;
	boolean mIsUseVibration;
	boolean mIsUseTorch;
	boolean mIsUseSound;
	String mSoundPath;
	String mBackgroundImagePath;
	
	Alarm () {
		mId = Math.abs((int)Calendar.getInstance().getTimeInMillis());
		mIsUse = false;
		mTime = Calendar.getInstance().getTimeInMillis();
		mDaily = new ArrayList <Integer> ();
		mSlogan = "";
		mIsUseVibration = true;
		mIsUseTorch = true;
		mIsUseSound = true;
		mSoundPath = "";
		mBackgroundImagePath = "";
	}
	
	Alarm (Alarm alarm) {
		mId = alarm.mId;
		mIsUse = alarm.mIsUse;
		mTime = alarm.mTime;
		mDaily.clear();
		mDaily.addAll(alarm.mDaily);
		mSlogan = alarm.mSlogan;
		mIsUseVibration = alarm.mIsUseVibration;
		mIsUseTorch = alarm.mIsUseTorch;
		mIsUseSound = alarm.mIsUseSound;
		mSoundPath = alarm.mSoundPath;
		mBackgroundImagePath = alarm.mBackgroundImagePath;
	}
	
	// TODO : get Method
	int getId () {
		return mId;
	}
	long getTime () {
		return mTime;
	}
	ArrayList<Integer> getDaily () {
		return mDaily;
	}
	String getSlogan () {
		return mSlogan;
	}
	String getSoundPath () {
		return mSoundPath;
	}
	String getBackgroundImagePath () {
		return mBackgroundImagePath;
	}
	
	// TODO : is Method
	boolean isUse () {
		return mIsUse;
	}
	boolean isUseVibration () {
		return mIsUseVibration;
	}
	boolean isUseTorch () {
		return mIsUseTorch;
	}
	boolean isUseSound () {
		return mIsUseSound;
	}
	
	// TODO : set Method
	void setId (int id) {
		mId = id;
	}
	void setUse (boolean isUse) {
		mIsUse = isUse;
	}
	void setTime (long time) {
		mTime = time;
	}
	void setDaily (ArrayList<Integer> daily) {
		mDaily.clear();
		mDaily.addAll(daily);
	}
	void setSlogan (String slogan) {
		mSlogan = slogan;
	}
	void setUseVibration (boolean isUse) {
		mIsUseVibration = isUse;
	}
	void setUseTorch (boolean isUse) {
		mIsUseTorch = isUse;
	}
	void setUseSound (boolean isUse) {
		mIsUseSound = isUse;
	}
	void setSoundPath (String path) {
		mSoundPath = path;
	}
	void setBackgroundImagePath (String path) {
		mBackgroundImagePath = path;
	}
}
