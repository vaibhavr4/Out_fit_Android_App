package com.vaibhav.out_fit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OutdoorInviteFriendsAdapterItem {

    String friendsName;

    public OutdoorInviteFriendsAdapterItem(String friendsName) {
        this.friendsName = friendsName;
    }

    public String getFriendsName() {
        return friendsName;
    }

    public void setFriendsName(String friendsName) {
        this.friendsName = friendsName;
    }
}
