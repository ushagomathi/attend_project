import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentSignupPage {
    private JFrame frame;
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField registerNumberField;
    private JPasswordField passwordField;
    private JTextField phoneNumberField;
    private JTextField batchYearField;
    private JTextField bloodGroupField;

    public StudentSignupPage() {
        frame = new JFrame("Student Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel departmentLabel = new JLabel("Department:");
        JLabel registerNumberLabel = new JLabel("Register Number:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        JLabel batchYearLabel = new JLabel("Batch Year:");
        JLabel bloodGroupLabel = new JLabel("Blood Group:");

        nameField = new JTextField();
        departmentField = new JTextField();
        registerNumberField = new JTextField();
        passwordField = new JPasswordField();
        phoneNumberField = new JTextField();
        batchYearField = new JTextField();
        bloodGroupField = new JTextField();

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String department = departmentField.getText();
                String registerNumber = registerNumberField.getText();
                char[] password = passwordField.getPassword();
                String phoneNumber = phoneNumberField.getText();
                String batchYear = batchYearField.getText();
                String bloodGroup = bloodGroupField.getText();

                // Validation
                if (name.isEmpty() || department.isEmpty() || registerNumber.isEmpty() || new String(password).isEmpty()
                        || phoneNumber.isEmpty() || batchYear.isEmpty() || bloodGroup.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
                } else {
                    try {
                        // Database Connection
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        String dbUrl = "jdbc:mysql://localhost:3306/studdb";
                        String dbUsername = "root";
                        String dbPassword = "lordshiva3";
                        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

                        // SQL Query
                        String insertQuery = "INSERT INTO studentdata(Name, Department, Register_Number, Password, Phone_Number, Batch_Year, Blood_Group) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, department);
                        preparedStatement.setString(3, registerNumber);
                        preparedStatement.setString(4, new String(password));
                        preparedStatement.setString(5, phoneNumber);
                        preparedStatement.setString(6, batchYear);
                        preparedStatement.setString(7, bloodGroup);

                        // Execute the Insert
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Student data inserted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to insert student data.");
                        }

                        // Close the database connection
                        connection.close();

                        // You can also close this window and open the student's dashboard or login page
                        frame.dispose();
                        StudentLoginPage studentLoginPage = new StudentLoginPage();
                        studentLoginPage.initUI();

                    } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                    }
                }
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(departmentLabel);
        panel.add(departmentField);
        panel.add(registerNumberLabel);
        panel.add(registerNumberField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);
        panel.add(batchYearLabel);
        panel.add(batchYearField);
        panel.add(bloodGroupLabel);
        panel.add(bloodGroupField);
        panel.add(new JLabel());
        panel.add(submitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentSignupPage();
            }
        });
    }
}
