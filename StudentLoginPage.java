import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentLoginPage extends JFrame {

    public StudentLoginPage() {
        initUI();
    }

    public void initUI() {
        setTitle("Student Login");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Perform student login authentication here (e.g., check username and password against a database)
                // For demonstration purposes, we'll use a static student username and password.
                String studentUsername = "student";
                String studentPassword = "student123";

                if (username.equals(studentUsername) && password.equals(studentPassword)) {
                    showStudentDashboard(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });

        add(panel);

        setVisible(true);
    }

    private void showStudentDashboard(String username) {
        new StudentDashboard(username);
        dispose(); // Close the student login window after opening the student dashboard
    }
}
