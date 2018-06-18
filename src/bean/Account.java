package bean;

import java.io.Serializable;

public class Account implements Serializable {
    private int  qqCode;
    private String pwd;
    private int age;
    private String sex;

    public Account(int qqCode, String nickName) {
        this.qqCode = qqCode;
        this.nickName = nickName;
    }
    public Account(){

    }

    private int port;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String ipAddr;
    private int statues;//0离线，1在线，2隐身，3忙碌
    private String nickName;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }





    public int getStatues() {
        return statues;
    }

    public void setStatues(int statues) {
        this.statues = statues;
    }


    public int getQqCode() {
        return qqCode;
    }

    public void setQqCode(int qqCode) {
        this.qqCode = qqCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String toString(){
        return nickName+"("+qqCode+")";
    }
}

