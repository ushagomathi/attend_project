import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDashboard extends JFrame {

    private String adminUsername;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField facultyNameField;
    private JTextField idNumberField;
    private JTextField designationField;

    public AdminDashboard(String adminUsername) {
        this.adminUsername = adminUsername;
        initUI();
    }

    private void initUI() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Top panel with home and logout buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");
        topPanel.add(homeButton);
        topPanel.add(logoutButton);

        JPopupMenu dropdownMenu = new JPopupMenu();
        JMenuItem facultyInfoItem = new JMenuItem("Faculty Information");
        JMenuItem AttendenceDetailsItem = new JMenuItem("Attendence Details");
      
        dropdownMenu.add(facultyInfoItem);
        dropdownMenu.add(AttendenceDetailsItem);
     

        // Center panel with faculty information labels and table
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Create a panel for labels and input fields
        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        JLabel facultyNameLabel = new JLabel("Faculty Name:");
        JLabel idNumberLabel = new JLabel("ID Number:");
        JLabel designationLabel = new JLabel("Designation:");
        facultyNameField = new JTextField();
        idNumberField = new JTextField();
        designationField = new JTextField();
        infoPanel.add(facultyNameLabel);
        infoPanel.add(facultyNameField);
        infoPanel.add(idNumberLabel);
        infoPanel.add(idNumberField);
        infoPanel.add(designationLabel);
        infoPanel.add(designationField);
        centerPanel.add(infoPanel, BorderLayout.NORTH);

        // Example data for faculty information (replace with actual data from a database)
        Object[][] facultyData = {
                {"John Doe", "12345", "Professor"},
                {"Jane Smith", "67890", "Assistant Professor"}
                // Add more rows as needed
        };
        String[] columnNames = {"faculty Name", "ID Number", "Designation"};

        tableModel = new DefaultTableModel(facultyData, columnNames);
        table = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(table);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);


        // Bottom panel with search, save, new, and delete buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton searchButton = new JButton("Search");
        JButton saveButton = new JButton("Save");
        JButton newButton = new JButton("New");
        JButton deleteButton = new JButton("Delete");
        bottomPanel.add(searchButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(newButton);
        bottomPanel.add(deleteButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dropdownMenu.show(homeButton, 0, homeButton.getHeight());
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String facultyName = facultyNameField.getText();
                String idNumber = idNumberField.getText();
                String designation = designationField.getText();
                boolean found = false;
    
                // Loop through the table and highlight the row if it matches the search criteria
                for (int i = 0; i < table.getRowCount(); i++) {
                    String tableFacultyName = table.getValueAt(i, 0).toString();
                    String tableIdNumber = table.getValueAt(i, 1).toString();
                    String tableDesignation = table.getValueAt(i, 2).toString();
                    if (facultyName.equals(tableFacultyName) && idNumber.equals(tableIdNumber) && designation.equals(tableDesignation)) {
                        table.getSelectionModel().setSelectionInterval(i, i);
                        table.scrollRectToVisible(new Rectangle(table.getCellRect(i, 0, true)));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Not Found");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int currentRow = table.getSelectedRow();
                if (currentRow != -1) {
                    // Check if all columns in the selected row are filled
                    boolean allColumnsFilled = true;
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        if (table.getValueAt(currentRow, col) == null) {
                            allColumnsFilled = false;
                            break;
                        }
                    }
    
                    if (allColumnsFilled) {
                        // Perform the save action
                        try {
                           
                            Class.forName("com.mysql.jdbc.Driver");
    
                            // Replace these with your actual database connection details
                            String dbUrl = "jdbc:mysql://localhost:3306/studdb";
                            String dbUsername = "root";
                            String dbPassword = "lordshiva3";
    
                            // Establish the database connection
                            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    
                            // Define the SQL query to insert data into your table
                            String insertQuery = "INSERT INTO staffdet (column1, column2, column3) VALUES (?, ?, ?)";
    
                            // Prepare the SQL statement
                            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
    
                            // Get data from the selected row
                            String facultyName = table.getValueAt(currentRow, 0).toString();
                            String idNumber = table.getValueAt(currentRow, 1).toString();
                            String designation = table.getValueAt(currentRow, 2).toString();
    
                            preparedStatement.setString(1, facultyName);
                            preparedStatement.setString(2, idNumber);
                            preparedStatement.setString(3, designation);
    
                            // Execute the insert
                            preparedStatement.executeUpdate();
    
                            // Close the database connection
                            connection.close();
    
                            JOptionPane.showMessageDialog(null, "Data saved to the database successfully!");
                        } catch (ClassNotFoundException | SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error saving data to the database: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in all columns before saving.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to save.");
                }
            }
        }); 
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the button is in "New" mode
                if (newButton.getText().equals("New")) {
                    // Change the button text to "Update"
                    newButton.setText("Update");
        
                    // Disable the saveButton
                    saveButton.setEnabled(false);
        
                    // Get the input values
                    String facultyName = facultyNameField.getText();
                    String idNumber = idNumberField.getText();
                    String designation = designationField.getText();
        
                    // Add a new row to the table
                    Object[] newRow = { facultyName, idNumber, designation };
                    tableModel.addRow(newRow);
        
                    // Clear the input fields
                    facultyNameField.setText("");
                    idNumberField.setText("");
                    designationField.setText("");
                } else if (newButton.getText().equals("Update")) {
                    // Check if all required fields are filled
                    String facultyName = facultyNameField.getText();
                    String idNumber = idNumberField.getText();
                    String designation = designationField.getText();
        
                    if (!facultyName.isEmpty() && !idNumber.isEmpty() && !designation.isEmpty()) {
                        // Enable the saveButton
                        saveButton.setEnabled(true);
        
                        // Change the button text back to "New"
                        newButton.setText("New");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                }else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

        // Add action listeners for the dropdown menu items
        facultyInfoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement faculty information functionality here
                new AdminDashboard(adminUsername);
                dispose();
            }
        });

        AttendenceDetailsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement working details functionality here
                new MainApplicationFrame("admin");
            }
        });

       
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginPage();
                dispose(); // Close the admin dashboard window after returning to the login page
            }
        });

        // Add components to the main frame
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Start with a sample username (replace with actual login logic)
            String adminUsername = "SampleAdmin";
            new AdminDashboard(adminUsername);
        });
    }
}
