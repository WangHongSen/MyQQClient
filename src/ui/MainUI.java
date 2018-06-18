package ui;


import bean.Account;
import utili.CMD;
import utili.Connection;

import javax.naming.ldap.SortKey;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;

public class MainUI extends JFrame implements ActionListener {
    private Account account;
    private JTabbedPane tab;
    private JList jfs;
    private  JScrollPane sl;
    private  JButton btnFind;//查找好友
    private JTextArea jtFriendAccount;//要查找的好友QQ号码
    private Vector<Account> friends;

    //查找friend事件
    @Override
    public void actionPerformed(ActionEvent e) {
        int friendAccount  = Integer.parseInt(jtFriendAccount.getText());
        Connection con = new Connection();
        System.out.println(friendAccount);
        con.addFriend(account.getQqCode(),friendAccount);
    }

    public MainUI(){

}

    public MainUI(Account a,Vector<Account> fs){
        System.out.println(a.toString());
        this.friends =fs;
        this.account = a;
        setTitle("MyQQ~"+a.getNickName());
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        Image icon = new ImageIcon("images/icon.png").getImage();
        setIconImage(icon);
        setSize(360,600);
        JLabel bj = new JLabel(new ImageIcon("images/mainbj.png"));//添加背景
        //add(bj, BorderLayout.NORTH);

        jfs = new JList();
        jfs.setPreferredSize(new Dimension(300,500));
        jfs.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);//只允许选择一个
        jfs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
           //     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                JList l = (JList)e.getSource();
                ListSelectionModel lsm = l.getSelectionModel();
                int index = lsm.getLeadSelectionIndex();
                System.out.println(index);
                new ChatMain(account,friends.elementAt(index));
            }
        });
//        jfs.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                if(e.getClickCount() == 2){
//                    System.out.println("双击");
//                    JList myList = (JList) e.getSource();
//                    int index = myList.getSelectedIndex();    //已选项的下标
//                    Object obj =myList.getModel().getElementAt(index);  //取出数据
//                    System.out.println(obj.toString());
//                    System.out.println(obj.getClass().getCanonicalName());
//                }
//            }
//        });
        refresh();
        sl = new JScrollPane( jfs);
        sl.setVisible(true);

       // sl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        btnFind = new JButton("加为好友");
        btnFind.addActionListener(this);
        add(sl,BorderLayout.CENTER);
        JPanel jpAddFriend = new JPanel();
        jpAddFriend.setLayout(new FlowLayout());
        jtFriendAccount = new JTextArea(1,18);
       // jtFriendAccount.setSize(50,100)
        jtFriendAccount.setEditable(true);
        JTextField jt = new JTextField("QQ: ");
        jpAddFriend.add(jt);
        jpAddFriend.add(jtFriendAccount);
        jpAddFriend.add(btnFind);
        add(jpAddFriend,BorderLayout.SOUTH);


        setVisible(true);
        setResizable(false);
        int width  = Toolkit.getDefaultToolkit().getScreenSize().width-400;
        setLocation(width,10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //启动线程监听是否有会话发起
//          Thread t = new Thread(new Runnable() {
//              @Override
//              public void run() {
//                  Connection con = new Connection();
//                  Socket server = con.getSocket();
//                  try {
//                      DataInputStream in = new DataInputStream(server.getInputStream());
//                      int session = in.readInt();
//                      if(session == CMD.CMD_SESSIONL){
//                          int port = in.readInt();
//                          int qq = in.readInt();
//                          String name = new String(in.readAllBytes());
//                          int flag = in.readInt();
//                          byte[] key;
//                          if(flag == CMD.SEND_KEY)
//                              key = in.readAllBytes();
//                      //todo
//                          new ChatMain(account,new Account(qq,name));
//                      }
//                  }catch(Exception e){
//                      e.printStackTrace();
//                  }
//              }
//          });
    }

    public void refresh(){
        for(Account a:friends)
           System.out.println(a.toString()+ " ip:"+ a.getIpAddr());
        //把向量放到list
        jfs.setModel(new listmodel(friends));  //显示资料
        jfs.setCellRenderer(new myFriend(friends));
    }

   class Listener extends Thread{

   }
    class myFriend extends DefaultListCellRenderer{
        Vector<Account> datas;
        public myFriend(Vector<Account> data){
            this.datas = data;
        }
        public  Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,boolean cellHasFocus){
            Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
            if(index>=0&&index<datas.size()){
                Account user = (Account)datas.get(index);
                if(user.getStatues() != 1){
                    setBackground(Color.gray);
                }
                setText(user.getNickName()+"("+user.getQqCode()+")");
            }
            if(isSelected){
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }else{
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
    class listmodel extends AbstractListModel{
        Vector dats;
        public listmodel(Vector v){
            this.dats = v;
        }

        @Override
        public int getSize() {
            return dats.size();
        }

        public Object getElementAt(int index){
            Account user =(Account)dats.get(index);
            return user.getNickName().trim()+" ("+user.getQqCode()+") ";
        }
    }

    public static void main(String[] args) {
        Vector<Account> fs = new Vector<>();
        fs.add(new Account(46654,"RE"));
        fs.add(new Account(8778,"fd"));
      new MainUI(new Account(1234,"32"),fs);
    }
}
