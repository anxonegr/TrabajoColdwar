package ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaResumen extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaResumen(int ronda, String logRonda, String estadoEquipos) {

        setTitle("Resumen de Ronda");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel(
                "===== RESUMEN RONDA " + ronda + " =====",
                SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        // Historial de la ronda
        JTextArea areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setText(logRonda);

        JScrollPane scroll = new JScrollPane(areaLog);

        add(scroll, BorderLayout.CENTER);

        // Estado de equipos
        JTextArea areaEstado = new JTextArea();
        areaEstado.setEditable(false);
        areaEstado.setText(estadoEquipos);

        areaEstado.setBorder(
                BorderFactory.createTitledBorder("Estado actual de los equipos"));

        // Botón continuar
        JButton btnContinuar = new JButton("Continuar");

        btnContinuar.addActionListener(e -> dispose());

        JPanel panelSur = new JPanel(new BorderLayout());

        panelSur.add(areaEstado, BorderLayout.CENTER);
        panelSur.add(btnContinuar, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        setVisible(true);
    }
}
