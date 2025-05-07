import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ShowListPage extends JFrame {
    public ShowListPage(String username) {
        setTitle("Available Shows");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Welcome Label
        JLabel welcome = new JLabel("Welcome, " + username + "! Here are your shows:");
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds(100, 30, 400, 40);
        add(welcome);

        // Movie List
        JList<String> movieList = new JList<>();
        DefaultListModel<String> model = new DefaultListModel<>();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT name FROM movies");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addElement(rs.getString("name"));
            }
            movieList.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieList.setBounds(100, 80, 400, 200);
        add(movieList);

        // Book Button
        JButton bookButton = new JButton("Book Tickets");
        bookButton.setBounds(200, 300, 200, 40);
        bookButton.setBackground(new Color(46, 204, 113));
        bookButton.setForeground(Color.WHITE);
        add(bookButton);

        bookButton.addActionListener(e -> {
            String selectedMovie = movieList.getSelectedValue();
            if (selectedMovie != null) {
                try {
                    // Get movie ID based on name
                    Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT id FROM movies WHERE name=?");
                    stmt.setString(1, selectedMovie);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        int movieId = rs.getInt("id");
                        new BookingPage(username, movieId); // Pass username and movieId
                        dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a movie.");
            }
        });

        setLayout(null);
        setVisible(true);
    }
}
