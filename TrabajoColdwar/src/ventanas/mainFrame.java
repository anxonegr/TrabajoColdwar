package ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class mainFrame extends JFrame {

    private JButton btnJugar;
    private JButton btnReglas;
    private JButton btnInfo;
    private JButton btnBonus;
    private JButton btnGanadores;
    private JButton btnSalir;
    private JLabel fondo;

    public mainFrame() {
        // CONFIGURACIÓN VENTANA
        setTitle("ColdWar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Diseño libre

        // 1. Creamos y añadimos primero los elementos que van ARRIBA
        crearTitulo();
        crearBotones();
        
        // 2. Creamos y añadimos el fondo AL FINAL
        crearFondo();

        setVisible(true);
    }

    // =========================
    // TITULO (Directo al JFrame)
    // =========================
    public void crearTitulo() {
        JLabel titulo = new JLabel("");
        titulo.setBounds(400, 600, 500, 60);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo); // Añadido directamente al frame
    }

    // =========================
    // BOTONES (Directos al JFrame)
    // =========================
    public void crearBotones() {

        // BOTÓN JUGAR
        btnJugar = new JButton();
        btnJugar.setBounds(325, 170, 250, 45);
        // Corregida la ruta a una relativa estándar
        ImageIcon imgJugar = new ImageIcon("recurso/jugar.png");
        Image imgJugarEscalada = imgJugar.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnJugar.setIcon(new ImageIcon(imgJugarEscalada));

        btnJugar.setBorderPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setFocusPainted(false);
        btnJugar.setOpaque(false);

        btnJugar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Iniciando partida...");
            // TrabajoColdwar.jugar(false); // Descomenta en tu proyecto
        });
        add(btnJugar); 

        // BOTÓN REGLAS
        btnReglas = new JButton();
        btnReglas.setBounds(325, 220, 250, 45);
        ImageIcon imgReglas = new ImageIcon("recurso/reglas.png");
        Image imgReglasEscalada = imgReglas.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnReglas.setIcon(new ImageIcon(imgReglasEscalada));

        btnReglas.setBorderPainted(false);
        btnReglas.setContentAreaFilled(false);
        btnReglas.setFocusPainted(false);
        btnReglas.setOpaque(false);

        btnReglas.addActionListener(e -> {
            new VentanaReglas();
        });
        add(btnReglas);

        // BOTÓN INFORMACIÓN
        btnInfo = new JButton();
        btnInfo.setBounds(325, 290, 250, 45);
        ImageIcon imgInfo = new ImageIcon(""); // Revisa si querías 'info.png' aquí
        Image imgInfoEscalada = imgInfo.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnInfo.setIcon(new ImageIcon(imgInfoEscalada));

        btnInfo.setBorderPainted(false);
        btnInfo.setContentAreaFilled(false);
        btnInfo.setFocusPainted(false);
        btnInfo.setOpaque(false);

        btnInfo.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "");
        });
        add(btnInfo);

        // BOTÓN Creditos
        btnBonus = new JButton();
        btnBonus.setBounds(325, 270, 250, 45);
        ImageIcon imgBonus = new ImageIcon("recurso/creditos.png"); 
        Image imgBonusEscalada = imgBonus.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnBonus.setIcon(new ImageIcon(imgBonusEscalada));

        btnBonus.setBorderPainted(false);
        btnBonus.setContentAreaFilled(false);
        btnBonus.setFocusPainted(false);
        btnBonus.setOpaque(false);

        btnBonus.addActionListener(e -> {
            new VentanaCreditos();
            JOptionPane.showMessageDialog(null, "");
            // TrabajoColdwar.jugar(true);
        });
        add(btnBonus);

        // BOTÓN GANADORES
        btnGanadores = new JButton();
        btnGanadores.setBounds(325, 310, 250, 45);
        ImageIcon imgGanadores = new ImageIcon("recurso/ranking.png");
        Image imgGanadoresEscalada = imgGanadores.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnGanadores.setIcon(new ImageIcon(imgGanadoresEscalada));

        btnGanadores.setBorderPainted(false);
        btnGanadores.setContentAreaFilled(false);
        btnGanadores.setFocusPainted(false);
        btnGanadores.setOpaque(false);

        btnGanadores.addActionListener(e -> {
            new VentanaRanking();
            JOptionPane.showMessageDialog(null, "Mostrando ranking...");
        });
        add(btnGanadores);

        // BOTÓN SALIR
        btnSalir = new JButton();
        btnSalir.setBounds(325, 380, 250, 45);
        ImageIcon imgSalir = new ImageIcon("recurso/salir.png");
        Image imgSalirEscalada = imgSalir.getImage().getScaledInstance(250, 45, Image.SCALE_SMOOTH);
        btnSalir.setIcon(new ImageIcon(imgSalirEscalada));

        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setOpaque(false);

        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    // =========================
    // FONDO (Se añade el último para quedar atrás)
    // =========================
    public void crearFondo() {
        ImageIcon imagen = new ImageIcon("recurso/Fondo_Final.png");
        Image img = imagen.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        fondo = new JLabel(new ImageIcon(img));
        fondo.setBounds(0, 0, 900, 600);
        add(fondo); // Al ser el último add, se dibuja por debajo de los botones
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainFrame());
    }
}
