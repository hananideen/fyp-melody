package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Trackings;

/**
 * Created by Hananideen on 2/9/2015.
 */
public class Tracking {

    public String Status;
    public int Order_ID;

    public Tracking() {}

    public Tracking (String statusTracking, int order_ID){
        Status = statusTracking;
        Order_ID = order_ID;
    }

    public Tracking(Json2Trackings jTrack) {
        Status = jTrack.status;
        Order_ID = jTrack.id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus (String status) {
        Status = status;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int orderId) {
        Order_ID = orderId;
    }
}
