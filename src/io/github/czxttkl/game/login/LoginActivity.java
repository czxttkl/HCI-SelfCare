package io.github.czxttkl.game.login;

import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.sample.shakespeare.R;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}

	public void onClickLogin(View v) {
		TextView tx = (TextView) findViewById(R.id.nameText);
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, MainScreenActivity.class));
		startActivity(i);
		finish();
	}
}
