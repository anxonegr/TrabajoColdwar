package ventanas;

import javax.swing.*;
import java.awt.*;

public class VentanaResumen extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaResumen(String texto) {

        setTitle("Resumen de ronda");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText(texto);

        JScrollPane scroll = new JScrollPane(area);

        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
