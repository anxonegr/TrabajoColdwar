package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCreditos extends JFrame {

    private static final long serialVersionUID = 1L;

    public VentanaCreditos() {
        setTitle("Créditos");
        setSize(627, 647);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fondo
        Image imagenFondo = new ImageIcon("recurso/fondocreditos.png")
                                .getImage().getScaledInstance(627, 647, Image.SCALE_SMOOTH);
        JLabel panelFondo = new JLabel(new ImageIcon(imagenFondo));
        panelFondo.setLayout(null);

        // Título créditos
        Image imagenTitulo = new ImageIcon("recurso/creditos.png")
                                 .getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        JLabel lblTitulo = new JLabel(new ImageIcon(imagenTitulo));
        lblTitulo.setBounds(205, 20, 200, 50);
        panelFondo.add(lblTitulo);

        // Versión
        JLabel lblVersion = new JLabel("Versión 3.0", SwingConstants.CENTER);
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblVersion.setForeground(Color.WHITE);
        lblVersion.setBounds(213, 72, 200, 20);
        panelFondo.add(lblVersion);

        // "Desarrollado por"
        JLabel creadoresTitulo = new JLabel("Desarrollado por:", SwingConstants.CENTER);
        creadoresTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        creadoresTitulo.setForeground(Color.WHITE);
        creadoresTitulo.setBounds(213, 110, 200, 25);
        panelFondo.add(creadoresTitulo);

        // Lista de autores
        String[] nombres = {
            "Anxo Negreira", "Miguel Riveiro", "Xoel Mauri",
            "Lukas Ouro", "Ruben González", "Enrique Martín", "Bieito Martínez"
        };

        javax.swing.border.Border bordeSimple = BorderFactory.createLineBorder(Color.BLACK, 2);
        int posicionY = 145;

        for (String nombre : nombres) {
            JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setBorder(bordeSimple);
            lblNombre.setBounds(183, posicionY, 260, 30);
            panelFondo.add(lblNombre);
            posicionY += 42;
        }

        // Contacto
        JLabel lblContacto = new JLabel("Contacto: xoelmauri@icloud.com", SwingConstants.CENTER);
        lblContacto.setFont(new Font("Arial", Font.ITALIC, 12));
        lblContacto.setForeground(Color.WHITE);
        lblContacto.setBounds(113, 470, 400, 25);
        panelFondo.add(lblContacto);

        // Botón salir
        Image imagenSalir = new ImageIcon("recurso/salir.png")
                                .getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH);
        JButton btnSalir = new JButton(new ImageIcon(imagenSalir));
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setBounds(238, 540, 150, 40);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelFondo.add(btnSalir);

        setContentPane(panelFondo);
        setVisible(true);
    }
}