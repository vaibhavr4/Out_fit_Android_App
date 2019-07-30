package utils;

import java.util.ArrayList;
import java.util.List;

public class UserSportsModel {

    public UserSportsModel(String userId, ArrayList<String> sports) {
        this.userId = userId;
        this.sports = sports;
    }

    public UserSportsModel()
    {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(ArrayList<String> sports) {
        this.sports = sports;
    }

    String userId;
    ArrayList<String> sports;
}
