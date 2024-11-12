import javax.swing.*;
import java.awt.*;

public class EmployeeManagement extends WorkEnvironment {
    private JLabel employeeManagementSystem;
    private JLabel cutDownLine;
    private JButton addWay;
    private JButton deleteWay;
    private JButton changeWay;
    private JButton viewWay;

    private JPanel framePanel;
    private JPanel buttonPanel;
    private JPanel cutDownPanel;
    private JPanel smallWindowPanel;





    public EmployeeManagement() {
        super("Employee Management");
        setSize(700,600);
        setLayout(new FlowLayout());

        framePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cutDownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        smallWindowPanel = new JPanel();

        // 加载 LOGO.png 图片
        ImageIcon logoIcon = new ImageIcon("G:/JAVA/demo/src/main/ApplicationSystem/LOGO.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        this.add(logoLabel); // 将图片添加到 JFrame 的顶部

        // 初始化字段
        employeeManagementSystem = new JLabel("Employee Management Application");
        Font customFont = new Font("Times New Roman", Font.BOLD, 36);
        employeeManagementSystem.setFont(customFont);


        addWay=new JButton("Add");
        this.add(addWay);

        deleteWay=new JButton("Delete");
        this.add(deleteWay);

        changeWay=new JButton("Change");
        this.add(changeWay);

        viewWay=new JButton("view");
        this.add(viewWay);

        cutDownLine = new JLabel("---------------------------------------------------------------------------------------------------------------------------");
        this.add(cutDownLine);

        smallWindowPanel.setLayout(new BoxLayout(smallWindowPanel,BoxLayout.Y_AXIS));
        smallWindowPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        smallWindowPanel.add(new JLabel("Employee Name:"));
        smallWindowPanel.add(new JTextField(20));
        smallWindowPanel.add(new JLabel("Employee ID:"));
        smallWindowPanel.add(new JTextField(20));
        smallWindowPanel.add(new JLabel("Department:"));
        smallWindowPanel.add(new JTextField(20));

        JScrollPane scrollPane = new JScrollPane(smallWindowPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



        framePanel.add(logoLabel);
        framePanel.add(employeeManagementSystem);

        buttonPanel.add(addWay);
        buttonPanel.add(deleteWay);
        buttonPanel.add(changeWay);
        buttonPanel.add(viewWay);
        cutDownPanel.add(cutDownLine);

        add(framePanel);
        add(buttonPanel);
        add(cutDownPanel);
        add(scrollPane);






        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void enterEnvironment() {
        JOptionPane.showMessageDialog(null, "Have done!", null, JOptionPane.INFORMATION_MESSAGE);

        // 在这里添加具体的员工管理界面逻辑
    }


    public static void main(String[] args) {
        new EmployeeManagement();
    }
}