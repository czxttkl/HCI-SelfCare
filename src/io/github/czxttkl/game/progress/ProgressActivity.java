package io.github.czxttkl.game.progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;

import com.actionbarsherlock.sample.shakespeare.R;

public class ProgressActivity extends FragmentActivity {
	
	CheckBox mPhysical;
	CheckBox mMental;
	CheckBox mSocial;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.progress);
		
		mPhysical = (CheckBox)findViewById(R.id.filter_physical);
		mPhysical.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mMental.setChecked(false);
					mSocial.setChecked(false);
				}
			}
		});
		
		mMental = (CheckBox)findViewById(R.id.filter_mental);
		mMental.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mPhysical.setChecked(false);
					mSocial.setChecked(false);
				}
			}
		});
		
		mSocial = (CheckBox)findViewById(R.id.filter_social);
		mSocial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mMental.setChecked(false);
					mPhysical.setChecked(false);
				}
			}
		});
	}

	public void onClickUpdate(View v) {
		Intent i = new Intent(this, UpdateProgress.class);
		startActivity(i);
	}
}
