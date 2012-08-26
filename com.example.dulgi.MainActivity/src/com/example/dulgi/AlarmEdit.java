package com.example.dulgi;

import java.util.Calendar;

import com.y2util.AndroidUtil;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class AlarmEdit extends Activity {
	
	// Dialog resource ID
	final static int ID_DLG_DATE= 1001;
	final static int ID_DLG_TIME = 1002;
	
	Calendar mCalendar;
	
	Alarm mAlarm;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_edit);
        
        mCalendar = Calendar.getInstance();
        
        Intent i = getIntent();
        mAlarm = (Alarm) i.getSerializableExtra("alarmData");
        
        reSetOneOverMinTime ();
        
        onButtonEvent ();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//	    getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}
	
	public void onButtonEvent () {
		Button btn;
	    btn = (Button)findViewById(R.id.btnSetDate);
	    btn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "날짜 설정");
				showDialog(ID_DLG_DATE);
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnSetTime);
	    btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "시간 설정");
				showDialog(ID_DLG_TIME);
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnSetSec_20sec);
	    btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "20초 뒤");
				reSetOneOverMinTime ();
			}
		});
	    
	    btn = (Button)findViewById(R.id.btnSubmit);
	    btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlarmStart ();
			}
		});
	    
	    ToggleButton btnTorchToggle = (ToggleButton)this.findViewById(R.id.btnToggleLight);
	    btnTorchToggle.setChecked (mAlarm.isUseTorch());
	    btnTorchToggle.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "Toggle Button Light");
				ToggleButton btn = (ToggleButton)v;
				mAlarm.setUseTorch (btn.isChecked());
			}
		});
	    
	    ToggleButton btnVibratorToggle = (ToggleButton)this.findViewById(R.id.btnToggVibrator);
	    btnVibratorToggle.setChecked (mAlarm.isUseVibration());
	    btnVibratorToggle.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "Toggle Button Vibrator");
				ToggleButton btn = (ToggleButton)v;
				mAlarm.setUseVibration (btn.isChecked());
			}
		});
	    
	    ToggleButton btnSoundToggle = (ToggleButton)this.findViewById(R.id.btnToggSound);
	    btnSoundToggle.setChecked (mAlarm.isUseSound());
	    btnSoundToggle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "Toggle Button Sound");
				ToggleButton btn = (ToggleButton)v;
				mAlarm.setUseSound (btn.isChecked());
			}
		});
	}
	
	public void reSetOneOverMinTime () {
		
	    Calendar cal = Calendar.getInstance();
	    mCalendar.set(cal.get(Calendar.YEAR),
	    		cal.get(Calendar.MONTH),
	    		cal.get(Calendar.DAY_OF_MONTH),
	    		cal.get(Calendar.HOUR_OF_DAY),
	    		cal.get(Calendar.MINUTE),
	    		cal.get(Calendar.SECOND) + 10);
	    
	    reSetScehedule ();
	}
	
	public void reSetScehedule () {
		TextView txtViewTime = (TextView)findViewById(R.id.timeView);
		txtViewTime.setText (calendarToString(mCalendar));
	}
	
	public String calendarToString (Calendar cal) {
		String dateToString , timeToString;
	    dateToString = String.format("%04d-%02d-%02d", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH)+1, mCalendar.get(Calendar.DAY_OF_MONTH));
	    timeToString = String.format("%02d:%02d:%02d", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), mCalendar.get(Calendar.SECOND));
//	    timeToString = String.format("%02d:%02d:%02d", mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), mCalendar.get(Calendar.SECOND));
	    
	    return dateToString + "   " + timeToString;
	}
	
	public void AlarmStart () {
		Intent intent = new Intent(this, AlarmReceiver.class);
	    intent.putExtra("alarmData", mAlarm);
	    
	    PendingIntent pendingIntent = PendingIntent.getActivity(this,
	    		mAlarm.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
	    am.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
	    
	    AndroidUtil.showLogI(getClass().getName(), "새 알람 등록 완료");
	    
	    Intent i = getIntent();
	    i.putExtra("alarmData", mAlarm);
	    setResult(RESULT_OK, i);
	    finish ();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case ID_DLG_DATE:	        	
				return new DatePickerDialog(this, mDateSetListener, 
						mCalendar.get(Calendar.YEAR),
						mCalendar.get(Calendar.MONTH),
						mCalendar.get(Calendar.DAY_OF_MONTH));
			case ID_DLG_TIME:
				return new TimePickerDialog(this,
						mTimeSetListener,
						mCalendar.get(Calendar.HOUR_OF_DAY),
						mCalendar.get(Calendar.MINUTE), false);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			AndroidUtil.showToast(AlarmEdit.this, monthOfYear);
			mCalendar.set (Calendar.YEAR, year);
			mCalendar.set (Calendar.MONTH, monthOfYear);
			mCalendar.set (Calendar.DAY_OF_MONTH, dayOfMonth);
			reSetScehedule ();
			
		}
	};
	
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mCalendar.set (Calendar.HOUR_OF_DAY, hourOfDay);
			mCalendar.set (Calendar.MINUTE, minute);
			reSetScehedule ();
		}
	};
}
