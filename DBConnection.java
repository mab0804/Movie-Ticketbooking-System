import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/movieticketbookingsys"; 
        String user = "root"; 
        String password = "1108"; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connected successfully!"); 
            return conn;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());  
            throw e;
        }
    }
}
