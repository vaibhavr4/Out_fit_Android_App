package com.vaibhav.out_fit;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class FriendsListAdapterItem {

    String friendsName;

    public FriendsListAdapterItem(String friendsName) {
        this.friendsName = friendsName;
    }

    public String getFriendsName() {
        return friendsName;
    }

    public void setFriendsName(String friendsName) {
        this.friendsName = friendsName;
    }
}
