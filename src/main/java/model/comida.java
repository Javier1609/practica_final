
package model;

import javax.swing.JOptionPane;

public class comida {

    private int initialFoodAmount;
    private int increaseUntilDay;
    private int foodAmountOnIncreaseDay;
    private int finalFoodAmount;

    public comida(int initialFoodAmount, int increaseUntilDay, int foodAmountOnIncreaseDay, int finalFoodAmount) {
        this.initialFoodAmount = initialFoodAmount;
        this.increaseUntilDay = increaseUntilDay;
        this.foodAmountOnIncreaseDay = foodAmountOnIncreaseDay;
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

    public int getFinalFoodAmount() {
        return this.finalFoodAmount;
    }

    public double[] calcularComida() {
        double[] comidaPorDia = new double[30];
        comidaPorDia[0] = initialFoodAmount;

        double incrementoDiario1 = (foodAmountOnIncreaseDay - initialFoodAmount) / (double)(increaseUntilDay - 1);
        for (int i = 1; i < increaseUntilDay; i++) {
            comidaPorDia[i] = comidaPorDia[i - 1] + incrementoDiario1;
        }

        double incrementoDiario2 = (finalFoodAmount - foodAmountOnIncreaseDay) / (double)(30 - increaseUntilDay);
        for (int i = increaseUntilDay; i < 30; i++) {
            comidaPorDia[i] = comidaPorDia[i - 1] + incrementoDiario2;
        }

        return comidaPorDia;
    }

    public void imprimirYMostrarComida() {
        double[] comidaPorDia = calcularComida();
        StringBuilder comidaPorDiaStr = new StringBuilder();
        for (int i = 0; i < comidaPorDia.length; i++) {
            comidaPorDiaStr.append("Día ").append(i + 1).append(": ").append(comidaPorDia[i]).append("\n");
        }
        System.out.println(comidaPorDiaStr.toString());
    }

    @Override
    public String toString() {
        return "{" +
                "Cantidad inicial de comida=" + initialFoodAmount +
                ", Día hasta el cual se incrementa la comida=" + increaseUntilDay +
                ", Comida en el día de incremento=" + foodAmountOnIncreaseDay +
                ", Cantidad final de comida en el día 30=" + finalFoodAmount +
                '}';
    }
}
