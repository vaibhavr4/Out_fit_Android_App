package com.vaibhav.out_fit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import utils.UserModel;
import utils.UserSportsModel;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OutdoorTabFragment extends Fragment {
    private GridView gridView;
    private View btnGo;
    String[] itemsName;
    private String selectedString="";
    String selectedItem;
    TextView GridViewItems,BackSelectedItem;
    int backposition = -1;
    ArrayList<String> selectedSports = new ArrayList();

    Activity activity = getActivity();
    FirebaseFirestore db;
    UserSportsModel userSportsModel;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    final String currentUserId = currentFirebaseUser.getUid();

public OutdoorTabFragment(){}
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();

        db.collection("user_sports")
                .document(currentUserId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userSportsModel = documentSnapshot.toObject(UserSportsModel.class);
                        selectedSports = userSportsModel.getSports();
                        itemsName = selectedSports.toArray(new String[0]);
                        gridView = (GridView) view.findViewById(R.id.outdoorSportsGrid);
                        btnGo = view.findViewById(R.id.outdoorSportsButton);
                        gridView.setAdapter(new TextAdapter(getActivity()));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Profile not invalid");
                    }
                });

        itemsName = selectedSports.toArray(new String[0]);
        gridView = (GridView) view.findViewById(R.id.outdoorSportsGrid);
        btnGo = view.findViewById(R.id.outdoorSportsButton);
        btnGo.setEnabled(false);
        gridView.setAdapter(new TextAdapter(getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                selectedItem = parent.getItemAtPosition(position).toString();
                GridViewItems = (TextView) view;
                GridViewItems.setBackgroundColor(Color.parseColor("#F55303"));
                GridViewItems.setTextColor(Color.parseColor("#FFFFFF"));
                BackSelectedItem = (TextView) gridView.getChildAt(backposition);
                if (backposition != -1)
                {
                    BackSelectedItem.setSelected(false);
                    BackSelectedItem.setBackgroundColor(Color.parseColor("#000000"));
                    BackSelectedItem.setTextColor(Color.parseColor("#FFFFFF"));
                }
                backposition = position;
                selectedString = itemsName[backposition];
                btnGo.setEnabled(true);
            }
        });

        //set listener for Button event
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SportLocationActivity.class);
                intent.putExtra("SPORT", selectedString);
                startActivity(intent);
            }
        });

    }

    public class TextAdapter extends BaseAdapter
    {
        Context context;

        public TextAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsName.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemsName[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView text = new TextView(this.context);
            text.setText(itemsName[position]);
            text.setGravity(Gravity.CENTER);
            text.setBackgroundColor(Color.parseColor("#000000"));
            text.setTextColor(Color.parseColor("#FFFFFF"));
            text.setLayoutParams(new GridView.LayoutParams(300, 300));
            return text;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_outdoor_layout, container, false);
    }
}
