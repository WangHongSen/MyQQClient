package utili;

import java.io.IOException;
import java.net.*;

public class ClientUDP  {
   private byte[] msg1 = new byte[1028];//接收
   private byte[] msg2 = new byte[1028];//发送
   private  DatagramSocket sendSocket = null;
   private  DatagramSocket receiveSocket = null;
   private  DatagramPacket receiveBuffer = new DatagramPacket(msg1,1028);
   private int port1,port2;
   private String ip = null;

   //port1自己要开放的端口号，port2朋友端口号
   public ClientUDP(int port1,String ip,int port2){
       this.port1 = port1;
       this.port2 = port2;
       this.ip = ip;
       try{
       sendSocket = new DatagramSocket();
       receiveSocket = new DatagramSocket(port1);

       }catch(SocketException e){
           e.printStackTrace();
       }

   }
   public void send(String msg){
       try {
            InetAddress addr = InetAddress.getByName(ip);
            DatagramPacket sendBuffer = new DatagramPacket(msg.getBytes(),msg.length(),addr,port2);
           sendSocket.send(sendBuffer);
       }catch (IOException e){
           e.printStackTrace();
       }
   }
   public String receive(){
          try {
              receiveSocket.receive(receiveBuffer);
              //其实没必要判断
              if(port2 != receiveBuffer.getPort())
                  port2 = receiveBuffer.getPort();
              if(ip != receiveBuffer.getAddress().getHostAddress())
                  ip = receiveBuffer.getAddress().getHostAddress();
              return new String(receiveBuffer.getData());
          }catch (IOException e){
              e.printStackTrace();
          }
          return null;
   }


}

