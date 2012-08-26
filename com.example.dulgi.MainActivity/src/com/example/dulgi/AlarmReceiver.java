package com.example.dulgi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.y2util.AndroidUtil;
import com.y2util.DeviceUtil;

public class AlarmReceiver extends Activity implements SurfaceHolder.Callback {
	
	Alarm mAlarm;
	
	private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    MediaPlayer mPlayer;
    Button mPlayBtn;
    private String mMediaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/media/movie/33.flv";
//    private String mMediaPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/media/movie/22.wmv";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		// 가로 고정
		this.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alarm_receiver2);
		
		Intent intent = getIntent();
		mAlarm = (Alarm) intent.getSerializableExtra("alarmData");
		
		/*
		if (mAlarm.isUseTorch())
			DeviceUtil.turnTorch(true);
		
		if (mAlarm.isUseVibration())
			DeviceUtil.turnVibration(this, true);
		
		if (mAlarm.isUseSound()) {
			DeviceUtil.turnSound(this, true);
		}
		*/
		
		mPreview = (SurfaceView)findViewById(R.id.mideaView);
        mHolder = mPreview.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
		stopAlarm.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				allStop ();
				finish();
			}
		});
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
    }
	
    public void surfaceCreated(SurfaceHolder holder) {
        if(mPlayer==null){
            mPlayer=new MediaPlayer();
        }else{
            mPlayer.reset();
        }
        
        try{
            mPlayer.setDataSource(mMediaPath);
            mPlayer.setDisplay(holder);
            mPlayer.setOnCompletionListener(mComplete);
            mPlayer.setOnVideoSizeChangedListener(mSizeChange);
            mPlayer.setScreenOnWhilePlaying(true);
            mPlayer.setLooping(true);
            
            mPlayer.prepare();
            
            AndroidUtil.showLogI("surfaceCreated", mPlayer.getVideoWidth());
            AndroidUtil.showLogI("surfaceCreated", mPlayer.getVideoHeight());
            
            
            mPlayer.start();
            
            
            
        }catch(Exception e){
        	AndroidUtil.showLogE("mPlayer", "media player  error~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
    
    MediaPlayer.OnCompletionListener mComplete = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
        	AndroidUtil.showLogI("AlarmReceiver", "  <<< ----- OnCompletionListener ----- >>>  ");
        }
    };
    
    MediaPlayer.OnVideoSizeChangedListener mSizeChange = new MediaPlayer.OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        	AndroidUtil.showLogI("AlarmReceiver", "  <<< ----- OnCompletionListener ----- >>>  ");
        }
    };
    
    public void surfaceDestroyed(SurfaceHolder holder) {
        
    }
    
    protected void onDestroy(){
        super.onDestroy();
        if(mPlayer!=null){
            mPlayer.release();
        }
    }
	
	public void allStop () {
		DeviceUtil.turnTorch(false);
		DeviceUtil.turnSound(this, false);
		DeviceUtil.turnVibration(this, false);
		
		if (mPlayer != null) {
			mPlayer.stop();
		}
	}
}