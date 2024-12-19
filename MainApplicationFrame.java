import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApplicationFrame extends JFrame {

    private DefaultTableModel tableModel;
    private JTable attendanceTable;

    public MainApplicationFrame(String userType) {
        initUI(userType);
    }

    private void initUI(String userType) {
        setTitle("Attendance Management System - Home");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel userTypeLabel = new JLabel(userType + " | Home");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(userTypeLabel);
        topPanel.add(logoutButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        attendanceTable = createAttendanceTable();
        JScrollPane tableScrollPane = new JScrollPane(attendanceTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons
        JButton saveButton = new JButton("Save");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton addButton = new JButton("Add New Row");
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        logoutButton.addActionListener(e -> showLoginScreen());
        saveButton.addActionListener(e -> saveTableData());
        editButton.addActionListener(e -> enableTableEditing());
        deleteButton.addActionListener(e -> deleteSelectedRow());
        addButton.addActionListener(e -> addNewRow());

        add(mainPanel);
        setVisible(true);
    }

    private void showLoginScreen() {
        new AttendanceManagementSystem();
        dispose(); // Close the main application window after logout
    }

    private JTable createAttendanceTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("S.No");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Starting Date");
        tableModel.addColumn("Ending Date");
        tableModel.addColumn("Credits");

        JTable table = new JTable(tableModel);
        return table;
    }

    private void saveTableData() {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                Object value = tableModel.getValueAt(row, col);
                System.out.print(value + " | ");
            }
            System.out.println();
        }
    }

    class EditRowDialog extends JDialog {
        private int selectedRow;
        private JTextField[] editFields;

        public EditRowDialog(int selectedRow) {
            this.selectedRow = selectedRow;
            editFields = new JTextField[tableModel.getColumnCount()];

            JPanel editPanel = new JPanel(new GridLayout(tableModel.getColumnCount() + 1, 2));

            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                String columnName = tableModel.getColumnName(col);
                JLabel label = new JLabel(columnName + ": ");
                JTextField textField = new JTextField(tableModel.getValueAt(selectedRow, col).toString());
                editFields[col] = textField;

                editPanel.add(label);
                editPanel.add(textField);
            }

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> saveEditedRow());

            // Add some spacing at the bottom of the dialog
            editPanel.add(new JLabel(""));
            editPanel.add(saveButton);

            setTitle("Edit Row");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setModal(true);
            setSize(300, 250); // Increased the height
            setLocationRelativeTo(null);

            add(editPanel);
        }

        private void saveEditedRow() {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                tableModel.setValueAt(editFields[col].getText(), selectedRow, col);
            }
            dispose(); // Close the edit dialog
        }
    }

    private void enableTableEditing() {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow >= 0) {
            EditRowDialog editDialog = new EditRowDialog(selectedRow);
            editDialog.setVisible(true);
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = attendanceTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNewRow() {
        tableModel.addRow(new Object[]{"", "", "", "", ""});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String userType = "Admin";
            new MainApplicationFrame(userType);
        });
    }
}
