package ui;


import bean.Account;
import utili.CMD;
import utili.Connection;
import utili.Encry;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;


public class Login extends JFrame implements ActionListener,ItemListener,MouseListener {

    private JTextField cbqqCode;
    private JPasswordField txtpwd;
    private JLabel lblface,lblReg;
    private JButton btnLogin;
    private Connection con;

    public Login(){
        super("Myqq");
        con = new Connection();
        //  Image icon = new ImageIcon();
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        Image icon = new ImageIcon("images/icon.png").getImage();
        setIconImage(icon);
        JLabel bj = new JLabel(new ImageIcon("images/bj.png"));
        add(bj, BorderLayout.NORTH);

        cbqqCode = new JTextField();
        txtpwd = new JPasswordField();
        //   lblfindpwd = new JLabel("找回密码");
        lblReg = new JLabel("注册用户");
//        cbmemorry = new JCheckBox("记住密码");
//        cbautologin = new JCheckBox("自动登录");
        btnLogin =  new JButton("登录");
        cbqqCode.setBounds(50,80,200,25);
        cbqqCode.setEditable(true);
        lblReg.setBounds(260,80,80,25);
        lblReg.setBackground(Color.black);
        lblReg.setFont(new Font("宋体",Font.PLAIN,12));
        txtpwd.setBounds(50,130,200,25);

        btnLogin.setBounds(120,200,100,25);
        btnLogin.setOpaque(true);
        lblface = new JLabel(new ImageIcon("images/d.png"));
        lblface.setBounds(150,20,50,50);

        btnLogin.addActionListener(this);

        lblReg.addMouseListener(this);

        bj.add(lblface);
        bj.add(cbqqCode);
        bj.add(lblReg);
        bj.add(txtpwd);
//        bj.add(lblfindpwd);
//        bj.add(cbmemorry);
//        bj.add(cbautologin);
        bj.add(btnLogin);

        setSize(440,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args){
        new Login();
    }

    public void actionPerformed(ActionEvent e){
        Account acc=null;
        try {
            String qqcode = cbqqCode.getText().trim();
            //    String pwd =txtpwd.getPassword().toString().trim();
            acc = new Account();
            acc.setQqCode(Integer.parseInt(qqcode));
            //    acc.setPwd(pwd);
            String p = txtpwd.getText();

            //p = utili.Encry.encry(p);
            p = Encry.digest(p);
            acc.setPwd(p);

            acc.setStatues(1);//设为在线
            Vector<Account> friends = con.login(acc);
            acc = friends.lastElement();
            friends.remove(acc);
            System.out.println("dsa" +acc.toString());
            JOptionPane.showMessageDialog(null,e.getActionCommand()+"成功");
            if(acc!=null)
            {
                con.close();
                new MainUI(acc,friends);
            }

        }catch(NullPointerException ne){
            ne.printStackTrace();
        }

        this.dispose();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JOptionPane.showMessageDialog(null,e.getSource());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == lblReg){
         //   new RegUsers();
        }
//        JOptionPane.showMessageDialog(null,e.getSource());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
