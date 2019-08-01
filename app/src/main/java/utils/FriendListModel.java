package utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class FriendListModel {
    public FriendListModel()
    {
        //default constructor
    }

    public FriendListModel(Map<String, ArrayList<String>> friendList) {
        this.friendList = friendList;
    }

    public Map<String, ArrayList<String>> getFriendList() {
        return friendList;
    }

    public void setFriendList(Map<String, ArrayList<String>> friendList) {
        this.friendList = friendList;
    }

    public Map<String, ArrayList<String>> friendList;

}
