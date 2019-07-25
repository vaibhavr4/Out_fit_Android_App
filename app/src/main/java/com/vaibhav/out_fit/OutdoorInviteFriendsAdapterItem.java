package com.vaibhav.out_fit;

import androidx.appcompat.app.AppCompatActivity;

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
