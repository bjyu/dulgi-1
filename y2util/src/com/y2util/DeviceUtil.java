package com.y2util;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class DeviceUtil {
	
	static Camera mCamera;
	static MediaPlayer mMediaPlayer;
	static Vibrator mVibrator;
	
	static public void turnTorch (boolean isOn) {
		// ²ô±â
		if (!isOn) {
			if (mCamera != null) {
				Camera.Parameters params =  mCamera.getParameters();
				params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(params);
				mCamera.release();
				
				mCamera = null;
			}
			return;
		}
		
		if (mCamera == null) {
			mCamera = Camera.open();
			
			Camera.Parameters params =  mCamera.getParameters();
			params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(params);
		}
	}
	
	static public void turnSound (Context context, boolean isOn) {
		turnSound (context, isOn, null);
	}
	
	static public void turnSound (Context context, boolean isOn, String path) {
		// ²ô±â
		if (!isOn) {
			if (mMediaPlayer != null) {
				mMediaPlayer.stop();
				
				mMediaPlayer = null;
			}	
			return;
		}
		
		// ±âº» À½¾Ç °æ·Î °¡Á®¿À±â - (¾Ë¸²1)
		Uri alert = null;
		if (path == null) {
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			if (alert == null) {
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				if (alert == null) {
					alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
				}
			}
		}
		
		if (alert == null)
			return;
		
		if (mMediaPlayer == null)
			mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
			
			//int sb2value = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
			int sb2value = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
			audioManager.setStreamVolume(AudioManager.STREAM_ALARM, sb2value, 0);
			
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.setLooping(true);
				mMediaPlayer.start();
			}
			
		} catch (IOException e) {
			System.out.println("OOPS");
		}
	}
	
	static public void turnVibration (Context context, boolean isOn) {
		// ²ô±â
		if (!isOn) {
			if (mVibrator != null) {
				mVibrator.cancel();
				
				mVibrator = null;
			}	
			return;
		}
		
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		// Start immediately
		// Vibrate for milliseconds
		// Sleep for milliseconds
		long[] pattern = { 0, 500, 500 };
		mVibrator.vibrate(pattern, 0);
	}
}
