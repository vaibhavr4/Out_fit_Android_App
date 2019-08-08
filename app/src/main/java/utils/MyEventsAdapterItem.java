package utils;

import android.content.Context;

public class MyEventsAdapterItem {
    public MyEventsAdapterItem(String events) {
        this.events = events;
    }

    public MyEventsAdapterItem()
    {
        //default
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    String events;

}
