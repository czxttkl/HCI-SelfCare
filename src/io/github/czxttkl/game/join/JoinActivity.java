package io.github.czxttkl.game.join;

import io.github.czxttkl.game.progress.ProgressActivity;
import io.github.czxttkl.game.progress.UpdateProgress;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.sample.shakespeare.R;

public class JoinActivity extends FragmentActivity {
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
