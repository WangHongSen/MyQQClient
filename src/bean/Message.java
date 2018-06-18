package bean;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private int selfAccount; //自己账号
    private int friendAccount;
    private String msgcontent;  //最长一百四

    public Message(int selfAccount, int friendAccount, String msgcontent) {
        this.selfAccount = selfAccount;
        this.friendAccount = friendAccount;
        this.msgcontent = msgcontent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSelfAccount() {
        return selfAccount;
    }

    public void setSelfAccount(int selfAccount) {
        this.selfAccount = selfAccount;
    }

    public int getFriendAccount() {
        return friendAccount;
    }

    public void setFriendAccount(int friendAccount) {
        this.friendAccount = friendAccount;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }
}
