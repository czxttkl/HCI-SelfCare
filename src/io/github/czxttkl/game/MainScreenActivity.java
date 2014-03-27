package io.github.czxttkl.game;

import com.actionbarsherlock.sample.shakespeare.R;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.main);
	}

	public void onClickNew(View v) {
        Challenge crime = new Challenge();
        ChallengeLab.get(this).addCrime(crime);
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, ChallengeActivity.class));
		startActivity(i);
	}

	public void onClickJoin(View v) {
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, JoinActivity.class));
		startActivity(i);
	}

	public void onClickQuit(View v) {
		this.finish();
	}

	public void onClickProgress(View v) {

	}
}
