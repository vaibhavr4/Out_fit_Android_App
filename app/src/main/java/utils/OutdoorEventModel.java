package utils;

import java.util.ArrayList;

public class OutdoorEventModel {
    public OutdoorEventModel(String creatorId, String creatorName, String eventDescription, String date, String time, String location, String sport, ArrayList<String> friendJoined) {
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.eventDescription = eventDescription;
        this.date = date;
        this.time = time;
        this.location = location;
        this.sport = sport;
        this.friendsJoined = friendJoined;
    }

    public OutdoorEventModel()
    {
        //default
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public ArrayList<String> getFriendsJoined() {
        return friendsJoined;
    }

    public void setFriendsJoined(ArrayList<String> friendsJoined) {
        this.friendsJoined = friendsJoined;
    }

    String creatorId;
   String creatorName;
   String eventDescription;
   String date;
   String time;
   String location;
   String sport;
   ArrayList<String> friendsJoined = new ArrayList();

}
