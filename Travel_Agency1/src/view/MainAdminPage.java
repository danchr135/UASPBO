package view;

import javax.swing.*;
import java.awt.*;

public class MainAdminPage extends JFrame {

    public MainAdminPage() {
        setTitle("Admin - Manage Tickets");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manage Journeys", new ManageJourneyPanel());
        tabbedPane.addTab("Reports", new ReportPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainAdminPage frame = new MainAdminPage();
            frame.setVisible(true);
        });
    }
}
