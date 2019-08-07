package utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

public class OutdoorInviteFriendsModel implements Parcelable {

    public OutdoorInviteFriendsModel(String senderId,String senderName,String receiverId, String receiverName, String sport, String eventId) {
        this.senderId = senderId;
        this.receiverName = receiverName;
        this.receiverId = receiverId;
        this.sport = sport;
        this.senderName = senderName;
        this.eventId = eventId;
    }

    public OutdoorInviteFriendsModel(String senderId,String senderName,String receiverId, String receiverName, String sport) {
        this.senderId = senderId;
        this.receiverName = receiverName;
        this.receiverId = receiverId;
        this.sport = sport;
        this.senderName = senderName;
    }

    public OutdoorInviteFriendsModel()
    {
        //default
    }

    protected OutdoorInviteFriendsModel(Parcel in) {
        senderId = in.readString();
        receiverName = in.readString();
        receiverId = in.readString();
        sport = in.readString();
        senderName = in.readString();
        eventId = in.readString();
    }

    public static final Creator<OutdoorInviteFriendsModel> CREATOR = new Creator<OutdoorInviteFriendsModel>() {
        @Override
        public OutdoorInviteFriendsModel createFromParcel(Parcel in) {
            return new OutdoorInviteFriendsModel(in);
        }

        @Override
        public OutdoorInviteFriendsModel[] newArray(int size) {
            return new OutdoorInviteFriendsModel[size];
        }
    };

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    String senderId;
    String receiverName;
    String receiverId;
    String sport;
    String senderName;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    String eventId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(senderId);
        parcel.writeString(receiverName);
        parcel.writeString(receiverId);
        parcel.writeString(sport);
        parcel.writeString(senderName);
        parcel.writeString(eventId);
    }
}
