
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

public class bacteriaven extends JFrame {
    private manejoesp_Impl manejoEx;
    private JTextField[] textFields;
    private String[] labels = {"Nombre de la población:",
            "Fecha de comienzo (dd/mm/aaaa ):",
            "Fecha de fin (dd/mm/aaaa ):",
            "Número de bacterias iniciales:",
            "Temperatura máxima a la que son sometidas las bacterias (°C):",
            "Condiciones de luminosidad (Alta, Media, Baja):",
            "Cantidad inicial de comida (no más de 300):",
            "Día hasta el cual se incrementa la comida (no más de 30):",
            "Comida en el día de incremento (no más de 300):",
            "Cantidad final de comida en el día 30 (no más de 300):"};

    public bacteriaven(manejoesp_Impl manejoExperimento) {
        this.manejoEx = manejoEx;

        setTitle("Añadir población de bacterias");
        setSize(850, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(10, 2));
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
        JButton saveButton = new JButton("Guardar");
        JButton saveAsButton = new JButton("Guardar como");
        JButton backButton = new JButton("Volver al menú principal");
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Mostrar un mensaje que indica que los cambios han sido guardados
                JOptionPane.showMessageDialog(null, "Cambios guardados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione dónde guardar el experimento");
                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    bacteria poblacion = createPopulationFromInput();
                    if (poblacion != null) {
                        // Escribir la información de la población en el archivo seleccionado
                        try (PrintWriter out = new PrintWriter(new FileOutputStream(fileToSave, true))) {
                            out.println("Información de la población:");
                            out.println("Nombre: " + poblacion.getNombre());
                            out.println("Fecha de inicio: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(poblacion.getStartDate()));
                            out.println("Fecha de fin: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(poblacion.getEndDate()));
                            out.println("Número de bacterias iniciales: " + poblacion.getInitialBacteriaCount());
                            out.println("Temperatura máxima a la que son sometidas las bacterias (°C): " + poblacion.getTemp());
                            out.println("Condiciones de luminosidad: " + poblacion.getCondicionesLuminosidad());
                            out.println("Organización de comida: " + poblacion.getOrganizacionComida());
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

    public bacteria createPopulationFromInput() {
        try {
            String populationName = textFields[0].getText();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date startDate = formatter.parse(textFields[1].getText());
            Date endDate = formatter.parse(textFields[2].getText());

            // Comprobar que la duración de la población es exactamente de 30 días
            long duration = endDate.getTime() - startDate.getTime();
            long durationInDays = TimeUnit.MILLISECONDS.toDays(duration);
            if (durationInDays != 30) {
                JOptionPane.showMessageDialog(null, "La duración de la población debe ser exactamente de 30 días.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            int initialBacteriaCount = Integer.parseInt(textFields[3].getText());
            double temp = Double.parseDouble(textFields[4].getText());
            String condicionesLuminosidad = textFields[5].getText();
            int cantidadInicialComida = Integer.parseInt(textFields[6].getText());
            int diaIncrementoComida = Integer.parseInt(textFields[7].getText());
            int comidaDiaIncremento = Integer.parseInt(textFields[8].getText());
            int cantidadFinalComidaDia30 = Integer.parseInt(textFields[9].getText());
            comida organizacionComida = new comida(cantidadInicialComida, diaIncrementoComida, comidaDiaIncremento, cantidadFinalComidaDia30);
            return new bacteria(populationName, startDate, endDate, initialBacteriaCount, temp, condicionesLuminosidad, organizacionComida);
        } catch (ParseException parseException) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese las fechas en el formato correcto (dd/MM/yyyy HH:mm:ss).", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese los números en el formato correcto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
