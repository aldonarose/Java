import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage1 extends JFrame implements ActionListener {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    public LoginPage1() {
        setTitle("Login Page");
        setSize(800, 1050);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("CHRIST HOSPITAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(300, 100, 200, 50);
        panel.add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(300, 440, 200, 50);
        panel.add(emailLabel);

        emailField = new JTextField(15);
        emailField.setBounds(350, 450, 200, 30);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(275, 500, 70, 20);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(350, 495, 200, 30);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(350, 550, 80, 30);
        panel.add(loginButton);

        signupButton = new JButton("Sign Up");
        signupButton.addActionListener(this);
        signupButton.setBounds(350, 600, 80, 30);
        panel.add(signupButton);
        
        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("medicalcentre.jpg"));
        JLabel image = new JLabel(il);
        image.setBounds(10, 140, il.getIconWidth(), il.getIconHeight());
        panel.add(image);

        setContentPane(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if (isValidLogin(email, password)) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_authentications", "root", "aldo_365")) {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("select email, password from users");
                        boolean found = false;
                        while (rs.next()) {
                            String dbEmail = rs.getString("email");
                            String dbPassword = rs.getString("password");
                            if (email.equals(dbEmail) && password.equals(dbPassword)) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            JOptionPane.showMessageDialog(this, "Login Successful!");
                            P p = new P();
                            ((Window) p).setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.");
                        }
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(this, "An error occurred. Please try again later.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email and password cannot be empty.");
            }
        } else if (e.getSource() == signupButton) {
            SignupPage signupPage = new SignupPage();
            signupPage.setVisible(true);
            dispose();
        }
    }

    private boolean isValidLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    public static void main(String[] args) {
        new LoginPage1();
    }
}