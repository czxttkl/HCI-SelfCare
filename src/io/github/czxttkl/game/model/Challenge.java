package io.github.czxttkl.game.model;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.menu;
import android.util.Log;

public class Challenge {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_START_DATE = "startdate";
    private static final String JSON_END_DATE = "enddate";
    private static final String JSON_PROGRESS = "progress";
    private static final String JSON_FREQUNCE = "freq";
    private static final String JSON_LOCATION = "location";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_DETAILS = "details";
    
    private UUID mId;
    private String mTitle;
    private Date mStartDate;
    private Date mEndDate;
    private String mFreqString;
    private int mProgress;
    private String mLocation;
    private Photo mPhoto;
    private String mDetails;
    
    public Challenge() {
        mId = UUID.randomUUID();
        mStartDate = new Date();
        mEndDate = new Date();
    }

    public Challenge(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mStartDate = new Date(json.getLong(JSON_START_DATE));
        mEndDate = new Date(json.getLong(JSON_END_DATE));
        mFreqString = json.getString(JSON_FREQUNCE);
        mProgress = json.getInt(JSON_PROGRESS);
        mLocation = json.getString(JSON_LOCATION);
        mDetails = json.getString(JSON_DETAILS);
       
        
        if (json.has(JSON_PHOTO))
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_START_DATE, mStartDate.getTime());
        json.put(JSON_END_DATE, mEndDate.getTime());
        json.put(JSON_FREQUNCE, mFreqString);
        json.put(JSON_PROGRESS, mProgress);
        json.put(JSON_LOCATION, mLocation);
        json.put(JSON_DETAILS, mDetails);
        
        if (mPhoto != null)
            json.put(JSON_PHOTO, mPhoto.toJSON());
        return json;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    
    public String getFreq() {
    	return mFreqString;
    }
    
    public void setFreq(String newFreq) {
    	mFreqString = newFreq;
    }
    
    public void setProgress(int newProgress) {
    	mProgress = newProgress;
    }
    
    public int getProgress() {
    	return mProgress;
    }

    public UUID getId() {
        return mId;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date date) {
        mStartDate = date;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date date) {
        mEndDate = date;
    }
    
	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo photo) {
		mPhoto = photo;
	}
    
	public void setLocation(String newLocation) {
		mLocation = newLocation;
	}
	
	public String getLocation() {
		return mLocation;
	}
	
	public void setDetails(String newDetails) {
		mDetails = newDetails;
	}
	
	public String getDetails() {
		return mDetails;
	}
}
