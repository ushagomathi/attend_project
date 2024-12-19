import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class StudentAttendancePage extends JFrame {
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField attendanceCountField;
    private JButton calculateButton;

    public StudentAttendancePage() {
        initUI();
    }

    private void initUI() {
        setTitle("Student Attendance");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        // Add columns to the table model
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Attendance Status");
        tableModel.addColumn("Total Working Days");
        tableModel.addColumn("Attendance Percentage");

        nameField = new JTextField(20);
        attendanceCountField = new JTextField(20);
        calculateButton = new JButton("Calculate");

        // Create a panel for input fields and the calculate button
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Attendance Count:"));
        inputPanel.add(attendanceCountField);
        inputPanel.add(calculateButton);

        // Add input panel to the frame
        add(inputPanel, BorderLayout.NORTH);

        // Calculate button action
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = nameField.getText();
                double attendanceCount = Double.parseDouble(attendanceCountField.getText());
                final int totalWorkingDays = 100;  // Fixed total working days

                double attendancePercentage = (totalWorkingDays == 0) ? 0 : (attendanceCount / totalWorkingDays) * 100;

                Object[] rowData = {
                    studentName,
                    (attendancePercentage < 75.0) ? "Low" : "Good",
                    totalWorkingDays,
                    String.format("%.2f%%", attendancePercentage)
                };

                tableModel.addRow(rowData);
            }
        });

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear input fields
                nameField.setText("");
                attendanceCountField.setText("");
            }
        });

        // Add the back button to the frame
        add(backButton, BorderLayout.SOUTH);

        // Add table to the frame
        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentAttendancePage();
        });
    }
}
