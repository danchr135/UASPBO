package view;

import javax.swing.*;
import java.awt.*;

public class MainUserPage extends JFrame {

    public MainUserPage() {
        setTitle("User - Book Tickets");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new TicketBookingPanel(), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUserPage frame = new MainUserPage();
            frame.setVisible(true);
        });
    }
}
