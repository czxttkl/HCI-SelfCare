package io.github.czxttkl.game.progress;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.actionbarsherlock.sample.shakespeare.R;

public class ProgressActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTitle(R.string.activity_titles);
		setContentView(R.layout.progress);
	}

	public void onClickUpdate(View v) {

	}
}
