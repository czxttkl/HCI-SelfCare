package io.github.czxttkl.game.graph;

import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.progress.ProgressActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.sample.shakespeare.R;

public class GraphActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.graph);
	}
	
	public void onClickNew(View v) {
		final CharSequence[] items = { "New Challenge", "Upload My Progress" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Make your selection");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				if (item == 0) {
					Challenge crime = new Challenge();
					ChallengeLab.get(GraphActivity.this).addChallenge(crime);
					Intent i = new Intent();
					i.setComponent(new ComponentName(GraphActivity.this, ChallengeActivity.class));
					startActivity(i);
				} else {
					Intent i = new Intent();
					i.setComponent(new ComponentName(GraphActivity.this, ProgressActivity.class));
					startActivity(i);
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
