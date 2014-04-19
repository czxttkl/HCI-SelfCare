package io.github.czxttkl.game.create;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class CameraActivity extends SingleFragmentActivity {
	private static final String TAG = "CameraActivity";
	public static final String SWITCH_CAMERA = "SwitchCamera";
	private boolean switchCamera = false;
	private int cameraId = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        cameraId = sharedPreferences.getInt(SWITCH_CAMERA, 0);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
			switchCamera = extras.getBoolean(SWITCH_CAMERA);
			Log.i(TAG, "ready for switching");
			if (switchCamera) {
				if (cameraId == 0) {
					cameraId = 1;
				} else {
					cameraId = 0;
				}
				
				
				Editor editor = sharedPreferences.edit();
				editor.putInt(SWITCH_CAMERA, cameraId);
				editor.commit();
			}
			Log.i(TAG, "switching to " + cameraId);
		}
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected Fragment createFragment() {
    	
    	return createCameraFragment(cameraId);
    }
    
    protected Fragment createCameraFragment(int cameraId) {
    	Bundle args = new Bundle();
    	if (cameraId == 0) {
        	args.putInt(CameraFragment.USING_CAMERA, 0);
		} else if (cameraId == 1) {
	    	args.putInt(CameraFragment.USING_CAMERA, 1);
		}
		CameraFragment fragment = new CameraFragment();
		fragment.setArguments(args);
		
        return fragment;
	}
}

