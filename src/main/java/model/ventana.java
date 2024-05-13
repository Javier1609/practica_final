package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ventana  extends JFrame {
    private ExperimentManager experimentManager;
    private JTextField[] textFields;
    private String[] labels = {"Name of the population:",
            "Start date (dd/mm/yyyy hh:mm:ss):",
            "End date (dd/mm/yyyy hh:mm:ss):",
            "Initial number of bacteria:",
            "Maximum temperature to which the bacteria are subjected (°C):",
            "Light conditions (High, Medium, Low):",
            "Initial amount of food (no more than 300):",
            "Day until which food is increased (no more than 30):",
            "Food on the day of increase (no more than 300):",
            "Day when food starts to decrease (no more than 30):",
            "Final amount of food on day 30 (no more than 300):"};

    public ventana(ExperimentManager experimentManager) {
        this.experimentManager = experimentManager;

        setTitle("Add bacteria population");
        setSize(850, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(11, 2)); // Updated to 11 to accommodate the new field
        textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            JTextField textField = new JTextField();
            textFields[i] = textField;
            panel.add(label);
            panel.add(textField);
        }

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton saveAsButton = new JButton("Save as");
        JButton backButton = new JButton("Back to main menu");
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Show a message indicating that the changes have been saved
                JOptionPane.showMessageDialog(null, "Changes saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select where to save the experiment");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    BacteriaPopulation bacteriaPopulation = createPopulationFromInput();
                    if (bacteriaPopulation != null) {
                        // Write the population information to the selected file
                        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileToSave, true))) {
                            out.println("Population information:");
                            out.println("Name: " + bacteriaPopulation.getName());
                            out.println("Start date: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bacteriaPopulation.getStartDate()));
                            out.println("End date: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(bacteriaPopulation.getEndDate()));
                            out.println("Initial number of bacteria: " + bacteriaPopulation.getInitialBacteriaCount());
                            out.println("Maximum temperature to which the bacteria are subjected (°C): " + bacteriaPopulation.getTemp());
                            out.println("Light conditions: " + bacteriaPopulation.getLightConditions());
                            out.println("Food organization: " + bacteriaPopulation.getFoodOrganization());
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public BacteriaPopulation createPopulationFromInput() {
        try {
            String populationName = textFields[0].getText();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date startDate = formatter.parse(textFields[1].getText());
            Date endDate = formatter.parse(textFields[2].getText());

            // Check that the duration of the population is exactly 30 days
            long duration = endDate.getTime() - startDate.getTime();
            long durationInDays = TimeUnit.MILLISECONDS.toDays(duration);
            if (durationInDays != 30) {
                JOptionPane.showMessageDialog(null, "The duration of the population must be exactly 30 days.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            int initialBacteriaCount = Integer.parseInt(textFields[3].getText());
            double temp = Double.parseDouble(textFields[4].getText());
            String lightConditions = textFields[5].getText();
            int initialFoodAmount = Integer.parseInt(textFields[6].getText());
            int foodIncreaseDay = Integer.parseInt(textFields[7].getText());
            int foodOnIncreaseDay = Integer.parseInt(textFields[8].getText());
            int decreaseStartDay = Integer.parseInt(textFields[9].getText()); // New field
            int finalFoodAmountOnDay30 = Integer.parseInt(textFields[10].getText()); // Updated index
            Food foodOrganization = new Food(initialFoodAmount, foodIncreaseDay, foodOnIncreaseDay, decreaseStartDay, finalFoodAmountOnDay30);
            return new BacteriaPopulation(populationName, startDate, endDate, initialBacteriaCount, temp, lightConditions, foodOrganization);
        } catch (ParseException parseException) {
            JOptionPane.showMessageDialog(null, "Please enter the dates in the correct format (dd/MM/yyyy HH:mm:ss).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Please enter the numbers in the correct format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}