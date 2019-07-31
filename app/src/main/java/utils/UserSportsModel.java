package utils;

import java.util.ArrayList;
import java.util.List;

public class UserSportsModel {

    public UserSportsModel( ArrayList<String> sports) {
        this.sports = sports;
    }

    public UserSportsModel()
    {

    }



    public ArrayList<String> getSports() {
        return sports;
    }

    public void setSports(ArrayList<String> sports) {
        this.sports = sports;
    }

    ArrayList<String> sports;
}
