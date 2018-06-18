package utili;


import bean.Account;
import jdk.nashorn.api.tree.CompoundAssignmentTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

//服务器连接
public class Connection {
    //网络传输流
    private  DataOutputStream out = null;
    private DataInputStream in =null;
    //对象序列化流
    private ObjectInputStream oin;
    private ObjectOutputStream obout;
    //socket
    Socket server = null; //连接服务器的socket


    public Connection(){
         connectServer();
     }

     public Socket getSocket(){
        return server;
     }
     private  void connectServer(){
        //登陆
        int port = 0;
        String ip = null;
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try{
            //dom parser
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(new File("res/server.xml"));
            Element root = document.getDocumentElement();
            Node portE =root.getElementsByTagName("port").item(0);
            Node ipE = root.getElementsByTagName("ip").item(0);
            port = Integer.parseInt(portE.getTextContent());
            ip = ipE.getTextContent();
            server = new Socket(InetAddress.getByName(ip),port);
            if(server.isConnected())
                System.out.println("connect successfully");
            else
                System.out.println("failed");
          //  out = new DataOutputStream(client.getOutputStream());
           // in = new DataInputStream(client.getInputStream());
        }catch (ParserConfigurationException|SAXException|IOException e){
            //java 7特性一次处理多个异常
            e.printStackTrace();
        }
    }

    public void send(int command){
         try{
             if(server !=null){
                 out = new DataOutputStream(server.getOutputStream());
                 out.write(command);
             }
             else{
                 System.err.println("Class Connection: error can not connect");
             }
         }catch (IOException e){
             e.printStackTrace();
         }
    }



    public Vector<Account> login(Account acc) {
        try {
            obout = new ObjectOutputStream(server.getOutputStream());
            obout.writeInt(CMD.CMD_LOGIN);
            obout.writeObject(acc);

            oin = new ObjectInputStream(server.getInputStream());
            acc = (Account)oin.readObject();
       //     System.out.println(acc.toString());
            int cmd = oin.readInt();
            if(cmd == CMD.TRANSFER_OBJECT){
                Vector<Account> friends = new Vector<>();
                 int nums = oin.readInt();
                 for(int i=0;i<nums;i++ ){
                     friends.add((Account)oin.readObject());
                 }
                 friends.add(acc);
                 return friends;
            }
        }catch (IOException|ClassNotFoundException e ){
            e.printStackTrace();
        }
        return null;
    }
    public void requestKey(Account a,int port,Account b){
        if(server != null){
            try{
                obout = new ObjectOutputStream(server.getOutputStream());
                obout.writeInt(CMD.REQUEST_KEY);
                obout.writeObject(a);
                obout.writeInt(port);
                obout.writeObject(b);

            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    //用于注册等，发送对象的序列化.返回命令
    public void regist(Account acc){

        try{
          //  out = new ObjectOutputStream(client.getOutputStream());
            if(server !=null){
                obout = new ObjectOutputStream(server.getOutputStream());
                obout.writeInt(CMD.CMD_REGIST);
                obout.writeObject(acc);
                in = new DataInputStream(server.getInputStream());
                int sp=in.readInt();
                System.out.println(sp);

            }
            else{
                System.err.println("Class Connection: error can not connect");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
//收到一个Account对象，登陆时使用
    public int receive(Account acc){
        try{
            oin  = new ObjectInputStream(server.getInputStream());
            acc = (Account)oin.readObject();
            return in.readInt();
        }catch(IOException|ClassNotFoundException e){
            e.printStackTrace();
            return 0;
        }
    }
//添加好友，qq1将qq2加为好友
     public boolean addFriend(int qq1,int qq2){
        if(server !=null){
            try{
                obout = new ObjectOutputStream(server.getOutputStream());
                System.out.println(qq1+"  "+qq2);
                obout.writeInt(CMD.CMD_ADDFRIEND);
                obout.writeInt(qq1);
               obout.writeInt(qq2);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return true;
     }
//关闭流
      public boolean close(){
         try{
             if(in != null){
                 in.close();
             }
             if(out != null)
                 out.close();
             if(server != null)
                 server.close();
             return true;
         }catch(IOException e){
             System.err.println("close failed ！！！");
             e.printStackTrace();
             return false;
         }
      }


}
