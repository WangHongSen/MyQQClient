package bean;

import java.io.Serializable;

public class Friend implements Serializable {
    private int id;
    private int selfAccount; //自己账号
    private int friendsAccount;//朋友账号
    private String groupname;//所在分组

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

    public int getFriendsAccount() {
        return friendsAccount;
    }

    public void setFriendsAccount(int friendsAccount) {
        this.friendsAccount = friendsAccount;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public int getInvalid() {
        return invalid;
    }

    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }

    private int invalid; //是否在黑名单
}
