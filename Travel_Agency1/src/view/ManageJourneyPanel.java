package view;

import models.Journey;
import view.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageJourneyPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField routeField, scheduleField, priceField, availableTicketsField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private JTextField searchField;

    public ManageJourneyPanel() {
        setLayout(new BorderLayout());

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Route", "Schedule", "Price", "Available Tickets"}, 0);
        table = new JTable(tableModel);
        loadJourneyData();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Route:"));
        routeField = new JTextField();
        inputPanel.add(routeField);

        inputPanel.add(new JLabel("Schedule:"));
        scheduleField = new JTextField();
        inputPanel.add(scheduleField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Available Tickets:"));
        availableTicketsField = new JTextField();
        inputPanel.add(availableTicketsField);

        add(inputPanel, BorderLayout.NORTH);

        // Panel Button
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addJourney();
            }
        });
        buttonPanel.add(addButton);

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editJourney();
            }
        });
        buttonPanel.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteJourney();
            }
        });
        buttonPanel.add(deleteButton);

        searchButton = new JButton("Search");
        searchField = new JTextField();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchJourney();
            }
        });
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadJourneyData() {
        Connection conn = DatabaseConnection.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM journeys");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("route"),
                        rs.getString("schedule"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addJourney() {
        String route = routeField.getText();
        String schedule = scheduleField.getText();
        double price = Double.parseDouble(priceField.getText());
        int availableTickets = Integer.parseInt(availableTicketsField.getText());

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO journeys (route, schedule, price, available_tickets) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, route);
            pst.setString(2, schedule);
            pst.setDouble(3, price);
            pst.setInt(4, availableTickets);
            pst.executeUpdate();
            tableModel.addRow(new Object[]{getLastInsertedId(conn), route, schedule, price, availableTickets});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLastInsertedId(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    private void editJourney() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String route = routeField.getText();
            String schedule = scheduleField.getText();
            double price = Double.parseDouble(priceField.getText());
            int availableTickets = Integer.parseInt(availableTicketsField.getText());

            Connection conn = DatabaseConnection.getConnection();
            try {
                String query = "UPDATE journeys SET route = ?, schedule = ?, price = ?, available_tickets = ? WHERE id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, route);
                pst.setString(2, schedule);
                pst.setDouble(3, price);
                pst.setInt(4, availableTickets);
                pst.setInt(5, id);
                pst.executeUpdate();

                tableModel.setValueAt(route, selectedRow, 1);
                tableModel.setValueAt(schedule, selectedRow, 2);
                tableModel.setValueAt(price, selectedRow, 3);
                tableModel.setValueAt(availableTickets, selectedRow, 4);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a journey to edit");
        }
    }

    private void deleteJourney() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);

            Connection conn = DatabaseConnection.getConnection();
            try {
                String query = "DELETE FROM journeys WHERE id = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, id);
                pst.executeUpdate();

                tableModel.removeRow(selectedRow);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a journey to delete");
        }
    }

    private void searchJourney() {
        String search = searchField.getText();
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM journeys WHERE route LIKE ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, "%" + search + "%");
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("route"),
                        rs.getString("schedule"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
