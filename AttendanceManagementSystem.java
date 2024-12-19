import javax.swing.*;
import java.awt.*;

public class AttendanceManagementSystem extends JFrame {

    public AttendanceManagementSystem() {
        initUI();
    }

    private void initUI() {
        setTitle("Attendance Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        JButton adminButton = new JButton("Admin");
        JButton studentButton = new JButton("Student");

        adminButton.addActionListener(e -> showAdminLoginPage());
        studentButton.addActionListener(e -> showStudentLoginPage());

        mainPanel.add(adminButton);
        mainPanel.add(studentButton);

        add(mainPanel);

        setVisible(true);
    }

    private void showAdminLoginPage() {
        SwingUtilities.invokeLater(() -> {
            new AdminLoginPage();
            dispose(); // Close the main window after opening the admin login window
        });
    }

    private void showStudentLoginPage() {
        SwingUtilities.invokeLater(() -> {
            new StudentLoginPage();
            dispose(); // Close the main window after opening the student login window
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceManagementSystem::new);
    }
}
