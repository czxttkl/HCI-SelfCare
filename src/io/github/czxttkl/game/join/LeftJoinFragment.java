package io.github.czxttkl.game.join;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.model.Photo;
import io.github.czxttkl.game.model.PictureUtils;
import io.github.czxttkl.game.progress.ProgressDetailActivity;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LeftJoinFragment extends ListFragment {
	
	private static final String TAG = "LeftJoinFragment";
	boolean mHasDetailsFrame;
	int mPositionChecked = 0;
	int mPositionShown = -1;

	private ArrayList<Challenge> mChallenges;
	static ChallengeAdapter mAdapter;

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
	}

	class ChallengeAdapter extends ArrayAdapter<Challenge> {

		public ChallengeAdapter(ArrayList<Challenge> challenges) {
			super(getActivity(), 0, challenges);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_challenge, null);
			}

			Challenge c = getItem(position);

			Random rand = new Random();

			int i = rand.nextInt(3) + 1;
			
			Log.i(TAG, "The random number is: " + i);

			ImageView colorBar = (ImageView) convertView
					.findViewById(R.id.colorbar);

			if (i == 0) {
				colorBar.setImageResource(R.drawable.mental);

			} else if (i == 1) {
				colorBar.setImageResource(R.drawable.physical);

			} else if (i == 2) {
				colorBar.setImageResource(R.drawable.social);

			}

			ImageView flagImageView = (ImageView) convertView
					.findViewById(R.id.flag);
			Photo p = c.getPhoto();
			BitmapDrawable b = null;
			if (p != null) {
				String path = getActivity().getFileStreamPath(p.getFilename())
						.getAbsolutePath();
				b = PictureUtils.getScaledDrawable(getActivity(), path);
			}

			flagImageView.setImageDrawable(b);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.txt);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.cur);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentDateandTime = sdf.format(c.getStartDate());

			dateTextView.setText(currentDateandTime);

			return convertView;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		mChallenges = ChallengeLab.get(getActivity()).getChallenges();
		mAdapter = new ChallengeAdapter(mChallenges);

		// Populate list with our static array of titles.
		setListAdapter(mAdapter);

		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.frame_details);
		mHasDetailsFrame = (detailsFrame != null)
				&& (detailsFrame.getVisibility() == View.VISIBLE);

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mPositionChecked = savedInstanceState.getInt("curChoice", 0);
			mPositionShown = savedInstanceState.getInt("shownChoice", -1);
		}

		if (mHasDetailsFrame) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(mPositionChecked);
		}
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	void showDetails(int index) {
		mPositionChecked = index;

		if (mHasDetailsFrame) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			if (mPositionShown != mPositionChecked) {
				// If we are not currently showing a fragment for the new
				// position, we need to create and install a new one.
				RightJoinFragment df = RightJoinFragment.newInstance(index);

				// Execute a transaction, replacing any existing fragment
				// with this one inside the frame.
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.frame_details, df)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.commit();

				mPositionShown = index;
			}

		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			intent.setClass(getActivity(), JoinDetailActivity.class);
			intent.putExtra("index", index);
			startActivity(intent);
		}
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.new_challenge_list, menu);
		inflater.inflate(R.menu.help_on_menu, menu);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mPositionChecked);
		outState.putInt("shownChoice", mPositionShown);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_challenge:
			Challenge crime = new Challenge();
			ChallengeLab.get(getActivity()).addChallenge(crime);
			Intent i = new Intent(getActivity(), ChallengeActivity.class);
			startActivity(i);
			getActivity().finish();
			return true;
		case R.id.menu_help:
			Intent helpIntent = new Intent(getActivity(), HelpViewpager.class);
			helpIntent.putExtra("page", 2);
			startActivity(helpIntent);
			return true;
		case android.R.id.home:
			Intent intent = new Intent(getActivity(), MainScreenActivity.class);
			startActivity(intent);
			getActivity().finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
