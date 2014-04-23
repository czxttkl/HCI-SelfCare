package io.github.czxttkl.game.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.util.Log;

public class ChallengeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "challenges.json";

	private ArrayList<Challenge> mChallenges;
	private ArrayList<Challenge> mPhysicalChallenges;
	private ArrayList<Challenge> mMentalChallenges;
	private ArrayList<Challenge> mSocialChallenges;
	
	private ChallengeIntentJSONSerializer mSerializer;

	private static ChallengeLab sCrimeLab = null;
	private Context mAppContext;

	private ChallengeLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new ChallengeIntentJSONSerializer(mAppContext, FILENAME);
//
//		try {
//			Log.i(TAG, "just get called loading");
//
//			mChallenges = mSerializer.loadChallenges();
//		} catch (Exception e) {
			mChallenges = new ArrayList<Challenge>();
			
			Log.i(TAG, "just get called 1");

			// default items in the list
			createDefaultChallenge();

//			Log.e(TAG, "Error loading crimes: ", e);
//		}
	}

	public static ChallengeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new ChallengeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}

	public Challenge getChallenge(UUID id) {
		for (Challenge c : mChallenges) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}

	public void addChallenge(Challenge c) {
		mChallenges.add(c);
		saveChallenges();
	}

	public ArrayList<Challenge> getChallenges() {
		return mChallenges;
	}
	
	public ArrayList<Challenge> getPhysicalChallenges() {
		return mPhysicalChallenges;
	}
	
	public ArrayList<Challenge> getMentalChallenges() {
		return mMentalChallenges;
	}
	
	public ArrayList<Challenge> getSocialChallenges() {
		return mSocialChallenges;
	}
	public void deleteChallenge(Challenge c) {
		mChallenges.remove(c);
		saveChallenges();
	}

	public boolean saveChallenges() {
		try {
			mSerializer.saveCrimes(mChallenges);
			Log.d(TAG, "crimes saved to file");
			
			updatePhysical();
			updateMental();
			updateSocial();
			
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: " + e);
			return false;
		}
	}
	
	String[] defalutTitles = new String[] { "Workout Hard", "Read books every day", "Let's meditation", "Yoga yoga!",
			"Party every weekend", };

	// Array of integers points to images stored in /res/drawable/
	int[] defaultTypes = new int[] {1, 2, 2, 1, 3 };

	// Array of strings to store currencies
	String[] defaultDescriptions = new String[] { "Workout hard every 3 days", "Love this book!", "Let's relax more!",
			"Get more health!", "Enjoy the party!", };
	
	String[] defaultPhotos = new String[] {"flag1.jpg", "flag2.jpg", "flag3.jpg", "flag4.jpg", "flag5.gif"};
	
	
	// Colors
	
	private void createDefaultChallenge() {
		Log.i(TAG, "just get called 2");

		for (int i = 0; i < 5; i++) {
			Challenge challenge = new Challenge();
			challenge.setTitle(defalutTitles[i]);
			challenge.setDetails(defaultDescriptions[i]);
			challenge.setDefault();
			challenge.setNumOfImage(i+1);
			
			switch (i) {
			case 0:
				challenge.setType(1);
				break;
			case 1:
				challenge.setType(2);
				break;
			case 2:
				challenge.setType(2);
				break;
			case 3:
				challenge.setType(1);
				break;
			case 4:
				challenge.setType(3);
				Log.i(TAG, "This is social");
				break;
			default:
				break;
			}
			
			addChallenge(challenge);
		}
		
		
	}
	
	void updatePhysical() {
		mPhysicalChallenges = new ArrayList<Challenge>();
		for (Challenge mChallenge : mChallenges) {
			if (mChallenge.getType() == 1) {
				mPhysicalChallenges.add(mChallenge);
			}
		}
	}
	void updateMental() {
		mMentalChallenges = new ArrayList<Challenge>();
		for (Challenge mChallenge : mChallenges) {
			if (mChallenge.getType() == 2) {
				mMentalChallenges.add(mChallenge);
			}
		}
	}
	void updateSocial() {
		mSocialChallenges = new ArrayList<Challenge>();
		for (Challenge mChallenge : mChallenges) {
			if (mChallenge.getType() == 3) {
				mSocialChallenges.add(mChallenge);
			}
		}
		
		Log.i(TAG, "social size: " + mSocialChallenges.size());
	}

}
