package io.github.czxttkl.game.progress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.czxttkl.game.create.Challenge;
import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.create.ChallengeLab;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;

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
import android.widget.SimpleAdapter;

public class LeftProgressFragment extends ListFragment {
	boolean mHasDetailsFrame;
	int mPositionChecked = 0;
	int mPositionShown = -1;

	// Array of strings storing country names
	String[] names = new String[] { "Workout Hard", "Read books every day", "Let's meditation", "Yoga yoga!",
			"Party every weekend", };

	// Array of integers points to images stored in /res/drawable/
	int[] chllgprofiles = new int[] { R.drawable.flag1, R.drawable.flag2, R.drawable.flag3, R.drawable.flag4,
			R.drawable.flag5, };

	// Array of strings to store currencies
	String[] descriptions = new String[] { "Workout hard every 3 days", "Pakistani Rupee", "Sri Lankan Rupee",
			"Renminbi", "Bangladeshi Taka", };
	// Colors
	int[] colorbars = new int[] {R.drawable.physical, R.drawable.mental, R.drawable.mental,R.drawable.physical,R.drawable.social};

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showDetails(position);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		// Each row in the list stores country name, currency and flag
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < 5; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("txt", names[i]);
			hm.put("cur", descriptions[i]);
			hm.put("flag", Integer.toString(chllgprofiles[i]));
			hm.put("colorbar", Integer.toString(colorbars[i]));
			aList.add(hm);
		}

		// Keys used in Hashmap
		String[] from = { "flag", "txt", "cur","colorbar" };

		// Ids of views in listview_layout
		int[] to = { R.id.flag, R.id.txt, R.id.cur, R.id.colorbar };

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.chllg_listview, from,
				to);
		
		// Populate list with our static array of titles.
		setListAdapter(adapter);

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
	 * Helper function to show the details of a selected item, either by displaying a fragment in-place in the current
	 * UI, or starting a whole new activity in which it is displayed.
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
				RightProgressFragment df = RightProgressFragment.newInstance(index);

				// Execute a transaction, replacing any existing fragment
				// with this one inside the frame.
				getFragmentManager().beginTransaction().replace(R.id.frame_details, df)
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

				mPositionShown = index;
			}

		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			intent.setClass(getActivity(), ProgressDetailActivity.class);
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
			ChallengeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), ChallengeActivity.class);
			startActivity(i);
			getActivity().finish();
			return true;
		case R.id.menu_help:
			Intent helpIntent = new Intent(getActivity(), HelpViewpager.class);
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
