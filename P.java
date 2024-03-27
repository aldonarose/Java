import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class P extends JFrame implements ActionListener {
    private Object[][] doctors = {
            {"Dr. Aldona Rose Maria", "MBBS, MD (Orthopedic)", "Specializes in bone and joint disorders"},
            {"Dr. Allen Sabu", "MBBS, MD (Cardiology)", "Specializes in heart diseases"},
            {"Dr. Anakha Anil", "MBBS, MD (Pediatrics)", "Specializes in children's health"},
            {"Dr. Anna Vigilante", "MBBS, MD (Dermatology)", "Specializes in skin conditions"},
            {"Dr. Dhanya Bijith", "MBBS, MD (Neurology)", "Specializes in neurological disorders"},
            {"Dr. Vanessa Rodrigues", "MBBS, MS (Ophthalmology)", "Specializes in eye care"},
            {"Dr. Sri Meenakshi US", "MBBS, MD (Dermatology)", "Specializes in skin conditions"}
    };

    private String[] columnNames = {"Doctor", "Education", "Specialization"};
    private JTable doctorTable;
    private JButton btnBookAppointment;

    public P() {
        setTitle("Doctor List");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table to display doctors
        doctorTable = new JTable(new DefaultTableModel(doctors, columnNames));
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set layout
        setLayout(new BorderLayout());

        // Add the doctor table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button to book appointment
        btnBookAppointment = new JButton("Book Appointment");
        btnBookAppointment.addActionListener(this);
        add(btnBookAppointment, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBookAppointment) {
            int selectedRow = doctorTable.getSelectedRow();
            if (selectedRow != -1) {
               
                String doctorName = (String) doctorTable.getValueAt(selectedRow, 0);
                JOptionPane.showMessageDialog(this, "Booking appointment with " + doctorName);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a doctor to book an appointment.");
            }
        }
    }

    public static void main(String[] args) {
        new P();
    }
}
