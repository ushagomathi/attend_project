import java.sql.*;

public class attendence {
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("started");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/studdb", "root",
                    "lordshiva3");

            // Create a statement
            Statement stmt = con.createStatement();

            // Replace "<query>" with your actual SQL query
            String query = "<query>";

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Replace the column name in getString() with your actual column name from the
                // result set
                String data = rs.getString("column_name");
                System.out.println(data);
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
            con.close();
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException
            e.printStackTrace();
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
        }

        // Prompt user to select user type (Admin or Student) and open the corresponding login page
        String userType = "Admin"; // Replace this with user input, e.g., using a dialog box or console input

        if (userType.equalsIgnoreCase("Admin")) {
            AdminLoginPage AdminLoginPage = new AdminLoginPage();
            AdminLoginPage.initUI();
        } else if (userType.equalsIgnoreCase("Student")) {
            StudentLoginPage StudentLoginPage = new StudentLoginPage();
            StudentLoginPage.initUI();
        } else {
            System.out.println("Invalid user type!");
        }
    }
}
