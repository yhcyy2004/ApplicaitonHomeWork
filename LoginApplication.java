import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginApplication extends JFrame implements ActionListener{
    private String username;
    private String password;
    private double TimelyFinancial;
    private String duty;
    private String name;

                                                //create a visual interface
    private JLabel LoginSystem;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTimelyFinancial() {
        return TimelyFinancial;
    }

    public void setTimelyFinancial(double timelyFinancial) {
        TimelyFinancial = timelyFinancial;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //create a new frame
    public LoginApplication(String frameName){
        super(frameName);
        setSize(400,300);
        setLayout(new FlowLayout());

        panel1=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3=new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel4=new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 加载 LOGO.png 图片
        ImageIcon logoIcon = new ImageIcon("G:/JAVA/demo/src/main/ApplicationSystem/LOGO.jpg");
        JLabel logoLabel = new JLabel(logoIcon);

        this.add(logoLabel); // 将图片添加到 JFrame 的顶部


                                                //initialize the field
        LoginSystem=new JLabel("Login System");
        Font customFont=new Font("Times New Roman",Font.BOLD,36);
        LoginSystem.setFont(customFont);

        loginButton=new JButton("Login");
        this.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.setEnabled(true);
                String userFromField=usernameField.getText();
                String passwordFromField = String.valueOf(passwordField.getPassword());
                if(validateAccountFromDatabase(userFromField,passwordFromField)){
                    JOptionPane.showMessageDialog(null,"Welcome",null,JOptionPane.INFORMATION_MESSAGE);
                    String duty = getDutyFromDatabase(userFromField,passwordFromField);
                    switch (duty){
                        case "EmployeeManager"  :

                            break;
                        case "WareHouseManager" :

                            break;

                        case "NormalEmployee"   :

                            break;

                        default:

                            break;
                    }
                }else{
                    usernameField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null,"Error validating account:" ,null,JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        registerButton=new JButton("Register");
        this.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterAppication jumpWeb =new RegisterAppication(frameName);
                jumpWeb.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                jumpWeb.setVisible(true);
                jumpWeb.setLocationRelativeTo(null);
            }
        });

        Font labelFont =new Font("Times New Roman",Font.PLAIN,14);

        userLabel=new JLabel("Username:");
        userLabel.setFont(labelFont);
        this.add(userLabel);

        usernameField=new JTextField(20);
        this.add(usernameField);

        passwordLabel=new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        this.add(passwordLabel);

        passwordField=new JPasswordField(20);
        this.add(passwordField);


        panel1.add(LoginSystem);
        panel2.add(userLabel);
        panel2.add(usernameField);
        panel3.add(passwordLabel);
        panel3.add(passwordField);
        panel4.add(loginButton);
        panel4.add(registerButton);

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);





        setVisible(true);//set up the visual
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new LoginApplication("Application System");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private static boolean validateAccountFromDatabase(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/stu"; // 数据库连接URL
        String user = "root"; // 数据库用户名
        String pass = "root"; // 数据库密码
        String query = "SELECT * FROM employeedata WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there is a result, the account is valid


            }
        } catch (SQLException e) {
            return false;
        }
    }
    private static String getDutyFromDatabase(String username,String password) {
        String url = "jdbc:mysql://localhost:3306/stu"; // 数据库连接URL
        String user = "root"; // 数据库用户名
        String pass = "root"; // 数据库密码
        String query = "SELECT * FROM employeedata WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                   String duty = rs.getString("duty");
                   JOptionPane.showMessageDialog(null,"Have done!Your duty is : " + duty,null,JOptionPane.INFORMATION_MESSAGE);
                   return duty;
                } else {
                    JOptionPane.showMessageDialog(null,"Error validating account:" ,null,JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error getting" ,null,JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }


    }
