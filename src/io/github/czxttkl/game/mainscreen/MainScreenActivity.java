package io.github.czxttkl.game.mainscreen;

import io.github.czxttkl.game.Challenge;
import io.github.czxttkl.game.ChallengeActivity;
import io.github.czxttkl.game.ChallengeLab;
import io.github.czxttkl.game.join.JoinActivity;
import io.github.czxttkl.game.progress.ProgressActivity;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainScreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.mainscreen);
	}

	public void onClickNew(View v) {
		final CharSequence[] items = { "New Challenge", "Upload My Progress" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Make your selection");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Challenge crime = new Challenge();
					ChallengeLab.get(MainScreenActivity.this).addCrime(crime);
					Intent i = new Intent();
					i.setComponent(new ComponentName(MainScreenActivity.this, ChallengeActivity.class));
					startActivity(i);
				} else {
					Intent i = new Intent();
					i.setComponent(new ComponentName(MainScreenActivity.this, ProgressActivity.class));
					startActivity(i);
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

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
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, ProgressActivity.class));
		startActivity(i);
	}
}