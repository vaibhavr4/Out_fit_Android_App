package utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.vaibhav.out_fit.R;

import java.util.ArrayList;

public class EventRequestListAdapterItem extends BaseAdapter {
    Context context;
    private DatabaseReference dbChildReference;
    private FirebaseFirestore db;
    private ArrayList<DataSnapshot> dataSnapshotResultsFromDB;
    ArrayList<String> keysDataSnapshot = new ArrayList();


    public EventRequestListAdapterItem(Context context, DatabaseReference dbChildReference)
    {
        this.context = context;
        this.dbChildReference = dbChildReference;
        this.dbChildReference.addChildEventListener(dbChildListener);

        dataSnapshotResultsFromDB = new ArrayList();
        db = FirebaseFirestore.getInstance();
    }


    ChildEventListener dbChildListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            dataSnapshotResultsFromDB.add(dataSnapshot);
            keysDataSnapshot.add(dataSnapshot.getKey());
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            int index = keysDataSnapshot.indexOf(dataSnapshot.getKey());
            dataSnapshotResultsFromDB.remove(index);
            keysDataSnapshot.remove(index);
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };




    @Override
    public int getCount() {
        return dataSnapshotResultsFromDB.size();
    }

    @Override
    public OutdoorInviteFriendsModel getItem(int i) {
        DataSnapshot dataSnapshotItemFromList = dataSnapshotResultsFromDB.get(i);
        return dataSnapshotItemFromList.getValue(OutdoorInviteFriendsModel.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final OutdoorInviteFriendsModel outdoorInviteFriendsModel = getItem(i);

        if(view == null) {

            view = View.inflate(context, R.layout.event_request_adapter_item, null);
            final TextView eventDesc = view.findViewById(R.id.eventRequestDesc);
            final ImageView acceptEvent = view.findViewById(R.id.eventRequestConfirm);
            final ImageView rejectEvent = view.findViewById(R.id.eventRequestReject);

            populateEvents(new EventRequestCallback() {
                @Override
                public void OnCallback(String event) {
                    Log.d("EventRequest", "Event Desc: " + event);
                    eventDesc.setText(event);
                }
            }, outdoorInviteFriendsModel.getEventId());


            acceptEvent.setClickable(true);
            acceptEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //get reference to check if any events exist in database for user to append
                    final DocumentReference documentReference = db.collection("user_events")
                            .document(outdoorInviteFriendsModel.receiverId);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    UserEventModel userEventModel = documentSnapshot.toObject(UserEventModel.class);
                                    if (userEventModel != null) {
                                        userEventModel.getUserEvent().add(outdoorInviteFriendsModel.getEventId());
                                    }
                                    documentReference.set(userEventModel);
                                } else {
                                    //if no events exist for the user create an event
                                    ArrayList<String> tempEventList = new ArrayList<>();
                                    tempEventList.add(outdoorInviteFriendsModel.getEventId());
                                    final UserEventModel userEventModel = new UserEventModel();
                                    userEventModel.setUserEvent(tempEventList);
                                    documentReference.set(userEventModel);
                                }
                            }

                        }
                    });

//----------------------add user to accepted invites in events--------------------------------------
                    final DocumentReference eventDocumentReference = db.collection("events")
                            .document(outdoorInviteFriendsModel.eventId);

                    eventDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    OutdoorEventModel outdoorEventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                                    if (outdoorEventModel != null) {
                                        if (outdoorEventModel.getFriendsJoined() != null)
                                            outdoorEventModel.getFriendsJoined().add(outdoorInviteFriendsModel.getReceiverId());
                                        else {
                                            ArrayList<String> friendsAccepted = new ArrayList();
                                            friendsAccepted.add(outdoorInviteFriendsModel.getReceiverId());
                                        }
                                        eventDocumentReference.set(outdoorEventModel);
                                    }
                                }
                            }
                        }
                    });

//-----------------------------------remove event request from realtime db---------------------------------

                    Query realTimeEventQuery = dbChildReference.orderByChild("eventId").equalTo(outdoorInviteFriendsModel.eventId);
                    realTimeEventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                eventSnapshot.getRef().removeValue();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

//----------------------------reject event---------------------------------------------------------

            rejectEvent.setClickable(true);
            rejectEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Query realTimeEventQuery = dbChildReference.orderByChild("eventId").equalTo(outdoorInviteFriendsModel.eventId);
                    realTimeEventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                                eventSnapshot.getRef().removeValue();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });


        }

        return view;
    }





//-----------------------Get event from Id---------------------------------------------------
private void populateEvents(final EventRequestCallback callback,String eventId){
    Log.d("EventRequest","EventID"+eventId);
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    db = FirebaseFirestore.getInstance();
    final String currentUserId = currentFirebaseUser.getUid();
    final DocumentReference eventList = db.collection("events").document(eventId);
    Log.d("EventRequest",eventList.toString());
    eventList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            if (documentSnapshot.exists()) {
                OutdoorEventModel outdoorEventModel = documentSnapshot.toObject(OutdoorEventModel.class);
                if(outdoorEventModel!=null)
                {
                    callback.OnCallback(outdoorEventModel.getEventDescription());
                }

            }

        }
    });

}

//-----------------------------------get event from id end------------------------------------------


}

interface EventRequestCallback{
    void OnCallback(String event);
}


