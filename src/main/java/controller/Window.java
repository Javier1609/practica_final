package controller;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Window extends JFrame {
    private manejoesp_Impl manejoExperimento;

    public Window() {
        manejoExperimento = createManejoEspImplInstance();

        setTitle("Gestor de cultivo de bacterias");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new Panel(manejoExperimento);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Cerrar aplicación");
        closeButton.addActionListener(e -> System.exit(0));

        getContentPane().add(closeButton, BorderLayout.SOUTH);
    }

    private manejoesp_Impl createManejoEspImplInstance() {
        return new manejoesp_Impl() {
            @Override
            public void saveExperiment(manejoesp_Impl experiment) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("experiment.ser"))) {
                    oos.writeObject(experiment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }
}