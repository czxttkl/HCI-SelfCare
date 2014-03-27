package io.github.czxttkl.game;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class ChallengeActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent()
            .getSerializableExtra(ChallengeFragment.EXTRA_CRIME_ID);
        return ChallengeFragment.newInstance(crimeId);
    }
}
