package com.actionbarsherlock.sample.shakespeare.fragments;

import io.github.czxttkl.game.Challenge;
import io.github.czxttkl.game.ChallengeActivity;
import io.github.czxttkl.game.ChallengeLab;
import io.github.czxttkl.game.MainScreenActivity;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;
import com.actionbarsherlock.sample.shakespeare.activities.DetailsActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LeftFragment extends ListFragment {
	boolean mHasDetailsFrame;
	int mPositionChecked = 0;
	int mPositionShown = -1;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);

		// Populate list with our static array of titles.
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, Shakespeare.TITLES));

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

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("curChoice", mPositionChecked);
		outState.putInt("shownChoice", mPositionShown);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
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
				RightFragment df = RightFragment.newInstance(index);

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
			intent.setClass(getActivity(), DetailsActivity.class);
			intent.putExtra("index", index);
			startActivity(intent);
		}
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_challenge_list, menu);
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_challenge:
			Challenge crime = new Challenge();
			ChallengeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), ChallengeActivity.class);
			startActivity(i);
			getActivity().finish();
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
