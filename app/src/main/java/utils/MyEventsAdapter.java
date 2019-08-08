package utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vaibhav.out_fit.R;

import java.util.ArrayList;

public class MyEventsAdapter extends BaseAdapter {

    Context context;
    ArrayList<MyEventsAdapterItem> eventItems;
    private FirebaseFirestore db;
    String eventId;

    public MyEventsAdapter(Context context, ArrayList<MyEventsAdapterItem> eventItems)
    {
        this.context = context;
        this.eventItems = eventItems;

        db = FirebaseFirestore.getInstance();
    }

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public MyEventsAdapterItem getItem(int i) {
        return eventItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        MyEventsAdapterItem myEventsAdapterItem = getItem(i);
        eventId = myEventsAdapterItem.getEvents();

            if(view==null) {
                view = View.inflate(context, R.layout.my_events_adapter_item, null);


                final TextView textView = view.findViewById(R.id.eventAdapterItemDesc);


                populateEvents(new MyEventsCallback() {
                    @Override
                    public void OnCallback(String event) {
                        Log.d("EventRequest", "Event Desc: " + event);
                        textView.setText(event);
                    }
                });
            }




        return view;
    }


    //-----------------------Get event from Id---------------------------------------------------
    private void populateEvents(final MyEventsCallback callback){
        Log.d("EventRequestTest","EventID"+eventId);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        db = FirebaseFirestore.getInstance();
        final String currentUserId = currentFirebaseUser.getUid();
        final DocumentReference eventList = db.collection("events").document(eventId);
        Log.d("EventRequestTest",eventList.toString());
        eventList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    OutdoorEventModel outdoorEventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                    if(outdoorEventModel!=null)
                    {
                        Log.d("EventRequestTest",outdoorEventModel.getEventDescription().toString());
                        callback.OnCallback(outdoorEventModel.getEventDescription());
                    }

                }

            }
        });

    }

//-----------------------------------get event from id end------------------------------------------



}
interface MyEventsCallback{
    void OnCallback(String event);
}
