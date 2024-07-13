package view;

import models.Booking;
import models.Journey;
import view.DatabaseConnection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TicketBookingPanel extends JPanel {
    private JComboBox<Journey> journeyComboBox;
    private JTextField customerNameField, customerEmailField, ticketsBookedField;
    private JLabel totalPriceLabel;
    private JButton bookButton, printButton;
    private List<Journey> journeys;
    private Booking currentBooking;

    public TicketBookingPanel() {
        setLayout(new BorderLayout());

        // Load journeys from database
        journeys = loadJourneys();

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Select Journey:"));
        journeyComboBox = new JComboBox<>(journeys.toArray(new Journey[0]));
        inputPanel.add(journeyComboBox);

        inputPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        inputPanel.add(new JLabel("Customer Email:"));
        customerEmailField = new JTextField();
        inputPanel.add(customerEmailField);

        inputPanel.add(new JLabel("Tickets Booked:"));
        ticketsBookedField = new JTextField();
        ticketsBookedField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalPrice();
            }
        });
        inputPanel.add(ticketsBookedField);

        inputPanel.add(new JLabel("Total Price:"));
        totalPriceLabel = new JLabel();
        inputPanel.add(totalPriceLabel);

        add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookTicket();
            }
        });
        buttonPanel.add(bookButton);

        printButton = new JButton("Print");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTicket();
            }
        });
        buttonPanel.add(printButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<Journey> loadJourneys() {
        List<Journey> journeys = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM journeys");
            while (rs.next()) {
                journeys.add(new Journey(
                        rs.getInt("id"),
                        rs.getString("route"),
                        rs.getString("schedule"),
                        rs.getDouble("price"),
                        rs.getInt("available_tickets")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return journeys;
    }

    private void calculateTotalPrice() {
        Journey selectedJourney = (Journey) journeyComboBox.getSelectedItem();
        if (selectedJourney != null) {
            try {
                int ticketsBooked = Integer.parseInt(ticketsBookedField.getText());
                double totalPrice = selectedJourney.getPrice() * ticketsBooked;
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                totalPriceLabel.setText(currencyFormatter.format(totalPrice));
            } catch (NumberFormatException e) {
                totalPriceLabel.setText("Invalid number of tickets");
            }
        }
    }

    private void bookTicket() {
        Journey selectedJourney = (Journey) journeyComboBox.getSelectedItem();
        String customerName = customerNameField.getText();
        String customerEmail = customerEmailField.getText();
        int ticketsBooked;
        try {
            ticketsBooked = Integer.parseInt(ticketsBookedField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number of tickets!");
            return;
        }
        double totalPrice = selectedJourney.getPrice() * ticketsBooked;

        // Cek ketersediaan tiket
        if (ticketsBooked > selectedJourney.getAvailableTickets()) {
            JOptionPane.showMessageDialog(this, "Not enough tickets available!");
            return;
        }

        // Insert booking into database
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "INSERT INTO bookings (journey_id, customer_name, customer_email, tickets_booked, total_price, sale_date) VALUES (?, ?, ?, ?, ?, CURDATE())";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, selectedJourney.getId());
            pst.setString(2, customerName);
            pst.setString(3, customerEmail);
            pst.setInt(4, ticketsBooked);
            pst.setDouble(5, totalPrice);
            pst.executeUpdate();

            // Update available tickets in journeys table
            query = "UPDATE journeys SET available_tickets = available_tickets - ? WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, ticketsBooked);
            pst.setInt(2, selectedJourney.getId());
            pst.executeUpdate();

            selectedJourney.setAvailableTickets(selectedJourney.getAvailableTickets() - ticketsBooked);

            currentBooking = new Booking(getLastInsertedId(conn), selectedJourney.getId(), customerName, customerEmail, ticketsBooked, totalPrice);
            JOptionPane.showMessageDialog(this, "Booking successful!");
            updateJourneyComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Booking failed!");
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

    private void printTicket() {
        if (currentBooking != null) {
            String filePath = "D:/ticket.pdf";  // Ganti lokasi penyimpanan di drive D
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                document.add(new Paragraph("Ticket"));
                document.add(new Paragraph("Journey: " + currentBooking.getJourneyId()));
                document.add(new Paragraph("Customer Name: " + currentBooking.getCustomerName()));
                document.add(new Paragraph("Customer Email: " + currentBooking.getCustomerEmail()));
                document.add(new Paragraph("Tickets Booked: " + currentBooking.getTicketsBooked()));
                document.add(new Paragraph("Total Price: " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(currentBooking.getTotalPrice())));
                document.close();
                JOptionPane.showMessageDialog(this, "Ticket has been printed to " + filePath);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error printing ticket!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No booking available to print!");
        }
    }

    private void updateJourneyComboBox() {
        journeyComboBox.removeAllItems();
        for (Journey journey : journeys) {
            journeyComboBox.addItem(journey);
        }
    }
}
