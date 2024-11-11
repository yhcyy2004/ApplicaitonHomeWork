import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;

public class RegisterAppication extends JFrame implements ActionListener {
    private String username;
    private String password;
    private double TimelyFinancial;
    private String duty;
    private String name;
    private JLabel RegisterSystem;
    private JLabel nameLabel;
    private JLabel passwordLabel;
    private JButton confirm;
    private JTextField nameField;
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

    public RegisterAppication(String frameName) {
        super(frameName);

        this.username = username;
        this.password = password;
        this.TimelyFinancial = TimelyFinancial;
        this.duty = duty;
        this.name = name;

        setSize(400, 300);
        setLayout(new FlowLayout());

        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 加载 LOGO.png 图片
        ImageIcon logoIcon = new ImageIcon("G:/JAVA/demo/src/main/ApplicationSystem/LOGO.jpg");
        JLabel logoLabel = new JLabel(logoIcon);

        this.add(logoLabel); // 将图片添加到 JFrame 的顶部

        RegisterSystem = new JLabel("Register System");
        Font customFont = new Font("Times New Roman", Font.BOLD, 36);
        RegisterSystem.setFont(customFont);
        this.add(RegisterSystem);

        confirm = new JButton("Submit");
        this.add(confirm);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterAppication registerAppication = new RegisterAppication("Register Frame");
                String nameFromField = nameField.getText();
                String passwordFromField = String.valueOf(passwordField.getPassword());
                if (passwordFromField.equals("")) {
                    passwordFromField = "123456";
                }
                String randomuser = generateUniqueRandomNumber();

                registerAppication.setUsername(randomuser);
                registerAppication.setPassword(passwordFromField);
                registerAppication.setTimelyFinancial(0);
                registerAppication.setDuty(null);
                registerAppication.setName(nameFromField);

                saveAccountToDatabase(registerAppication);
            }
        });

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 14);
        nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        this.add(nameLabel);

        nameField = new JTextField(20);
        this.add(nameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        this.add(passwordLabel);

        passwordField = new JPasswordField(20);
        this.add(passwordField);

        panel1.add(RegisterSystem);
        panel2.add(nameLabel);
        panel2.add(nameField);
        panel3.add(passwordLabel);
        panel3.add(passwordField);
        panel4.add(confirm);

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);

        setVisible(true);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new RegisterAppication("Register System");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void saveAccountToDatabase(RegisterAppication registerAppication) {
        String url = "jdbc:mysql://localhost:3306/stu"; // 数据库连接URL
        String user = "root"; // 数据库用户名
        String pass = "root"; // 数据库密码

        String sql = "INSERT INTO employeedata (username, password, TimelyFinancial, duty, name) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, registerAppication.getUsername());
            ps.setString(2, registerAppication.getPassword());
            ps.setDouble(3, registerAppication.getTimelyFinancial());
            ps.setString(4, registerAppication.getDuty());
            ps.setString(5, registerAppication.getName());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Have done! Your username is: " + registerAppication.getUsername(), null, JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String generateUniqueRandomNumber() {
        String randomNumber;
        boolean isUnique = false;

        while (!isUnique) {
            randomNumber = generateRandomNumber();
            isUnique = checkUnique(randomNumber);
            if (isUnique) {
                return randomNumber;
            }
        }

        return null; // 理论上不会到达这里
    }

    public static String generateRandomNumber() {
        // 固定的前缀 "5439"
        String prefix = "5439";

        // 创建一个随机数生成器
        Random random = new Random();

        // 生成剩余的6位随机数
        int remainingDigits = 6;
        StringBuilder randomPart = new StringBuilder();

        for (int i = 0; i < remainingDigits; i++) {
            randomPart.append(random.nextInt(10)); // 生成0到9之间的随机数
        }

        // 组合前缀和随机部分
        String randomNumber = prefix + randomPart.toString();

        return randomNumber;
    }

    public static boolean checkUnique(String randomNumber) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean isUnique = true;

        try {
            // 连接到数据库
            String url = "jdbc:mysql://localhost:3306/stu"; // 数据库连接URL
            String user = "root"; // 数据库用户名
            String pass = "root"; // 数据库密码
            conn = DriverManager.getConnection(url, user, pass);

            // 查询数据库以检查随机数是否已存在
            String sql = "SELECT * FROM employeedata WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, randomNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                isUnique = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isUnique;
    }
}