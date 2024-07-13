package models;

import java.text.NumberFormat;
import java.util.Locale;

public class Journey {
    private int id;
    private String route;
    private String schedule;
    private double price;
    private int availableTickets;

    // Constructor
    public Journey(int id, String route, String schedule, double price, int availableTickets) {
        this.id = id;
        this.route = route;
        this.schedule = schedule;
        this.price = price;
        this.availableTickets = availableTickets;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    @Override
    public String toString() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return route + " - " + schedule + " - " + currencyFormatter.format(price) + " - Available: " + availableTickets;
    }
}
