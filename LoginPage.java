import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField userText;
    private JPasswordField passText;

    public LoginPage() {
        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center screen
        setLayout(null);
        getContentPane().setBackground(new Color(44, 62, 80));

        JLabel titleLabel = new JLabel("Movie Ticket Booking - Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(90, 30, 350, 30);
        add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userLabel.setBounds(100, 100, 100, 25);
        add(userLabel);

        userText = new JTextField();
        userText.setBounds(200, 100, 180, 30);
        add(userText);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passLabel.setBounds(100, 150, 100, 25);
        add(passLabel);

        passText = new JPasswordField();
        passText.setBounds(200, 150, 180, 30);
        add(passText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(120, 220, 100, 40);
        loginButton.setBackground(new Color(52, 152, 219));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(240, 220, 120, 40);
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new ShowListPage(username); // Navigate to show list
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new RegisterPage(); // Open registration page
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private boolean authenticate(String username, String password) {
        boolean valid = false;
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            // Debugging line
            System.out.println("Username: " + username + ", Password: " + password);

            valid = rs.next();  // If the user exists in the DB, valid will be true

            if (valid) {
                System.out.println("Login successful!");  // If login is successful, show success message
            } else {
                System.out.println("Invalid username or password");  // If no matching records
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
