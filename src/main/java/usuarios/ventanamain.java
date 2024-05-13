package usuarios;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ventanamain extends JFrame {
    private ExperimentManager experimentManager;

    public ventanamain() {
        experimentManager = new ExperimentManager();

        setTitle("Gestor de cultivo de bacterias");
        setSize(800, 600); // Ajusta el tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Crear un nuevo botón para abrir la ventana de agregar población
        JButton addExperimentButton = new JButton("Agregar experimento");
        addExperimentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana window = new ventana(experimentManager);
                window.setVisible(true);
            }
        });

        // Crear un nuevo botón para eliminar experimentos
        JButton deleteExperimentButton = new JButton("Eliminar experimento");
        deleteExperimentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la funcionalidad para eliminar experimentos
            }
        });

        // Crear un nuevo botón para guardar los experimentos en un archivo
        JButton saveExperimentsButton = new JButton("Guardar experimentos");
        saveExperimentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la funcionalidad para guardar los experimentos en un archivo
            }
        });

        // Crear un nuevo botón para cargar los experimentos desde un archivo
        JButton loadExperimentsButton = new JButton("Cargar experimentos");
        loadExperimentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select the experiment to load");
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = fileChooser.getSelectedFile();
                    try (Scanner in = new Scanner(fileToLoad)) {
                        String populationName = in.nextLine().split(": ")[1];
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        Date startDate = formatter.parse(in.nextLine().split(": ")[1]);
                        Date endDate = formatter.parse(in.nextLine().split(": ")[1]);
                        int initialBacteriaCount = Integer.parseInt(in.nextLine().split(": ")[1]);
                        double temp = Double.parseDouble(in.nextLine().split(": ")[1]);
                        String lightConditions = in.nextLine().split(": ")[1];
                        int initialFoodAmount = Integer.parseInt(in.nextLine().split(": ")[1]);
                        int foodIncreaseDay = Integer.parseInt(in.nextLine().split(": ")[1]);
                        int foodOnIncreaseDay = Integer.parseInt(in.nextLine().split(": ")[1]);
                        int finalFoodAmountOnDay30 = Integer.parseInt(in.nextLine().split(": ")[1]);
                        Food foodOrganization = new Food(initialFoodAmount, foodIncreaseDay, foodOnIncreaseDay, finalFoodAmountOnDay30);
                        BacteriaPopulation bacteriaPopulation = new BacteriaPopulation(populationName, startDate, endDate, initialBacteriaCount, temp, lightConditions, foodOrganization);
                        experimentManager.addBacteriaPopulation(bacteriaPopulation);
                        JOptionPane.showMessageDialog(null, "Experiment loaded.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FileNotFoundException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Crear un nuevo botón para cerrar la aplicación
        JButton closeButton = new JButton("Cerrar aplicación");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Crear un panel para los botones y agregar los botones a este panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addExperimentButton);
        buttonPanel.add(deleteExperimentButton);
        buttonPanel.add(saveExperimentsButton);
        buttonPanel.add(loadExperimentsButton);
        buttonPanel.add(closeButton);

        // Agregar el panel de botones a la ventana
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ventanamain().setVisible(true);
            }
        });
    }
}