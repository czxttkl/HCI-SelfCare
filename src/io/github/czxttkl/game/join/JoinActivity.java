package io.github.czxttkl.game.join;

import java.util.ArrayList;

import io.github.czxttkl.game.join.LeftJoinFragment.ChallengeAdapter;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.progress.ProgressActivity;
import io.github.czxttkl.game.progress.UpdateProgress;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.sample.shakespeare.R;

public class JoinActivity extends FragmentActivity {
	
	private int preferredType = 0;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.join);
	}

	public void onClickJoin(View v) {
		Intent i = new Intent(this, ProgressActivity.class);
		startActivity(i);
		this.finish();
		Toast toast = Toast.makeText(getBaseContext(), "The activity is added to your list.", Toast.LENGTH_LONG);
		toast.show();
	}
}
