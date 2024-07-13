package models;

public class Booking {
    private int id;
    private int journeyId;
    private String customerName;
    private String customerEmail;
    private int ticketsBooked;
    private double totalPrice;

    // Constructor
    public Booking(int id, int journeyId, String customerName, String customerEmail, int ticketsBooked, double totalPrice) {
        this.id = id;
        this.journeyId = journeyId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.ticketsBooked = ticketsBooked;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(int ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
