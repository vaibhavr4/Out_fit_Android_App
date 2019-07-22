package utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.vaibhav.out_fit.R;

import java.util.ArrayList;

public class FriendsListAdapterItem extends BaseAdapter {
    public FriendsListAdapterItem(Context context, ArrayList<String> friendItems) {
        this.context = context;
        this.friendItems = friendItems;
    }

    //Receive the context from main activity
    Context context;

    //ArrayList with the data points that are to be populated on my items that I'm creating
    ArrayList<String> friendItems;



    @Override
    public int getCount() {
        return friendItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //Create and return the view

        if(view == null) {
            view = View.inflate(context, R.layout.friend_request_adapter_item, null);

        }
        // Grab the child view from your root view(Linear Layout)

        TextView textView = view.findViewById(R.id.friendName);

        // Overwrite the values of the child views -> Based on the input I'm getting from the Mainactivity

        textView.setText(friendItems.get(i));


        return view;
    }


}
