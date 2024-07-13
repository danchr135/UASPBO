package view;

import models.Booking;
import view.DatabaseConnection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton dailyReportButton, monthlyReportButton, transactionReportButton, exportCsvButton, exportPdfButton;

    public ReportPanel() {
        setLayout(new BorderLayout());

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Journey ID", "Customer Name", "Customer Email", "Tickets Booked", "Total Price", "Sale Date"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel Button
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        dailyReportButton = new JButton("Daily Report");
        dailyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateDailyReport();
            }
        });
        buttonPanel.add(dailyReportButton);

        monthlyReportButton = new JButton("Monthly Report");
        monthlyReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMonthlyReport();
            }
        });
        buttonPanel.add(monthlyReportButton);

        transactionReportButton = new JButton("Transaction Report");
        transactionReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateTransactionReport();
            }
        });
        buttonPanel.add(transactionReportButton);

        exportCsvButton = new JButton("Export CSV");
        exportCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToCsv();
            }
        });
        buttonPanel.add(exportCsvButton);

        exportPdfButton = new JButton("Export PDF");
        exportPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToPdf();
            }
        });
        buttonPanel.add(exportPdfButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateDailyReport() {
        tableModel.setRowCount(0);
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM bookings WHERE sale_date = CURDATE()";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("journey_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getInt("tickets_booked"),
                        rs.getDouble("total_price"),
                        rs.getDate("sale_date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateMonthlyReport() {
        tableModel.setRowCount(0);
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM bookings WHERE MONTH(sale_date) = MONTH(CURDATE()) AND YEAR(sale_date) = YEAR(CURDATE())";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("journey_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getInt("tickets_booked"),
                        rs.getDouble("total_price"),
                        rs.getDate("sale_date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateTransactionReport() {
        tableModel.setRowCount(0);
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM bookings";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("journey_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getInt("tickets_booked"),
                        rs.getDouble("total_price"),
                        rs.getDate("sale_date")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exportToCsv() {
        try {
            String filePath = "D:/report.csv";
            FileWriter csvWriter = new FileWriter(filePath);
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                csvWriter.append(tableModel.getColumnName(i));
                csvWriter.append(",");
            }
            csvWriter.append("\n");

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    csvWriter.append(tableModel.getValueAt(i, j).toString());
                    csvWriter.append(",");
                }
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
            JOptionPane.showMessageDialog(this, "Export to CSV successful! File saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Export to CSV failed!");
        }
    }

    private void exportToPdf() {
        String filePath = "D:/report.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                document.add(new Paragraph(tableModel.getColumnName(i)));
            }
            document.add(new Paragraph("\n"));

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    document.add(new Paragraph(tableModel.getValueAt(i, j).toString()));
                }
                document.add(new Paragraph("\n"));
            }
            document.close();
            JOptionPane.showMessageDialog(this, "Export to PDF successful! File saved to " + filePath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Export to PDF failed!");
        }
    }
}
