package io.github.czxttkl.game.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.Context;

import android.util.Log;

public class ChallengeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "challenges.json";

	private ArrayList<Challenge> mChallenges;
	private ChallengeIntentJSONSerializer mSerializer;

	private static ChallengeLab sCrimeLab;
	private Context mAppContext;

	private ChallengeLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new ChallengeIntentJSONSerializer(mAppContext, FILENAME);

		try {
			mChallenges = mSerializer.loadChallenges();
		} catch (Exception e) {
			mChallenges = new ArrayList<Challenge>();
			
			// default items in the list

			Log.e(TAG, "Error loading crimes: ", e);
		}
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

	public void deleteChallenge(Challenge c) {
		mChallenges.remove(c);
		saveChallenges();
	}

	public boolean saveChallenges() {
		try {
			mSerializer.saveCrimes(mChallenges);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: " + e);
			return false;
		}
	}
}
