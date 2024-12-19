import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginPage extends JFrame {

    public AdminLoginPage() {
        initUI();
    }

    public void initUI() {
        setTitle("Admin Login");
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

                // Perform admin login authentication here (e.g., check username and password against a database)
                // For demonstration purposes, we'll use a static admin username and password.
                String adminUsername = "admin";
                String adminPassword = "admin123";

                if (username.equals(adminUsername) && password.equals(adminPassword)) {
                    new AdminDashboard(username);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
                }
            }
        });

        add(panel);

        setVisible(true);
    }

    private void showAdminDashboard(String username) {
        new AdminDashboard(username);
        dispose(); // Close the admin login window after opening the admin dashboard
    }
}