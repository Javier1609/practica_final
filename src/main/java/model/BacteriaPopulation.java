package model;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BacteriaPopulation implements Serializable {
    private String name;
    private Date startDate;
    private Date endDate;
    private int initialBacteriaCount;
    private double temp;
    private String lightConditions;
    private Food foodOrganization;

    public BacteriaPopulation(String name, Date startDate, Date endDate, int initialBacteriaCount, double temp, String lightConditions, Food foodOrganization) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (initialBacteriaCount < 0) {
            throw new IllegalArgumentException("Initial bacteria count cannot be negative");
        }
        if (temp < -273.15) { // Absolute zero in Celsius
            throw new IllegalArgumentException("Temperature cannot be below absolute zero");
        }
        if (lightConditions == null || lightConditions.isEmpty()) {
            throw new IllegalArgumentException("Light conditions cannot be null or empty");
        }
        if (foodOrganization == null) {
            throw new IllegalArgumentException("Food organization cannot be null");
        }

        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.initialBacteriaCount = initialBacteriaCount;
        this.temp = temp;
        this.lightConditions = lightConditions;
        this.foodOrganization = foodOrganization;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getInitialBacteriaCount() {
        return initialBacteriaCount;
    }

    public double getTemp() {
        return temp;
    }

    public String getLightConditions() {
        return lightConditions;
    }

    public Food getFoodOrganization() {
        return foodOrganization;
    }

    public long getDurationInDays() {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toDays(duration);
    }

    @Override
    public String toString() {
        return "BacteriaPopulation{" +
                "name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", initialBacteriaCount=" + initialBacteriaCount +
                ", temp=" + temp +
                ", lightConditions='" + lightConditions + '\'' +
                ", foodOrganization=" + foodOrganization +
                '}';
    }
}