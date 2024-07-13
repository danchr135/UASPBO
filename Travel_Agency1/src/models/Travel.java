package models;

public class Travel {
    private int id;
    private String origin;
    private String destination;
    private String schedule;
    private double price;

    public Travel(int id, String origin, String destination, String schedule, double price) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.schedule = schedule;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
