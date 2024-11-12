import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;

public class EmployeeManagement extends WorkEnvironment {
    private JLabel employeeManagementSystem;
    private JLabel cutDownLine;
    private JButton addWay;
    private JButton deleteWay;
    private JButton changeWay;
    private JButton viewWay;
    private JButton cancel;

    private JPanel framePanel;
    private JPanel buttonPanel;
    private JPanel cutDownPanel;
    private JPanel smallWindowPanel; // 小型窗口面板

    private JTable table; // 用于显示数据库中的数据
    private DefaultTableModel tableModel; // 表格模型

    public EmployeeManagement() {
        super("Employee Management");
        setSize(800, 600);
        setLayout(new BorderLayout()); // 使用 BorderLayout 布局管理器

        framePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cutDownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        smallWindowPanel = new JPanel(); // 小型窗口面板

        // 加载 LOGO.png 图片
        ImageIcon logoIcon = new ImageIcon("G:/JAVA/demo/src/main/ApplicationSystem/LOGO.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        framePanel.add(logoLabel); // 将图片添加到 framePanel 中

        // 初始化字段
        employeeManagementSystem = new JLabel("Employee Management Application");
        Font customFont = new Font("Times New Roman", Font.BOLD, 36);
        employeeManagementSystem.setFont(customFont);
        framePanel.add(employeeManagementSystem);

        addWay = new JButton("Add");
        buttonPanel.add(addWay);

        deleteWay = new JButton("Delete");
        buttonPanel.add(deleteWay);

        changeWay = new JButton("Change");
        buttonPanel.add(changeWay);

        viewWay = new JButton("View");
        buttonPanel.add(viewWay);

        cancel = new JButton("Cancel");
        buttonPanel.add(cancel);

        cutDownLine = new JLabel("---------------------------------------------------------------------------------------------------------------------------");
        cutDownPanel.add(cutDownLine);

        // 设置小型窗口面板的布局
        smallWindowPanel.setLayout(new BorderLayout());
        smallWindowPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 添加一些边距

        // 初始化表格模型和表格
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        smallWindowPanel.add(scrollPane, BorderLayout.CENTER);

        // 将各个面板添加到主窗口中
        add(framePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(cutDownPanel, BorderLayout.SOUTH);
        add(smallWindowPanel, BorderLayout.EAST); // 将小型窗口面板添加到 EAST 区域

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addWay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示添加员工信息的对话框
                loadDataFromDatabase();
                addEmployee();

            }
        });

        deleteWay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 从数据库中加载数据
                loadDataFromDatabase();
                JOptionPane.showMessageDialog(null, "Now you can delete the employee information", "Delete Employee", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        changeWay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 从数据库中加载数据
                loadDataFromDatabase();
                JOptionPane.showMessageDialog(null, "Now you can change the employee information", "Change Employee", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        viewWay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 从数据库中加载数据
                loadDataFromDatabase();
                JOptionPane.showMessageDialog(null, "Now you can view the employee information", "View Employee", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 取消操作并初始化表格
                initializeTable();
                JOptionPane.showMessageDialog(null, "Operation canceled and table initialized", "Cancel", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void loadDataFromDatabase() {
        try {
            // 连接到数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stu", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employeedata");

            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 清空表格模型
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            // 添加列名到表格模型
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // 添加数据到表格模型
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            // 关闭连接
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeTable() {
        // 清空表格模型
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }

    private void addEmployee() {
        // 创建一个对话框来获取用户输入
        JTextField timelyFinancial = new JTextField(10);
        JTextField position = new JTextField(10);
        JTextField namee = new JTextField(10);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("TimelyFinancial:"));
        inputPanel.add(timelyFinancial);
        inputPanel.add(new JLabel("duty:"));
        inputPanel.add(position);
        inputPanel.add(new JLabel("name:"));
        inputPanel.add(namee);


        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String TimelyFinancial = timelyFinancial.getText();
            String duty = position.getText();
            String name = namee.getText();
            String password = "123456";
            String username = generateUniqueRandomNumber();

            // 插入数据到数据库
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stu", "root", "root");
                String sql = "INSERT INTO employeedata (TimelyFinancial, duty, name,password,username) VALUES (?, ? , ? , ? , ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, TimelyFinancial);
                preparedStatement.setString(2, duty);
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, username);

                preparedStatement.executeUpdate();

                // 关闭连接
                preparedStatement.close();
                connection.close();

                // 重新加载数据
                loadDataFromDatabase();

                JOptionPane.showMessageDialog(null, "Employee added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to add employee", "Error", JOptionPane.ERROR_MESSAGE);
            }
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


    @Override
    public void enterEnvironment() {
        // 在这里添加具体的员工管理界面逻辑
    }

    public static void main(String[] args) {
        new EmployeeManagement();
    }
}