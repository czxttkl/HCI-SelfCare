package io.github.czxttkl.game.mainscreen;

import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.create.ChallengeFragment;
import io.github.czxttkl.game.graph.GraphActivity;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.join.JoinActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.progress.ProgressActivity;

import com.actionbarsherlock.sample.shakespeare.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainScreenActivity extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.help_on_menu, menu);
		getMenuInflater().inflate(R.menu.new_challenge_list, menu);
		getMenuInflater().inflate(R.menu.new_challenge_text, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_help:
			Intent helpIntent = new Intent(this, HelpViewpager.class);
			helpIntent.putExtra("page", 0);
			startActivity(helpIntent);
			return true;
		case R.id.menu_new_challenge:
			newChallenge();
			return true;
		case R.id.menu_new_challenge_text:
			newChallenge();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.mainscreen);
	}

	public void newChallenge() {
		Challenge crime = new Challenge();
		ChallengeLab.get(getApplicationContext()).addChallenge(crime);
		Intent i = new Intent();
        i.putExtra(ChallengeFragment.EXTRA_CRIME_ID, crime.getId());
		i.setComponent(new ComponentName(MainScreenActivity.this, ChallengeActivity.class));
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
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, ProgressActivity.class));
		startActivity(i);
	}
	
	public void onClickAvatar(View v) {
		Intent i = new Intent();
		i.setComponent(new ComponentName(this, GraphActivity.class));
		startActivity(i);
	}
}
