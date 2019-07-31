package com.vaibhav.out_fit;

public class FriendsInviteBlockAdapterItem {

    String friendsName;
    String friendID;
    String sport;

    public FriendsInviteBlockAdapterItem(String friendsName, String friendID, String sport) {
        this.friendsName = friendsName;
        this.friendID = friendID;
        this.sport = sport;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public String getFriendsName() {
        return friendsName;
    }

    public void setFriendsName(String friendsName) {
        this.friendsName = friendsName;
    }
}
