package com.example.dulgi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.y2util.AndroidUtil;

import android.content.Context;

public class XmlDocument {
	
	public void load (Context context) {
		File dir = context.getExternalFilesDir (null);
		File file = new File(dir, "file.txt");
		
		BufferedReader bufferReader = null;
		FileInputStream fis;
		String str = null;
		try {
			
			fis = new FileInputStream (file);
			bufferReader = new BufferedReader(new InputStreamReader(fis));
			str = bufferReader.readLine();
			
//			AndroidUtil.showLogI("Document - load", strPath);
			
			JSONLoad (str);
			
		} catch (IOException e) {
			AndroidUtil.showLogI("Document - load", "파일 없음");
			e.printStackTrace();
		}
	}
	
	public void JSONLoad (String jsonStr) {
		
		JSONObject doc;
		try {
			
			doc = new JSONObject(jsonStr);
			JSONArray alarms = new JSONArray(doc.getString("Alarms")); 
	        
	        for (int i = 0 ; i < alarms.length() ; i++) {
	        	JSONObject alarm = alarms.getJSONObject(i);
	        	int id = alarm.getInt("id");
	        	long time = alarm.getLong("time");
	        	
	        	AndroidUtil.showLogI("Document - load", id);
	        	AndroidUtil.showLogI("Document - load", time);
	        }
	        
		} catch (JSONException e) {
			AndroidUtil.showLogI("Document - load", "JSON 에러");
			e.printStackTrace();
		}
        
	}
	
	public void save (Context context, Alarm al) {
		File dir = context.getExternalFilesDir (null);
		File file = new File(dir, "file.txt");
		
		AndroidUtil.showLogI("Document - save", file.getPath());
		
		String s = JSONSave(al);
		
		FileOutputStream fos;
		try {
			
			fos = new FileOutputStream(file);
			fos.write(s.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String JSONSave (Alarm al) {
		
		JSONObject doc = new JSONObject();
		JSONArray alarms = new JSONArray();
		try {
			
			JSONObject alarm = new JSONObject();
			alarm.put("id", al.getId());
			alarm.put("time", al.getTime());
			
			alarms.put(alarm);
			
			doc.put("Alarms", alarms);
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String s = doc.toString();
		
		return s;
	}
}
