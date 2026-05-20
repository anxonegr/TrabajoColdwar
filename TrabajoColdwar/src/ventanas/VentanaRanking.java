package ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ranking extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public Ranking() {

        setTitle("RANKING");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // panel con fondo
        JPanel panel = new JPanel(new BorderLayout()) {

            Image fondo = new ImageIcon("recurso/fondo.png").getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // los paneles encima del fondo tienen que ser transparentes
        panel.setOpaque(false);

        // Imagen ranking
        ImageIcon icono = new ImageIcon("recurso/ranking.png");

        JLabel titulo = new JLabel(icono);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        panel.add(titulo, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel();
        modelo.addColumn("PLANETA");
        modelo.addColumn("IDENTIFICADOR");

        tabla = new JTable(modelo);

        tabla.setBackground(Color.WHITE);
        tabla.setForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(400, 250));
        
        tabla.getTableHeader().setOpaque(true);
        tabla.getTableHeader().setBackground(Color.ORANGE);
        tabla.getTableHeader().setForeground(Color.BLACK);

        // CENTRO TRANSPARENTE
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        centro.add(scroll);

        panel.add(centro, BorderLayout.CENTER);

        // Botón volver
        ImageIcon iconoFlecha = new ImageIcon(
                new ImageIcon("recurso/flechaizq.png")
                        .getImage()
                        .getScaledInstance(124, 70, Image.SCALE_SMOOTH));

        JButton volver = new JButton(iconoFlecha);
        volver.setContentAreaFilled(false);
        volver.setBorderPainted(false);
        volver.setFocusPainted(false);
        volver.setOpaque(false);

        volver.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);

        panelBoton.add(volver);

        panel.add(panelBoton, BorderLayout.SOUTH);

        add(panel);

        cargarGanadores();
    }

    // Cargar archivo
    private void cargarGanadores() {

        String ruta = "src/fichero/ganadores.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(" - ");

                if (partes.length == 2) {
                    modelo.addRow(new Object[]{
                            partes[0],
                            partes[1]
                    });
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ranking");
        }
    }
}
