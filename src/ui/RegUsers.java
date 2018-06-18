package ui;




import bean.Account;
import utili.CMD;
import utili.Connection;
import utili.Encry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.*;

public class RegUsers extends JFrame implements ActionListener {
    private Account account;
    private JLabel lblbgl;//背景图片
    private JComboBox cbface;
    private JTextField qqcode,nickname,port,age,ipAddr;
    private JRadioButton sex2,sex1;
    private JPasswordField pwd,cfpwd;
    private JButton btnSave,btnClose;
    private Connection con =null;


    public RegUsers(){
        con = new Connection();
        init();
    }
//    public RegUsers(Account acc){
//        this.account = acc;
//
//    }

    public void init(){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        Image icon = new ImageIcon("images/icon.png").getImage();
        setIconImage(icon);
        setTitle("注册");
        Icon bgimg = new ImageIcon("images/regbj.png");
        lblbgl = new JLabel(bgimg);
        add(lblbgl);
        lblbgl.setBorder(BorderFactory.createBevelBorder((2)));
        Icon face[]={
                new ImageIcon("images/female.png"),
                new ImageIcon("images/male.png"),
        };

        setSize(400,600);
        JLabel lblqqcode = new JLabel("QQ号码：",JLabel.RIGHT);
        JLabel lblpwd = new JLabel("密码：",JLabel.RIGHT);
        JLabel lblcfpwd = new JLabel("确认密码：",JLabel.RIGHT);
        JLabel lblnickname = new JLabel("昵称：",JLabel.RIGHT);
        JLabel lblipAddr = new JLabel("IP地址：",JLabel.RIGHT);
        JLabel lblport = new JLabel("端口号： ",JLabel.RIGHT);
        JLabel lblage = new JLabel("年龄：",JLabel.RIGHT);
        JLabel lblsex = new JLabel("性别：",JLabel.RIGHT);
        qqcode = new JTextField(10);
        lblqqcode.setBounds(100,100,60,30);
        qqcode.setBounds(160,100,120,30);
        lblbgl.add(lblqqcode);
        lblbgl.add(qqcode);
        nickname = new JTextField(15);
        lblnickname.setBounds(90,150,60,30);
        nickname.setBounds(160,150,120,30);
        lblbgl.add(lblnickname);
        lblbgl.add(nickname);
        port = new JTextField(15);
        pwd = new JPasswordField( );
        lblpwd.setBounds(0,200,60,30);
        pwd.setBounds(70,200,120,30);
        lblbgl.add(pwd);
        lblbgl.add(lblpwd);
        cfpwd = new JPasswordField();
        lblcfpwd.setBounds(200,200,70,30);
        cfpwd.setBounds(280,200,100,30);
        lblbgl.add(cfpwd);
        lblbgl.add(lblcfpwd);
        InetAddress addr = null;
        try{
            addr = InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        ipAddr = new JTextField(addr.getHostAddress());
        ipAddr.setBounds(70,250,100,30);
        ipAddr.setEditable(false);
        lblipAddr.setBounds(10,250,50,30);
        lblbgl.add(ipAddr);
        lblbgl.add(lblipAddr);
        port = new JTextField();
        port.setEditable(false);
        lblport.setBounds(200,250,50,30);
        port.setBounds(280,250,80,30);
        lblbgl.add(port);
        lblbgl.add(lblport);
        age = new JTextField();
        lblage.setBounds(100,300,50,30);
        age.setBounds(160,300,100,30);
        lblbgl.add(age);
        lblbgl.add(lblage);
        sex1 = new JRadioButton("男");
        sex2 = new JRadioButton("女");
        lblsex.setBounds(100,350,60,30);
        sex1.setBounds(160,350,60,30);
        sex2.setBounds(220,350,60,30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(sex1);
        bg.add(sex2);
        lblbgl.add(lblsex);
        lblbgl.add(sex1);
        lblbgl.add(sex2);
        btnClose = new JButton("关闭");
        btnSave= new JButton("创建账户");
        btnClose.setBounds(200,400,80,30);
        btnSave.setBounds(100,400,80,30);
        lblbgl.add(btnClose);
        lblbgl.add(btnSave);
        btnSave.addActionListener(this);
        btnClose.addActionListener(this);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭当前窗口
    }

    public static void main(String[] args) {
        new RegUsers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== btnSave){
            Account a = new Account();
            try{
                a.setQqCode(Integer.parseInt(qqcode.getText().trim()));//qq号
            }catch(Exception es){
                JOptionPane.showMessageDialog(null,"QQ号码输入错误");
                es.printStackTrace();
            }
            if(pwd.getPassword().toString().trim().equals("")){
                JOptionPane.showMessageDialog(null,"密码输入错误");
                return;
            }
            if(pwd.getPassword().toString().trim().equals(cfpwd.getPassword().toString().trim())){
                JOptionPane.showMessageDialog(null,"两次密码密码不一致");
                return;
            }
            if(nickname.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"昵称不能为空");
                return;
            }
            int nage =0;
            try{
                nage = Integer.parseInt(age.getText().trim());
            }catch(Exception ex1){
                JOptionPane.showMessageDialog(null,"年龄含有非法字符");
                return;
            }
            if(nage>150 || nage<0){
                JOptionPane.showMessageDialog(null,"年龄取值范围为1-150");
                return;
            }
            String p = pwd.getText();
            p=Encry.digest(p);//单项加密
            //   System.out.println(p);
            a.setPwd(p);
            a.setIpAddr(ipAddr.getText().trim());//ip地址
           // a.setPort(nport);//端口号
            a.setAge(nage); //年龄
            a.setNickName(nickname.getText().trim()); //昵称
            if(sex1.isSelected()){ //性别
                a.setSex("男");
            }else{
                a.setSex("女");
            }
            a.setStatues(0);
            System.out.println(a.toString());

          //实现注册
            con.regist(a);
            JOptionPane.showMessageDialog(null,"注册成功");
//            if(con.regist(a) == CMD.CMD_REGIST){
//                JOptionPane.showMessageDialog(null,"注册成功!");
//                con.close();
//            }else{
//                JOptionPane.showMessageDialog(null,"注册失败!");
//            }

        }

        if(e.getSource()==btnClose){
            if(con!=null) con.close();
            dispose();//关闭窗口
        }
    }
}
