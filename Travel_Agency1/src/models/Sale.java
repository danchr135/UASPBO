package models;

public class Sale {
    private int id;
    private int journeyId;
    private String saleDate;
    private int ticketsSold;
    private double totalPrice;

    // Constructor
    public Sale(int id, int journeyId, String saleDate, int ticketsSold, double totalPrice) {
        this.id = id;
        this.journeyId = journeyId;
        this.saleDate = saleDate;
        this.ticketsSold = ticketsSold;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
