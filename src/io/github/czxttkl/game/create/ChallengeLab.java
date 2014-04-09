package io.github.czxttkl.game.create;

import java.util.ArrayList;
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
			Log.e(TAG, "Error loading crimes: ", e);
		}
	}

	public static ChallengeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new ChallengeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}

	public Challenge getCrime(UUID id) {
		for (Challenge c : mChallenges) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}

	public void addCrime(Challenge c) {
		mChallenges.add(c);
		saveCrimes();
	}

	public ArrayList<Challenge> getCrimes() {
		return mChallenges;
	}

	public void deleteCrime(Challenge c) {
		mChallenges.remove(c);
		saveCrimes();
	}

	public boolean saveCrimes() {
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
