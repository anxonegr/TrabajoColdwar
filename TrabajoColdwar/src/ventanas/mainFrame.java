package ventanas;

import java.awt.Image;
import javax.swing.*;

public class mainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel fondoPanel;

    public mainFrame() {
        setTitle("ColdWar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        mostrarMenuPrincipal();
        setVisible(true);
    }

    // ── Menú principal ────────────────────────────────────────────────────────
    public void mostrarMenuPrincipal() {
        getContentPane().removeAll();

        Image img = new ImageIcon("recurso/fondo_inicio.png")
                        .getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        fondoPanel = new JLabel(new ImageIcon(img));
        fondoPanel.setBounds(0, 0, 900, 600);
        fondoPanel.setLayout(null);
        add(fondoPanel);

        agregarBoton("recurso/jugar.png",    325, 170, e -> mostrarCrearEquipos());
        agregarBoton("recurso/reglas.png",   325, 220, e -> mostrarReglas());
        agregarBoton("recurso/creditos.png", 325, 270, e -> new VentanaCreditos());
        agregarBoton("recurso/ranking.png",  325, 310, e -> mostrarRanking());
        agregarBoton("recurso/salir.png",    325, 380, e -> System.exit(0));
        
     // ── Botón música ──────────────────────────────────────────────────────────
        ImageIcon iconoMusica      = new ImageIcon("recurso/musica.png");
        ImageIcon iconoMusicaMuted = new ImageIcon("recurso/musicamuted.png");

        Image imgMusica = iconoMusica.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image imgMuted  = iconoMusicaMuted.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JButton btnMusica = new JButton(new ImageIcon(imgMusica));
        btnMusica.setBounds(5, 55, 50, 50);
        btnMusica.setBorderPainted(false);
        btnMusica.setContentAreaFilled(false);
        btnMusica.setFocusPainted(false);
        btnMusica.setOpaque(false);

        // Guardamos el estado en un array de 1 elemento para poder usarlo dentro del lambda
        boolean[] muteado = {false};

        btnMusica.addActionListener(e -> {
            muteado[0] = !muteado[0];
            if (muteado[0]) {
                btnMusica.setIcon(new ImageIcon(imgMuted));
            } else {
                btnMusica.setIcon(new ImageIcon(imgMusica));
            }
        });

        fondoPanel.add(btnMusica);
        
        revalidate();
        repaint();
    }

    public void mostrarRanking() {
        getContentPane().removeAll();
        VentanaRanking panel = new VentanaRanking(this);
        panel.setBounds(0, 0, 900, 600);
        add(panel);
        revalidate();
        repaint();
    }

    // ── Pantalla Crear Equipos ────────────────────────────────────────────────
    public void mostrarCrearEquipos() {
        getContentPane().removeAll();

        // VentanaCrearEquipos pinta su propio fondo en paintComponent
        VentanaCrearEquipos panel = new VentanaCrearEquipos(this);
        panel.setBounds(0, 0, 900, 600);
        add(panel);

        revalidate();
        repaint();
    }
    
    public void mostrarReglas() {
        getContentPane().removeAll();
        VentanaReglas panel = new VentanaReglas(this);
        panel.setBounds(0, 0, 900, 600);
        add(panel);
        revalidate();
        repaint();
    }

    // ── Helper botón imagen ───────────────────────────────────────────────────
    private void agregarBoton(String ruta, int x, int y, java.awt.event.ActionListener accion) {
        JButton btn = new JButton();
        btn.setBounds(x, y, 250, 45);
        Image imgEsc = new ImageIcon(ruta).getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(imgEsc));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.addActionListener(accion);
        fondoPanel.add(btn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainFrame());
    }
}