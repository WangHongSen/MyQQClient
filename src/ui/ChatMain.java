package ui;

import bean.Account;
import utili.ClientUDP;
import utili.Connection;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;

public class ChatMain extends JFrame implements ActionListener {
    private JButton send;
    private JPanel messages;
    private JTextPane input;
    private int count = 0;//目前消息界面消息个数,最大为6
    private JLabel m1,m2,m3,m4,m5,m6;
    private Account self,friend;
    public Socket server = null; //向服务端请求key
    private int port; //用于沟通的端口号
    private ClientUDP udp = null;
    private void requestKey(){
        //通话双方自己协商key

//        Connection con = new Connection();
//        Random random = new Random();
//        double seed =random.nextDouble();
//        do{
//            port = (int)(seed * 66535);
//        }while(port <024 || port == self.getPort());
//        con.requestKey(self,port,friend);
    }
    public void communicate(){
        byte[] msg1 = new byte[1028];//接收
        byte[] msg2 = new byte[1028];//发送
        try {
            DatagramSocket sendSocket = new DatagramSocket();
            DatagramSocket receive = new DatagramSocket();
            DatagramPacket sendBuffer = new DatagramPacket(msg2,1028);
            DatagramPacket receiveBuffer = new DatagramPacket(msg1,1028);

        }catch (SocketException e){
            e.printStackTrace();
        }

    }
    private void change(String msg){
        if(count>=6){
            m1.setText(m2.getText());
            m2.setText(m3.getText());
            m3.setText(m4.getText());
            m4.setText(m5.getText());
            m5.setText(m6.getText());
            m6.setText(msg);
            count++;
        }else{
            switch(count){
                case 0: m6.setText(msg); break;
                case 1: m5.setText(m6.getText());m6.setText(msg); break;
                case 2: m4.setText(m5.getText());  m5.setText(m6.getText()) ;m6.setText(msg); break;
                case 3: m3.setText(m4.getText()); m4.setText(m5.getText());  m5.setText(m6.getText()) ;m6.setText(msg); break;
                case 4: m2.setText(m3.getText());m3.setText(m4.getText()); m4.setText(m5.getText());  m5.setText(m6.getText()) ;m6.setText(msg); break;
                case 5: m1.setText(m2.getText());m2.setText(m3.getText());m3.setText(m4.getText()); m4.setText(m5.getText());  m5.setText(m6.getText()) ;m6.setText(msg); m6.setText(msg); break;
            }
            count++;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = input.getText().trim();
        if(msg.equals("")){
            JOptionPane.showMessageDialog(null,"No message ,please input sonmthing");
            return;
        }
        udp.send(msg);
        msg = self.getNickName()+ " :  " +msg;
        change(msg);
    }

    public  ChatMain(Account acc,Account fri){
        super("Myqq");
        self = acc;
        friend = fri;
        //  Image icon = new ImageIcon();
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        setLayout(null);

//        try {
//            toGetMsg = DatagramChannel.open();
////            toGetMsg.connect()
//            //   toGetMsg.bind(new InetSocketAddress(fri.getIpAddr(),fri.getPort()));
//        }catch (IOException ioe){
//            ioe.printStackTrace();
//        }

        messages = new JPanel();
        messages.setBounds(0,0,500,300);
        messages.setLayout(null); // 消息面板内部也使用绝对布局
        m1 = new JLabel();
        m2 = new JLabel();
        m3 = new JLabel();
        m4 = new JLabel();
        m5 = new JLabel();
        m6 = new JLabel();
        m1.setBounds(0,0,400,50);
        m2.setBounds(0,50,400,50);
        m3.setBounds(0,100,400,50);
        m4.setBounds(0,150,400,50);
        m5.setBounds(0,200,400,50);
        m6.setBounds(0,250,400,50);
        Border b = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        m1.setBorder(b);
        m2.setBorder(b);
        m3.setBorder(b);
        m4.setBorder(b);
        m5.setBorder(b);
        m6.setBorder(b);
        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        messages.add(m5);
        messages.add(m6);

        input = new JTextPane();
        input.setBounds(0,300,400,100);
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    String msg = input.getText().trim();
                    if(msg.equals("")){
                        JOptionPane.showMessageDialog(null,"No message ,please input sonmthing");
                        return;
                    }
                    msg = self.getNickName()+ " :  " +msg;
                    change(msg);
                }
            }
        });

        send  = new JButton("send");
        send.setBounds(400,300,100,100);
        send.addActionListener(this);
        send.setFont(new Font("宋体",Font.BOLD,20));
        setSize(500,400);

//        try{
//            monitor = DatagramChannel.open();
//            // monitor.bind(new InetSocketAddress(friend.getIpAddr(),friend.getPort()));
//        }catch(IOException ioe){
//            ioe.printStackTrace();
//        }
        add(messages);
        add(input);
        add(send);

        ClientUDP udp = new ClientUDP(self.getPort(),friend.getIpAddr(),friend.getPort());
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = udp.receive();
                change(friend.getNickName()+" : "+msg);
            }
        });
        t.start();
    }

//    private boolean send(String msg){
//        ByteBuffer buff = ByteBuffer.allocate(140);//限定消息长度为一百四十
//        buff.clear();
//        buff.put(msg.getBytes());
//        buff.flip();
//        try {
//            //   monitor.connect(new InetSocketAddress(friend.getIpAddr(),friend.getPort()));
//            int len = monitor.send(buff,new InetSocketAddress(friend.getIpAddr(),friend.getPort()));
//
//        }catch(IOException e){
//            System.out.println("meessage send failed !");
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    private boolean get(){
//        ByteBuffer buff = ByteBuffer.allocate(140);//限定消息长度为一百四十
//        buff.clear();
//        if(!monitor.isConnected()){
//            try {
//                monitor.connect(new InetSocketAddress(friend.getIpAddr(), friend.getPort()));
//                //  int len = monitor.reci(buff,new InetSocketAddress(friend.getIpAddr(),friend.getPort()));
//                monitor.receive(buff);
//            }catch(IOException e){
//                System.out.println("connect failed !");
//                e.printStackTrace();
//                return false;
//            }
//        }
//        buff.flip();//切换到读模式
//        byte[] bs =  buff.array();// new byte[buff.limit()];
//        //   buff.get(bs,0,buff.limit()-1);
//        try {
//            String msg = new String(bs, "utf-8");
//            change(msg);
//        }catch (UnsupportedEncodingException w){
//            w.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
    public static void main(String[] args) {
        Account a = new Account(123,"sw");
        new ChatMain(a,null);
    }

}
