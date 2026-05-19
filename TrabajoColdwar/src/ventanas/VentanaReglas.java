package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class VentanaReglas extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String[][] PAGINAS = {
        {
            "☢  LA GUERRA GALÁCTICA",
            "La galaxia está en guerra.",
            "Seis equipos de lo más variopinto se disputan",
            "el control del universo a base de misiles.",
            "",
            "Cada equipo elige su planeta y se enfrenta",
            "al resto en combates por rondas.",
            "",
            "Solo uno saldrá victorioso.",
            "El resto... al vacío espacial."
        },
        {
            "⚔  CÓMO SE JUEGA",
            "1. Cada ronda todos los planetas reciben misiles",
            "   según su tipo.",
            "",
            "2. En tu turno eliges a quién atacar y cuántos",
            "   misiles lanzar. Puedes repartirlos.",
            "",
            "3. Desde ronda 2 puedes usar DEFENSA:",
            "   conviertes misiles en vida y terminas el turno.",
            "",
            "4. Si un planeta llega a 0 vidas, queda eliminado.",
            "5. Gana el último planeta con vida."
        },
        {
            "🔺  TRIÁNGULO DE VENTAJAS",
            "ROJO  ──x2──►  VERDE  ──x2──►  AZUL",
            "  ▲                                │",
            "  └──────────────x2────────────────┘",
            "",
            "Atacar en sentido contrario hace /2 de daño.",
            "Normal, Gaseoso y Enano no tienen ventajas (x1).",
            "",
            "★  MODO CHAOS (opción 4 del menú)",
            "Cada 2 rondas, un planeta aleatorio recibe",
            "+15 misiles extra. ¡Puede cambiar todo!"
        },
        {
            "★  LOS CONTENDIENTES  (1/2)",
            "NOBITA  →  Planeta Enano  (100♥ | 50% esquive)",
            "Tan torpe que los misiles le fallan uno de cada dos.",
            "El azar es su único aliado.",
            "",
            "AGALLAS  →  Planeta Azul  (200♥ | x2 vs Rojo)",
            "Cobarde por naturaleza, pero dobla el daño al Rojo.",
            "Huye del Verde como de la correa de Muriel.",
            "",
            "HOMER  →  Gigante Gaseoso  (400♥ | +10⚡/ronda)",
            "Lento para empezar, devastador con el tiempo.",
            "Mmm... misiles."
        },
        {
            "★  LOS CONTENDIENTES  (2/2)",
            "PETER GRIFFIN  →  Planeta Rojo  (200♥ | x2 vs Verde)",
            "Sin estrategia, sin filtros y con mucha potencia.",
            "Frente a los Azules, que alguien lo ayude.",
            "",
            "FINN EL HUMANO  →  Planeta Verde  (200♥ | x2 vs Azul)",
            "El único héroe de verdad en esta galaxia.",
            "Aplasta a los Azules, aunque un Rojo le arruina el día.",
            "",
            "FANBOY & CHUM CHUM  →  Planeta Normal  (200♥ | 50⚡)",
            "Dos raritos en un solo equipo. Constantes y sin sorpresas.",
            "Lo que ves es lo que hay."
        },
        {
            "🪪  IDENTIFICADOR DE EQUIPO",
            "Formato obligatorio:",
            "  4 números  +  3 letras mayúsculas",
            "",
            "Ejemplos válidos:",
            "  0001NOB   →  Nobita",
            "  0002AGA   →  Agallas",
            "  0003HOM   →  Homer",
            "  0004PET   →  Peter Griffin",
            "  0005FIN   →  Finn el Humano",
            "  0006FAN   →  Fanboy & Chum Chum",
            "",
            "Se usa para registrar al ganador en el historial."
        }
    };

    private int paginaActual = 0;

    private ImageIcon imgFondo;
    private ImageIcon imgTitulo;
    private ImageIcon imgFlechaIzq;
    private ImageIcon imgFlechaDer;

    private JLabel lblFlechaIzq;
    private JLabel lblFlechaDer;
    private JLabel lblIndicador;
    private JTextArea areaContenido;
    private JPanel panelFondo;

    private static final Color COLOR_PANEL   = new Color(0, 0, 0, 160);
    private static final Color COLOR_TITULO  = new Color(60, 180, 30);
    private static final Color COLOR_TEXTO   = new Color(230, 255, 200);
    private static final Color COLOR_INDICAD = new Color(120, 220, 60);
    private static final Font  FUENTE_TITULO = new Font("Arial Black", Font.BOLD, 22);
    private static final Font  FUENTE_TEXTO  = new Font("Monospaced", Font.PLAIN, 15);
    private static final Font  FUENTE_INDIC  = new Font("Arial", Font.BOLD, 14);

    public VentanaReglas() {
        super("ColdWar — Reglas");
        cargarImagenes();
        inicializarVentana();
        construirUI();
        actualizarPagina();
        setVisible(true);
    }

    private void cargarImagenes() {
        imgFondo     = cargarIcono("recurso/fondo.png");
        imgTitulo    = cargarIcono("recurso/reglas.png");
        imgFlechaIzq = cargarIcono("recurso/flechaizq.png");
        imgFlechaDer = cargarIcono("recurso/flechader.png");
    }

    private ImageIcon cargarIcono(String ruta) {
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) return new ImageIcon(url);
        return new ImageIcon(ruta);
    }

    private void inicializarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void construirUI() {
        panelFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imgFondo != null && imgFondo.getIconWidth() > 0) {
                    g.drawImage(imgFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(60, 120, 20));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(panelFondo);

        // Título (imagen "Reglas")
        JLabel lblTitulo = new JLabel();
        if (imgTitulo != null && imgTitulo.getIconWidth() > 0) {
            Image img = imgTitulo.getImage().getScaledInstance(320, 100, Image.SCALE_SMOOTH);
            lblTitulo.setIcon(new ImageIcon(img));
        } else {
            lblTitulo.setText("REGLAS");
            lblTitulo.setFont(FUENTE_TITULO);
            lblTitulo.setForeground(COLOR_TITULO);
        }
        lblTitulo.setBounds(290, 10, 320, 100);
        panelFondo.add(lblTitulo);

        // Panel de contenido semitransparente
        JPanel panelContenido = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        panelContenido.setOpaque(false);
        panelContenido.setBounds(100, 115, 700, 390);
        panelFondo.add(panelContenido);

        JLabel lblTitPag = new JLabel("", SwingConstants.CENTER);
        lblTitPag.setFont(FUENTE_TITULO);
        lblTitPag.setForeground(COLOR_TITULO);
        lblTitPag.setBorder(BorderFactory.createEmptyBorder(12, 10, 6, 10));
        panelContenido.add(lblTitPag, BorderLayout.NORTH);

        areaContenido = new JTextArea();
        areaContenido.setOpaque(false);
        areaContenido.setEditable(false);
        areaContenido.setFocusable(false);
        areaContenido.setFont(FUENTE_TEXTO);
        areaContenido.setForeground(COLOR_TEXTO);
        areaContenido.setLineWrap(false);
        areaContenido.setBorder(BorderFactory.createEmptyBorder(6, 24, 10, 24));
        panelContenido.add(areaContenido, BorderLayout.CENTER);
        panelContenido.putClientProperty("tituloPag", lblTitPag);

        // Flechas
        lblFlechaIzq = crearFlecha(imgFlechaIzq, 15, 250, -1);
        panelFondo.add(lblFlechaIzq);

        lblFlechaDer = crearFlecha(imgFlechaDer, 800, 250, 1);
        panelFondo.add(lblFlechaDer);

        // Indicador de página
        lblIndicador = new JLabel("", SwingConstants.CENTER);
        lblIndicador.setFont(FUENTE_INDIC);
        lblIndicador.setForeground(COLOR_INDICAD);
        lblIndicador.setBounds(350, 520, 200, 30);
        panelFondo.add(lblIndicador);

        // Botón cerrar
        JButton btnVolver = new JButton("✖  Cerrar");
        btnVolver.setFont(new Font("Arial Black", Font.BOLD, 13));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(30, 100, 10));
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(80, 200, 40), 2));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.setBounds(390, 520, 120, 36);
        btnVolver.addActionListener(e -> dispose());
        panelFondo.add(btnVolver);
    }

    private JLabel crearFlecha(ImageIcon icon, int x, int y, int direccion) {
        JLabel lbl = new JLabel();
        if (icon != null && icon.getIconWidth() > 0) {
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        } else {
            lbl.setText(direccion < 0 ? "◄" : "►");
            lbl.setFont(new Font("Arial", Font.BOLD, 36));
            lbl.setForeground(new Color(60, 200, 30));
        }
        lbl.setBounds(x, y, 70, 70);
        lbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { cambiarPagina(direccion); }
        });
        return lbl;
    }

    private void cambiarPagina(int delta) {
        paginaActual = (paginaActual + delta + PAGINAS.length) % PAGINAS.length;
        actualizarPagina();
    }

    private void actualizarPagina() {
        String[] pagina = PAGINAS[paginaActual];
        String tituloPag = pagina.length > 0 ? pagina[0] : "";

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < pagina.length; i++) {
            sb.append(pagina[i]);
            if (i < pagina.length - 1) sb.append("\n");
        }

        Component[] comps = ((JPanel) getContentPane()).getComponents();
        for (Component c : comps) {
            if (c instanceof JPanel) {
                Object obj = ((JPanel) c).getClientProperty("tituloPag");
                if (obj instanceof JLabel) {
                    ((JLabel) obj).setText(tituloPag);
                }
            }
        }

        areaContenido.setText(sb.toString());
        areaContenido.setCaretPosition(0);

        lblIndicador.setText("● ".repeat(paginaActual) + "◉ " + "● ".repeat(PAGINAS.length - paginaActual - 1));

        lblFlechaIzq.setVisible(paginaActual > 0);
        lblFlechaDer.setVisible(paginaActual < PAGINAS.length - 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaReglas::new);
    }
}
