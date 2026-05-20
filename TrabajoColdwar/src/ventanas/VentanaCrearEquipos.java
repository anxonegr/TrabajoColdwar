package ventanas;

import javax.swing.*;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.*;

public class VentanaCrearEquipos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JFrame ventanaPadre;

    private JButton btnAnadir, btnJugar, btnInfo;
    private JTextField[] cajasNombres;
    private JComboBox<String>[] combosTipos;
    private JLabel[] etiqEquipos;
    private JLabel[] miniFotos;

    private String[] nombresPersonajes = {
        "Nobita", "Agallas", "Homer", "Peter Griffin", "Finn el Humano", "Fanboy & Chum Chum"
    };
    private String[] archPersonajes = {
        "nobita.png", "agallas.png", "homer.png", "peter.png", "finn.png", "fanboychumchum.png"
    };

    @SuppressWarnings("unchecked")
    public VentanaCrearEquipos(JFrame padre) {
        this.ventanaPadre = padre;

        // Este panel ocupa exactamente la ventana y tiene layout null
        setLayout(null);
        setBounds(0, 0, 900, 600);
        setOpaque(false); // el fondo lo pinta paintComponent

        // ── Título ──────────────────────────────────────────────────────────
        JLabel labelTitulo = new JLabel(escalar("recurso/crearEquipos.png", 400, 80));
        labelTitulo.setBounds(250, 15, 400, 80);
        add(labelTitulo);

        // ── Cabeceras ────────────────────────────────────────────────────────
        JLabel lblNombre = new JLabel(escalar("recurso/nombreEquipo.png", 200, 35));
        lblNombre.setBounds(260, 100, 200, 35);
        add(lblNombre);

        JLabel lblPersonaje = new JLabel(escalar("recurso/personajes.png", 180, 35));
        lblPersonaje.setBounds(470, 100, 180, 35);
        add(lblPersonaje);

        // ── Filas de equipos ─────────────────────────────────────────────────
        cajasNombres = new JTextField[6];
        combosTipos  = new JComboBox[6];
        etiqEquipos  = new JLabel[6];
        miniFotos    = new JLabel[6];

        Color colorFondo = new Color(20, 20, 20);
        Color colorTexto = Color.WHITE;
        Color colorBorde = new Color(0, 200, 80);
        Font  fuenteInput = new Font("Arial", Font.PLAIN, 13);

        int yBase = 145;
        int paso  = 48;

        for (int i = 0; i < 6; i++) {
            int y = yBase + i * paso;

            // Etiqueta equipo
            etiqEquipos[i] = new JLabel(escalar("recurso/equipo" + (i + 1) + ".png", 120, 30));
            etiqEquipos[i].setBounds(110, y, 120, 30);
            add(etiqEquipos[i]);

            // Caja nombre
            cajasNombres[i] = new JTextField();
            cajasNombres[i].setFont(fuenteInput);
            cajasNombres[i].setBackground(colorFondo);
            cajasNombres[i].setForeground(colorTexto);
            cajasNombres[i].setCaretColor(colorTexto);
            cajasNombres[i].setBorder(BorderFactory.createLineBorder(colorBorde, 2));
            cajasNombres[i].setBounds(250, y, 195, 30);
            add(cajasNombres[i]);

            // ComboBox
            combosTipos[i] = new JComboBox<>(nombresPersonajes);
            combosTipos[i].setFont(fuenteInput);
            combosTipos[i].setBackground(colorFondo);
            combosTipos[i].setForeground(colorTexto);
            combosTipos[i].setBounds(460, y, 160, 30);
            add(combosTipos[i]);

            // Mini foto
            miniFotos[i] = new JLabel();
            miniFotos[i].setBounds(632, y - 4, 40, 40);
            actualizarMiniFoto(miniFotos[i], 0);
            add(miniFotos[i]);

            final int idx = i;
            combosTipos[i].addActionListener(e ->
                actualizarMiniFoto(miniFotos[idx], combosTipos[idx].getSelectedIndex())
            );

            // Equipos 4-6 ocultos al inicio
            if (i >= 3) {
                etiqEquipos[i].setVisible(false);
                cajasNombres[i].setVisible(false);
                combosTipos[i].setVisible(false);
                miniFotos[i].setVisible(false);
            }
            
            
        }

        // ── Botones ──────────────────────────────────────────────────────────
        btnAnadir = boton("recurso/añadirEquipo.png", 150, 500, 160, 45);
        btnJugar  = boton("recurso/jugar.png",        330, 500, 160, 45);
        btnInfo   = boton("recurso/Infopersonajes.png", 510, 500, 160, 45);

        add(btnAnadir);
        add(btnJugar);
        add(btnInfo);

        // ── Acciones ─────────────────────────────────────────────────────────
        btnAnadir.addActionListener(e -> {
            for (int i = 3; i < 6; i++) {
                if (!etiqEquipos[i].isVisible()) {
                    etiqEquipos[i].setVisible(true);
                    cajasNombres[i].setVisible(true);
                    combosTipos[i].setVisible(true);
                    miniFotos[i].setVisible(true);
                    if (i == 5) btnAnadir.setVisible(false);
                    break;
                }
            }
        });

        btnInfo.addActionListener(e ->
            new DialogoInfoPersonajes(ventanaPadre).setVisible(true)
        );

        btnJugar.addActionListener(e ->
            JOptionPane.showMessageDialog(ventanaPadre, "Iniciando partida...")
        );
        
     // ── Botón volver ─────────────────────────────────────────────────────────
        JButton btnVolver = boton("recurso/flechaizq.png", 20, 520, 60, 50);
        btnVolver.addActionListener(e -> {
            if (ventanaPadre instanceof mainFrame) {
                ((mainFrame) ventanaPadre).mostrarMenuPrincipal();
            }
        });
        add(btnVolver);
    }

    // Pinta el fondo estirado a 900x600 directamente en este panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon ic = new ImageIcon("recurso/fondo.png");
        g.drawImage(ic.getImage(), 0, 0, 900, 600, this);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private ImageIcon escalar(String ruta, int w, int h) {
        Image img = new ImageIcon(ruta).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton boton(String ruta, int x, int y, int w, int h) {
        // Cargamos la imagen original SIN escalar — el setBounds ya define el tamaño visual
        ImageIcon icono = new ImageIcon(ruta);
        Image imgOriginal = icono.getImage();
        // Solo escalamos si la imagen existe (ancho > 0)
        ImageIcon iconoFinal = (imgOriginal.getWidth(null) > 0)
            ? new ImageIcon(imgOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH))
            : icono;

        JButton btn = new JButton(iconoFinal);
        btn.setBounds(x, y, w, h);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        // Evita que el JButton reescale el icono internamente
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        return btn;
    }

    private void actualizarMiniFoto(JLabel label, int indice) {
        Image img = new ImageIcon("recurso/" + archPersonajes[indice])
                        .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    // ── Diálogo info personajes ───────────────────────────────────────────────

    class DialogoInfoPersonajes extends JDialog {
        private static final long serialVersionUID = 1L;
        private int indiceActual = 0;
        private JLabel fotoGrande;
        private LabelTextoBordeado textoInfo;

        private String[] detalles = {
            "NOBITA\n\nPlaneta: Enano\nVida: 100 ♥\nPasiva: Esquiva 50%\nTorpe pero escurridizo.",
            "AGALLAS\n\nPlaneta: Azul\nDaño: x2 Rojo, /2 Verde\nCobarde pero con ventaja.",
            "HOMER\n\nPlaneta: Gaseoso\nVida: 400 ♥\nMisiles crecen por ronda.",
            "PETER GRIFFIN\n\nPlaneta: Rojo\nDaño: x2 Verde, /2 Azul\nAgresivo y bruto.",
            "FINN EL HUMANO\n\nPlaneta: Verde\nDaño: x2 Azul, /2 Rojo\nHéroe aventurero.",
            "FANBOY & CHUM CHUM\n\nPlaneta: Normal\nVida: 200 ♥ | Energía: 50 ⚡\nEquilibrados."
        };

        public DialogoInfoPersonajes(JFrame parent) {
            super(parent, "Info Personajes", true);
            setSize(600, 400);
            setLocationRelativeTo(parent);
            setLayout(null);
            setResizable(false);

            // Fondo del diálogo
            JLabel fondo = new JLabel(escalar("recurso/fondo.png", 600, 400));
            fondo.setBounds(0, 0, 600, 400);
            fondo.setLayout(null);
            setContentPane(fondo);

            fotoGrande = new JLabel();
            fotoGrande.setBounds(50, 50, 200, 200);
            fondo.add(fotoGrande);

            textoInfo = new LabelTextoBordeado();
            textoInfo.setBounds(280, 50, 280, 250);
            fondo.add(textoInfo);

            JButton btnIzq = boton("recurso/flechaizq.png", 70, 270, 70, 50);
            btnIzq.addActionListener(e -> { indiceActual = (indiceActual - 1 + 6) % 6; actualizarVista(); });
            fondo.add(btnIzq);

            JButton btnDer = boton("recurso/flechader.png", 160, 270, 70, 50);
            btnDer.addActionListener(e -> { indiceActual = (indiceActual + 1) % 6; actualizarVista(); });
            fondo.add(btnDer);

            actualizarVista();
        }

        private void actualizarVista() {
            Image img = new ImageIcon("recurso/" + archPersonajes[indiceActual])
                            .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoGrande.setIcon(new ImageIcon(img));
            textoInfo.setText(detalles[indiceActual]);
            textoInfo.repaint();
        }
    }

    // ── Texto con borde negro ─────────────────────────────────────────────────

    class LabelTextoBordeado extends JTextArea {
        private static final long serialVersionUID = 1L;

        public LabelTextoBordeado() {
            setOpaque(false);
            setEditable(false);
            setFocusable(false);
            setFont(new Font("Arial", Font.BOLD, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            String[] lineas = getText().split("\n");
            FontMetrics fm = g2.getFontMetrics();
            int y = fm.getAscent() + 10;
            int grosor = 2;

            for (String linea : lineas) {
                g2.setColor(Color.BLACK);
                for (int i = -grosor; i <= grosor; i++)
                    for (int j = -grosor; j <= grosor; j++)
                        if (i != 0 || j != 0)
                            g2.drawString(linea, 10 + i, y + j);
                g2.setColor(Color.WHITE);
                g2.drawString(linea, 10, y);
                y += fm.getHeight();
            }
        }
    }
}