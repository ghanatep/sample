package com.example.misctest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;


public class Singleton extends Application {
	private final static String					LOG_TAG				= "Singleton";
	private static 		Singleton				m_Instance;

	static	final		boolean					SET_DEBUG			= true;
	// Appscreen metrics
	public				float					m_fFrameS			= 0;	
	public				int						m_nFrameW			= 0,
												m_nFrameH			= 0,
												m_nTotalW			= 0,
												m_nTotalH			= 0,
												m_nPaddingX			= 0,
												m_nPaddingY			= 0;
		
	public Singleton() {
		super();
		m_Instance = this;
	}
	
	public static Singleton getInstance() {
		if(m_Instance == null) {
			synchronized(Singleton.class) {
				if(m_Instance == null) new Singleton();
			}
		}
		return m_Instance;
	}

	@Override public void onCreate() {
		super.onCreate();
	
	}
	
	
	public static void Debug(String tag, String message) {
		if (SET_DEBUG) {
			Log.d(tag, message);
		}
	}
	
	//must be called in every oncreate 
	public void InitGUIFrame(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		m_nTotalW = dm.widthPixels;
		m_nTotalH = dm.heightPixels;
		// scale factor
		m_fFrameS = (float)m_nTotalW / 640.0f;
		// compute our frame
		m_nFrameW = m_nTotalW;
		m_nFrameH = (int) (960.0f * m_fFrameS);
		// compute padding for our frame inside the total screen size
		
		m_nPaddingY = 0;
		m_nPaddingX = (m_nTotalW - m_nFrameW) / 2;
		
		Debug(LOG_TAG, "InitGUIFrame: frame:"+m_nFrameW+"x"+m_nFrameH+ " Scale:"+m_fFrameS);
		
	}
	
	public int Px2DIP(int value) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int)((float) value * scale);
	}
	

	public int Scale(int v) {
		float s = (float)v * m_fFrameS; int rs = 0;
		if (s - (int)s >= 0.5) rs= ((int)s)+1; else rs= (int)s;
		return rs;
	}
	
	public Bitmap getScaledBitmap(Context context, float scalex, float scaley, int id) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
		Matrix matrix = new Matrix();
		matrix.postScale(scalex, scaley);
		matrix.postRotate(0);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	public Drawable getScaledDrawable(Context context, float scalex, float scaley, int id) {
		return new BitmapDrawable(context.getResources(), getScaledBitmap(context, scalex, scaley, id));
	}


	public int GetPercent(int value, int percent) {
		return percent*value / 100;
	}

	public int getMediumTextSize() {
		return Scale(16);
	}
	
}

