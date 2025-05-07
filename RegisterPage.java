import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    private JTextField userText;
    private JPasswordField passText;

    public RegisterPage() {
        setTitle("Register");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(52, 73, 94));

        JLabel titleLabel = new JLabel("Register for Movie Tickets");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(100, 30, 350, 30);
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

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 220, 120, 40);
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        add(registerButton);

        JButton backButton = new JButton("Back to Login");
        backButton.setBounds(280, 220, 150, 40);
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(backButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                try {
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(null, "Registration Successful!");
                        new LoginPage(); // Open LoginPage
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Username already exists!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage(); // open login page
                dispose();
            }
        });

        setVisible(true);
    }

    private boolean registerUser(String username, String password) throws Exception {
        Connection conn = DBConnection.getConnection();

        String checkQuery = "SELECT * FROM users WHERE username=?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, username);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            rs.close();
            checkStmt.close();
            conn.close();
            return false; // User exists
        }

        rs.close();
        checkStmt.close();

        String insertQuery = "INSERT INTO users(username, password) VALUES (?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, username);
        insertStmt.setString(2, password);
        insertStmt.executeUpdate();

        insertStmt.close();
        conn.close();
        return true;
    }

    public static void main(String[] args) {
        new RegisterPage();
    }
}
