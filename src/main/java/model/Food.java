package model;

public class Food {
    private String name;
    private int quantity;
    private int day;
    private String experimentName;

    public Food() {
        if (quantity < 0 || quantity >= 300) {
            throw new IllegalArgumentException("Quantity must be an integer less than 300");
        }
        this.name = name;
        this.quantity = quantity;
        this.day = day;
        this.experimentName = experimentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0 || quantity >= 300) {
            throw new IllegalArgumentException("Quantity must be an integer less than 300");
        }
        this.quantity = quantity;
    }

    public int getDay() {
        return day;
    }


    public void setDay(int day) {
        this.day = day;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }
}