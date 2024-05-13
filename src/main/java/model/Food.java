package model;

public class Food {

    private int initialFoodAmount;
    private int increaseUntilDay;
    private int foodAmountOnIncreaseDay;
    private int decreaseStartDay;
    private int finalFoodAmount;

    public Food(int initialFoodAmount, int increaseUntilDay, int foodAmountOnIncreaseDay, int decreaseStartDay, int finalFoodAmount) {
        this.initialFoodAmount = initialFoodAmount;
        this.increaseUntilDay = increaseUntilDay;
        this.foodAmountOnIncreaseDay = foodAmountOnIncreaseDay;
        this.decreaseStartDay = decreaseStartDay;
        this.finalFoodAmount = finalFoodAmount;
    }

    public int getInitialFoodAmount() {
        return this.initialFoodAmount;
    }

    public int getIncreaseUntilDay() {
        return this.increaseUntilDay;
    }

    public int getFoodAmountOnIncreaseDay() {
        return this.foodAmountOnIncreaseDay;
    }

    public int getDecreaseStartDay() {
        return this.decreaseStartDay;
    }

    public int getFinalFoodAmount() {
        return this.finalFoodAmount;
    }

    public double getFoodAmountOnDay(int day) {
        double[] foodPerDay = calculateFood();
        return foodPerDay[day - 1];
    }

    private double[] calculateFood() {
        double[] foodPerDay = new double[30];
        foodPerDay[0] = initialFoodAmount;

        double dailyIncrease1 = (foodAmountOnIncreaseDay - initialFoodAmount) / (double)(increaseUntilDay - 1);
        for (int i = 1; i < increaseUntilDay; i++) {
            foodPerDay[i] = foodPerDay[i - 1] + dailyIncrease1;
        }

        double dailyIncrease2 = (finalFoodAmount - foodAmountOnIncreaseDay) / (double)(decreaseStartDay - increaseUntilDay);
        for (int i = increaseUntilDay; i < decreaseStartDay; i++) {
            foodPerDay[i] = foodPerDay[i - 1] + dailyIncrease2;
        }

        double dailyDecrease = (finalFoodAmount - foodPerDay[decreaseStartDay - 1]) / (double)(30 - decreaseStartDay);
        for (int i = decreaseStartDay; i < 30; i++) {
            foodPerDay[i] = foodPerDay[i - 1] - dailyDecrease;
        }

        return foodPerDay;
    }

    public void printAndShowFood() {
        double[] foodPerDay = calculateFood();
        StringBuilder foodPerDayStr = new StringBuilder();
        for (int i = 0; i < foodPerDay.length; i++) {
            foodPerDayStr.append("Day ").append(i + 1).append(": ").append(foodPerDay[i]).append("\n");
        }
        System.out.println(foodPerDayStr.toString());
    }

    @Override
    public String toString() {
        return "{" +
                "Initial food amount=" + initialFoodAmount +
                ", Day until which food increases=" + increaseUntilDay +
                ", Food on increase day=" + foodAmountOnIncreaseDay +
                ", Day when food starts to decrease=" + decreaseStartDay +
                ", Final food amount on day 30=" + finalFoodAmount +
                '}';
    }
}