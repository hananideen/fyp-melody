package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Tracking;

/**
 * Created by Hananideen on 2/9/2015.
 */
public class Tracking {

    public String Status;

    public Tracking() {}

    public Tracking (String statusTracking){
        Status = statusTracking;
    }

    public Tracking(Json2Tracking jTrack) {
        Status = jTrack.status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus (String status) {
        Status = status;
    }
}
