package ;

import model.ExperimentManager;
import model.ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ventanamain extends JFrame {
    private ExperimentManager experimentManager;

    public MainFrame() {
        experimentManager = new ExperimentManager();

        setTitle("Gestor de cultivo de bacterias");
        setSize(800, 600); // Ajusta el tamaño de la ventana
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ventana mainPanel = new ventana(experimentManager);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Crear un nuevo botón para abrir la ventana de agregar población
        JButton addPopulationButton = new JButton("Agregar población");
        addPopulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana window = new ventana(experimentManager);
                window.setVisible(true);
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
        buttonPanel.add(addPopulationButton);
        buttonPanel.add(closeButton);

        // Agregar el panel de botones al panel principal
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}