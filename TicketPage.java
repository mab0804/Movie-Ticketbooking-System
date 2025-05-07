import java.sql.*;
import javax.swing.*;

public class TicketPage extends JFrame {
    public TicketPage(String username, int movieId, int tickets) {
        setTitle("Your Ticket");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextArea ticketArea = new JTextArea(10, 25);
        ticketArea.setEditable(false);

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT name, time FROM movies WHERE id = ?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ticketArea.setText("Movie: " + rs.getString("name") +
                                   "\nTime: " + rs.getString("time") +
                                   "\nTickets: " + tickets +
                                   "\nTotal Price: Rs. " + (tickets * 200));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(ticketArea);
        setVisible(true);
    }
}
