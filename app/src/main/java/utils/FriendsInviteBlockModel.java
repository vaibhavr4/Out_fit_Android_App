package utils;

public class FriendsInviteBlockModel {


    public FriendsInviteBlockModel(String senderId, String receiverName, String receiverId, String sport) {
        this.senderId = senderId;
        this.receiverName = receiverName;
        this.receiverId = receiverId;
        this.sport = sport;
    }

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




    public FriendsInviteBlockModel()
    {
        //default constructor
    }

}
