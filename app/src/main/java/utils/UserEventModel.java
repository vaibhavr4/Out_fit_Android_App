package utils;

import java.util.ArrayList;

public class UserEventModel {
    public UserEventModel(ArrayList<String> userEvent) {
        this.userEvent = userEvent;
    }

    public UserEventModel()
    {
        //default
    }

    public ArrayList<String> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(ArrayList<String> userEvent) {
        this.userEvent = userEvent;
    }

    ArrayList<String> userEvent = new ArrayList();

}
