package io.github.czxttkl.game.create;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

import android.util.Log;

public class ChallengeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";

	private ArrayList<Challenge> mCrimes;
	private ChallengeIntentJSONSerializer mSerializer;

	private static ChallengeLab sCrimeLab;
	private Context mAppContext;

	private ChallengeLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new ChallengeIntentJSONSerializer(mAppContext, FILENAME);

		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<Challenge>();
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
		for (Challenge c : mCrimes) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}

	public void addCrime(Challenge c) {
		mCrimes.add(c);
		saveCrimes();
	}

	public ArrayList<Challenge> getCrimes() {
		return mCrimes;
	}

	public void deleteCrime(Challenge c) {
		mCrimes.remove(c);
		saveCrimes();
	}

	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: " + e);
			return false;
		}
	}
}
