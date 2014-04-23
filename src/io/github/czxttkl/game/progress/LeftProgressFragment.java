package io.github.czxttkl.game.progress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.czxttkl.game.create.ChallengeActivity;
import io.github.czxttkl.game.create.ChallengeFragment;
import io.github.czxttkl.game.help.HelpViewpager;
import io.github.czxttkl.game.mainscreen.MainScreenActivity;
import io.github.czxttkl.game.model.Challenge;
import io.github.czxttkl.game.model.ChallengeLab;
import io.github.czxttkl.game.model.Photo;
import io.github.czxttkl.game.model.PictureUtils;

import com.actionbarsherlock.sample.shakespeare.R;
import com.actionbarsherlock.sample.shakespeare.Shakespeare;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class LeftProgressFragment extends ListFragment {
	private static final String TAG = "LeftProgressFragment";
	boolean mHasDetailsFrame;
	int mPositionChecked = 0;
	int mPositionShown = -1;
	int mType = 0;

	private ArrayList<Challenge> mChallenges;
	static ChallengeAdapter mAdapter;
	
	CheckBox mPhysical;
	CheckBox mMental;
	CheckBox mSocial;

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

			int i = c.getType();
			ImageView colorBar = (ImageView) convertView
					.findViewById(R.id.colorbar);

			if (i == 1) {
				colorBar.setImageResource(R.drawable.physical);

			} else if (i == 2) {
				colorBar.setImageResource(R.drawable.mental);

			} else if (i == 3) {
				colorBar.setImageResource(R.drawable.social);

			}

			ImageView flagImageView = (ImageView) convertView
					.findViewById(R.id.flag);
			
			if (c.getDefault()) {
				Bitmap bitmap = null;
				int numOfImage = c.getNumOfImage();
				switch (numOfImage) {
				case 1:
					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag1);
					break;
				case 2:
					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag2);
					break;
				case 3:
					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag3);
					break;
				case 4:
					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag4);
					break;
				case 5:
					bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flag5);
					break;

				default:
					break;
				}
				BitmapDrawable drawable = new BitmapDrawable(bitmap);
				flagImageView.setImageDrawable(drawable);

			} else {
				Photo p = c.getPhoto();
				BitmapDrawable b = null;
				if (p != null) {
					String path = getActivity().getFileStreamPath(p.getFilename())
								.getAbsolutePath();
					b = PictureUtils.getScaledDrawable(getActivity(), path);
				}

				flagImageView.setImageDrawable(b);
			}


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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		
		Log.i(TAG, "just get called 0");

		mChallenges = ChallengeLab.get(getActivity()).getChallenges();
		mAdapter = new ChallengeAdapter(mChallenges);		
		setListAdapter(mAdapter);
		
		mPhysical = (CheckBox) getActivity().findViewById(R.id.filter_physical);
		mPhysical.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mMental.setChecked(false);
					mSocial.setChecked(false);	
					
					changeToPhysical();
				} else {
					changeToAll();
				}
			}
		});
		
		mMental = (CheckBox) getActivity().findViewById(R.id.filter_mental);
		mMental.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mPhysical.setChecked(false);
					mSocial.setChecked(false);
					
					changeToMental();
				} else {
					changeToAll();
				}
			}
		});
		
		mSocial = (CheckBox) getActivity().findViewById(R.id.filter_social);
		mSocial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((CheckBox) v).isChecked()) {
					mMental.setChecked(false);
					mPhysical.setChecked(false);
					
					changeToSocial();
				} else {
					changeToAll();
				}
			}
		});
		
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
		inflater.inflate(R.menu.help_on_menu, menu);
		inflater.inflate(R.menu.new_challenge_list, menu);
		inflater.inflate(R.menu.new_challenge_text, menu);
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
		case R.id.menu_new_challenge:
			newChallenge();
			return true;
		case R.id.menu_new_challenge_text:
			newChallenge();
			return true;
		case R.id.menu_help:
			Intent helpIntent = new Intent(getActivity(), HelpViewpager.class);
			helpIntent.putExtra("page", 1);
			startActivity(helpIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void newChallenge() {
		Challenge crime = new Challenge();
		ChallengeLab.get(getActivity()).addChallenge(crime);
		Intent i = new Intent(getActivity(), ChallengeActivity.class);
        i.putExtra(ChallengeFragment.EXTRA_CRIME_ID, crime.getId());
		startActivity(i);
		getActivity().finish();
	}
	
	private void changeToPhysical() {
		ArrayList<Challenge> physicalChallenges = ChallengeLab.get(getActivity()).getPhysicalChallenges();
		ChallengeAdapter physicalAdapter = new ChallengeAdapter(physicalChallenges);
		setListAdapter(physicalAdapter);
	}
	
	private void changeToMental() {
		ArrayList<Challenge> mentalChallenges = ChallengeLab.get(getActivity()).getMentalChallenges();
		ChallengeAdapter mentalAdapter = new ChallengeAdapter(mentalChallenges);
		setListAdapter(mentalAdapter);
	}
	
	private void changeToSocial() {
		ArrayList<Challenge> socialChallenges = ChallengeLab.get(getActivity()).getSocialChallenges();
		Log.i(TAG, "social size: " + socialChallenges.size());
		ChallengeAdapter socialAdapter = new ChallengeAdapter(socialChallenges);
		setListAdapter(socialAdapter);
	}
	
	private void changeToAll() {
		ArrayList<Challenge> mChallenges = ChallengeLab.get(getActivity()).getChallenges();
		ChallengeAdapter allAdapter = new ChallengeAdapter(mChallenges);
		setListAdapter(allAdapter);
	}

}
