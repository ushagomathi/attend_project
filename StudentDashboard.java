import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDashboard extends JFrame {

    private String username;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField regNumberField;
    private JButton registerButton; // Corrected variable name

    public StudentDashboard(String username) {
        this.username = username;
        initUI();
    }

    private void initUI() {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Top panel with home and logout buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(homeButton);
        topPanel.add(logoutButton);

        // Create a JPopupMenu for the home button
        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem studentInfoItem = new JMenuItem("Student Information");
        JMenuItem attendanceItem = new JMenuItem("Student Attendance Page");
        dropdownMenu.add(studentInfoItem);
        dropdownMenu.add(attendanceItem);

        // Center panel with student information labels and table
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Create a panel for labels and input fields
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        JLabel nameLabel = new JLabel("Student Name:");
        JLabel regNumberLabel = new JLabel("Register Number:");
        nameField = new JTextField();
        regNumberField = new JTextField();
        infoPanel.add(nameLabel);
        infoPanel.add(nameField);
        infoPanel.add(regNumberLabel);
        infoPanel.add(regNumberField);

        // Add the infoPanel to the centerPanel
        centerPanel.add(infoPanel, BorderLayout.NORTH);

        // Example data for student information (replace with actual data from a
        // database)
        Object[][] studentData = {
            { "John Doe", "A+", "Computer Science", "2021", "12345", "555-555-5555" },
            { "Jane Smith", "B-", "Computer Science", "2021", "67890", "726-282-1817" },
            { "Valar mathi", "B+", "Computer Science", "2021", "32432", "162-928-0181" },
            { "Iswarya", "O+", "Computer Science", "2021", "82722", "726-292-9282" },
            { "Jaya priya", "A+", "Computer Science", "2021", "27212", "727-292-2927" },
            { "Naveen", "0+", "Computer Science", "2021", "78282", "572-292-2373" },
            { "Sri", "O-", "Computer Science", "2021", "26271", "573-383-3837" },
            { "Trypy", "AB+", "Computer Science", "2021", "29182", "731-833-2112" },
            { "Tharan", "A+", "Computer Science", "2021", "28272", "111-291-2823" },
        };
        String[] columnNames = { "Name", "Blood Group", "Department", "Batch Year", "Register Number", "Phone Number" };

        tableModel = new DefaultTableModel(studentData, columnNames);
        table = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(table);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom panel with search, save, and new buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save");
        registerButton = new JButton("Register");
        JButton deleteButton = new JButton("Delete");
        bottomPanel.add(searchButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(registerButton);
        bottomPanel.add(deleteButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the registration button click
                openRegistrationPage();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the search values
                String studentName = nameField.getText();
                String regNumber = regNumberField.getText();
                boolean found = false;
        
                // Loop through the table and highlight the row if it matches the search criteria
                for (int i = 0; i < table.getRowCount(); i++) {
                    String tableStudentName = table.getValueAt(i, 0).toString();
                    String tableRegNumber = table.getValueAt(i, 4).toString();
                    if (studentName.equals(tableStudentName) && regNumber.equals(tableRegNumber)) {
                        table.getSelectionModel().setSelectionInterval(i, i);
                        table.scrollRectToVisible(new Rectangle(table.getCellRect(i, 0, true)));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row
                int selectedRow = table.getSelectedRow();
        
                // Check if a row is selected
                if (selectedRow != -1) {
                    // Get the data from the selected row
                    String studentName = nameField.getText();
                    String regNumber = regNumberField.getText();
        
                    // Update the table with the new data
                    table.setValueAt(studentName, selectedRow, 0);
                    table.setValueAt(regNumber, selectedRow, 4);
        
                    // Clear the input fields
                    nameField.setText("");
                    regNumberField.setText("");
        
                    JOptionPane.showMessageDialog(null, "Data saved.");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to save.");
                }
            }
        });
        

        // Handle other button actions (search, save, delete) here

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropdownMenu.show(homeButton, 0, homeButton.getHeight());
            }
        });

        studentInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the student information functionality here
                new StudentDashboard(username);
                        dispose();
            }
        });

        attendanceItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the student attendance functionality here
                new StudentAttendancePage();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the logout button click
                new StudentLoginPage();
                dispose();
            }
        });

        // Add components to the main frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void openRegistrationPage() {
        // Implement the registration page opening here, for example:
        new StudentSignupPage();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = "Student";
            new StudentDashboard(username);
        });
    }
}
