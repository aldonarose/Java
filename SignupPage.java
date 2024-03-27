
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JFrame implements ActionListener {
    private JTextField firstNameField, lastNameField, ageField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton signupButton, backButton; 

    public SignupPage() {
        setTitle("Sign Up Page");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx++;
        firstNameField = new JTextField(15);
        panel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx++;
        lastNameField = new JTextField(15);
        panel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx++;
        ageField = new JTextField(15);
        panel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx++;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx++;
        confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        signupButton = new JButton("Sign Up");
        signupButton.addActionListener(this);
        panel.add(signupButton, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(this); 
        gbc.gridx++;
        panel.add(backButton, gbc);

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String email = emailField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

            if (isValidSignup(firstName, lastName, age, email, password, confirmPassword)) {
                if (insertIntoDatabase(firstName, lastName, age, email, password)) {
                    JOptionPane.showMessageDialog(this, "Sign Up Successful! User data inserted into the database.");
                    LoginPage1 login = new LoginPage1();
                    login.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert user data into the database.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid signup details. Please check and try again.");
            }
        } else if (e.getSource() == backButton) { 
            LoginPage1 login = new LoginPage1();
            login.setVisible(true);
            dispose();
        }
    }

    private boolean isValidSignup(String firstName, String lastName, int age, String email, String password, String confirmPassword) {
        return !firstName.isEmpty() && !lastName.isEmpty() && age > 0 && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword);
    }

    private boolean insertIntoDatabase(String firstName, String lastName, int age, String email, String password) {
        String sql = "INSERT INTO users (first_name, last_name, age, email, password) VALUES (?, ?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_authentications", "root", "aldo_365");
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setInt(3, age);
                stmt.setString(4, email);
                stmt.setString(5, password);
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignupPage();
            }
        });
    }
}
