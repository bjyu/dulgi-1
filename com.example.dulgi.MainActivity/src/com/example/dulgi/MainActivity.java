package com.example.dulgi;

import com.y2util.AndroidUtil;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	// Activity resource ID
	final static int ID_ACTIVITY_ALARM_EDIT = 100;
	
	ArrayList <Alarm> mAlarms;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        onButtonEvent ();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 //       getMenuInflater().inflate(R.menu.activity_main, menu);
        return false;
    }
    
    public void onButtonEvent () {
    	Button btn = (Button)findViewById(R.id.btnNew);
    	btn.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				AndroidUtil.showLogI(getClass().getName(), "새 알람");
				
				Intent i = new Intent(MainActivity.this, AlarmEdit.class);
				i.putExtra("alarmData", new Alarm ());
				startActivityForResult(i, ID_ACTIVITY_ALARM_EDIT);
				
				XmlDocument doc = new XmlDocument ();
		        doc.load (MainActivity.this);
			}
		});
    }
    
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == ID_ACTIVITY_ALARM_EDIT) {
    		
    		if (resultCode == RESULT_OK) {
    			Alarm al = (Alarm) data.getSerializableExtra("alarmData");
        		
    			XmlDocument doc = new XmlDocument ();
    			doc.save (this, al);
    			
        		AndroidUtil.showToast(this, "새 알람 등록 완료");
        		
    		} else {
    			
    			AndroidUtil.showToast(this, "취소");
    		}
    		
    		
    	}
    }
}
