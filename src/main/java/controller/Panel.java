
package controller;

import model.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Panel extends JPanel {
    private manejoesp_Impl manejoExperimento;

    public Panel(manejoesp_Impl manejoExperimento) {
        this.manejoExperimento = manejoExperimento;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear y añadir el JLabel
        JLabel titleLabel = new JLabel("Gestor de Cultivo de bacterias", SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Ajusta el tamaño de la fuente aquí
        add(titleLabel, gbc);

        String[] actions = {"Agregar nuevo experimento", "Agregar población de bacterias", "Borrar población", "Ver información de la población", "Abrir experimento"};
        JComboBox<String> actionList = new JComboBox<>(actions);

        // Crear un panel adicional para el desplegable
        JPanel actionPanel = new JPanel();
        actionPanel.add(actionList);

        // Agregar el panel adicional al panel principal
        add(actionPanel, gbc);

        actionList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { // Cambia 'e' a 'actionEvent'
                String selectedAction = (String) actionList.getSelectedItem();
                List<experimento> experiments = manejoExperimento.getExperiments();

                JFileChooser fileChooser; // Declare fileChooser here
                int userSelection; // Declare userSelection here

                switch (selectedAction) {
                    case "Agregar nuevo experimento":
                        String experimentName = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo experimento:", "Nuevo experimento", JOptionPane.QUESTION_MESSAGE);
                        if (experimentName != null && !experimentName.trim().isEmpty()) {
                            experimento experiment = new experimento(experimentName, "Description here");
                            manejoExperimento.saveExperiment(experiment);

                            // Crear un archivo .txt para el experimento
                            fileChooser = new JFileChooser(); // Reuse fileChooser
                            fileChooser.setDialogTitle("Seleccione dónde guardar el experimento");
                            userSelection = fileChooser.showSaveDialog(null); // Reuse userSelection

                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                File fileToSave = fileChooser.getSelectedFile();
                                try (PrintWriter out = new PrintWriter(fileToSave + ".txt")) {
                                    out.println("Nombre del experimento: " + experimentName);
                                    out.println("Descripción: Description here");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case "Agregar población de bacterias":
                        bacteriaven bacteriaven = new bacteriaven(manejoExperimento);
                        bacteriaven.setLocationRelativeTo(null);
                        bacteriaven.setVisible(true);

                        // Cuando el usuario haga clic en el botón "Guardar como"
                        JButton saveAsButton = new JButton("Guardar como");
                        saveAsButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                JFileChooser fileChooser = new JFileChooser();
                                int returnValue = fileChooser.showSaveDialog(null);
                                if (returnValue == JFileChooser.APPROVE_OPTION) {
                                    File selectedFile = fileChooser.getSelectedFile();

                                    // Escribir la información de la población en el archivo seleccionado
                                    try (PrintWriter out = new PrintWriter(new FileOutputStream(selectedFile, true))) {
                                        bacteria poblacion = bacteriaven.createPopulationFromInput();
                                        if (poblacion != null) {
                                            out.println("Información de la población:");
                                            out.println("Nombre: " + poblacion.getNombre()); // Obtiene el nombre de la población directamente de la población
                                            out.println("Fecha de inicio: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(poblacion.getStartDate())); // Obtiene la fecha de inicio directamente de la población
                                            // Añade aquí el resto de la información de la población
                                        }
                                    } catch (FileNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        });
                        break;

                    case  "Borrar población":
                        fileChooser = new JFileChooser(); // Reuse fileChooser
                        fileChooser.setDialogTitle("Seleccione el experimento para borrar la población");
                        userSelection = fileChooser.showOpenDialog(null); // Reuse userSelection

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToOpen = fileChooser.getSelectedFile();
                            try (Scanner scanner = new Scanner(fileToOpen)) {
                                Map<String, String> populations = new HashMap<>();
                                String currentPopulation = null;
                                StringBuilder currentPopulationInfo = new StringBuilder();
                                while (scanner.hasNextLine()) {
                                    String line = scanner.nextLine();
                                    if (line.startsWith("Nombre: ")) {
                                        if (currentPopulation != null) {
                                            populations.put(currentPopulation, currentPopulationInfo.toString());
                                        }
                                        currentPopulation = line.substring(8);
                                        currentPopulationInfo.setLength(0);
                                    }
                                    if (currentPopulation != null) {
                                        currentPopulationInfo.append(line).append("\n");
                                    }
                                }
                                if (currentPopulation != null) {
                                    populations.put(currentPopulation, currentPopulationInfo.toString());
                                }
                                if (!populations.isEmpty()) {
                                    JComboBox<String> populationOptions = new JComboBox<>(populations.keySet().toArray(new String[0]));
                                    JOptionPane.showMessageDialog(null, populationOptions, "Seleccione una población", JOptionPane.QUESTION_MESSAGE);
                                    String selectedPopulation = (String) populationOptions.getSelectedItem();

                                    // Remove the selected population from the file
                                    populations.remove(selectedPopulation);
                                    try (PrintWriter out = new PrintWriter(fileToOpen)) {
                                        for (Map.Entry<String, String> entry : populations.entrySet()) {
                                            out.println("Nombre: " + entry.getKey());
                                            out.println(entry.getValue());
                                        }
                                    }
                                    JOptionPane.showMessageDialog(null, "La población ha sido eliminada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontraron poblaciones en el experimento.", "Poblaciones", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case "Abrir experimento":
                        fileChooser = new JFileChooser(); // Reuse fileChooser
                        fileChooser.setDialogTitle("Seleccione el experimento para abrir");
                        userSelection = fileChooser.showOpenDialog(null); // Reuse userSelection

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToOpen = fileChooser.getSelectedFile();
                            try (Scanner scanner = new Scanner(fileToOpen)) {
                                StringBuilder populations = new StringBuilder();
                                while (scanner.hasNextLine()) {
                                    String line = scanner.nextLine();
                                    if (line.startsWith("Nombre: ")) {
                                        populations.append(line.substring(8)).append(", ");
                                    }
                                }
                                if (populations.length() > 0) {
                                    populations.setLength(populations.length() - 2);  // Remove the last comma and space
                                    JOptionPane.showMessageDialog(null, "Poblaciones en el experimento: " + populations, "Poblaciones", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontraron poblaciones en el experimento.", "Poblaciones", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                    case "Ver información de la población":
                        fileChooser = new JFileChooser(); // Reuse fileChooser
                        fileChooser.setDialogTitle("Seleccione el experimento para abrir");
                        userSelection = fileChooser.showOpenDialog(null); // Reuse userSelection

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToOpen = fileChooser.getSelectedFile();
                            try (Scanner scanner = new Scanner(fileToOpen)) {
                                Map<String, String> populations = new HashMap<>();
                                String currentPopulation = null;
                                StringBuilder currentPopulationInfo = new StringBuilder();
                                while (scanner.hasNextLine()) {
                                    String line = scanner.nextLine();
                                    if (line.startsWith("Nombre: ")) {
                                        if (currentPopulation != null) {
                                            populations.put(currentPopulation, currentPopulationInfo.toString());
                                        }
                                        currentPopulation = line.substring(8);
                                        currentPopulationInfo.setLength(0);
                                    }
                                    if (currentPopulation != null) {
                                        currentPopulationInfo.append(line).append("\n");
                                    }
                                }
                                if (currentPopulation != null) {
                                    populations.put(currentPopulation, currentPopulationInfo.toString());
                                }
                                if (!populations.isEmpty()) {
                                    JComboBox<String> populationOptions = new JComboBox<>(populations.keySet().toArray(new String[0]));
                                    JOptionPane.showMessageDialog(null, populationOptions, "Seleccione una población", JOptionPane.QUESTION_MESSAGE);
                                    String selectedPopulation = (String) populationOptions.getSelectedItem();
                                    JOptionPane.showMessageDialog(null, populations.get(selectedPopulation), "Información de la población", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontraron poblaciones en el experimento.", "Poblaciones", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;
                }
            }
        });

    }

}
