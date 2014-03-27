package io.github.czxttkl.game;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.actionbarsherlock.sample.shakespeare.R;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.login);
	}

	public void onClickLogin(View v) {
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, MainScreenActivity.class));
		startActivity(i);
		finish();
	}
}
