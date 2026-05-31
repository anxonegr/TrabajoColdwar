package ventanas;

import javax.swing.*;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaCrearEquipos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JFrame ventanaPadre;

    private JButton btnAnadir, btnJugar, btnInfo;
    private JTextField[] cajasNombres;
    private JComboBox<String>[] combosTipos;
    private JLabel[] etiqEquipos;
    private JLabel[] miniFotos;

    // ── NUEVO: etiquetas de error por fila (una bajo cada caja de nombre) ──
    private JLabel[] lblErrores;

    private String[] nombresPersonajes = {
        "Nobita", "Agallas", "Homer", "Peter Griffin", "Finn el Humano", "Fanboy & Chum Chum"
    };
    private String[] archPersonajes = {
        "nobita.png", "agallas.png", "homer.png", "peter.png", "finn.png", "fanboychumchum.png"
    };

    @SuppressWarnings("unchecked")
    public VentanaCrearEquipos(JFrame padre) {
        this.ventanaPadre = padre;

        setLayout(null);
        setBounds(0, 0, 900, 600);
        setOpaque(false);

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
        lblErrores   = new JLabel[6];   // NUEVO

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

            // ── NUEVO: etiqueta de error bajo la caja, oculta por defecto ──
            lblErrores[i] = new JLabel("");
            lblErrores[i].setFont(new Font("Arial", Font.BOLD, 11));
            lblErrores[i].setForeground(new Color(255, 70, 70));
            lblErrores[i].setBounds(250, y + 31, 300, 15);
            lblErrores[i].setVisible(false);
            add(lblErrores[i]);
            // ────────────────────────────────────────────────────────────────

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
                lblErrores[i].setVisible(false);
            }
        }

        // ── Botones Principales ───────────────────────────────────────────────
        btnAnadir = boton("recurso/añadirEquipo.png", 150, 500, 160, 45);
        btnJugar  = boton("recurso/jugar.png",        330, 500, 160, 45);
        btnInfo   = boton("recurso/Infopersonajes.png", 510, 500, 160, 45);

        add(btnAnadir);
        add(btnJugar);
        add(btnInfo);

        // ── Acciones de los Botones ──────────────────────────────────────────
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

        // ── CAMBIO: btnJugar ahora valida antes de arrancar ──────────────────
        btnJugar.addActionListener(e -> validarYJugar());
        // ─────────────────────────────────────────────────────────────────────

        // ── Botón Volver ──────────────────────────────────────────────────────
        JButton btnVolver = boton("recurso/flechaizqroja.png", 20, 520, 60, 50);
        btnVolver.addActionListener(e -> {
            if (ventanaPadre instanceof mainFrame) {
                ((mainFrame) ventanaPadre).mostrarMenuPrincipal();
            }
        });
        add(btnVolver);

        // ── Botón Salir ───────────────────────────────────────────────────────
        JButton btnSalir = boton("recurso/salir.png", 800, 520, 60, 50);
        btnSalir.addActionListener(e -> System.exit(0));
        add(btnSalir);
    }

    // ── NUEVO: validación completa antes de empezar la partida ───────────────
    /**
     * Comprueba que todos los equipos visibles tengan nombre y que no haya
     * nombres repetidos. Si todo va bien, arranca el juego. Si no, muestra
     * errores en rojo junto a cada campo problemático Y un JOptionPane resumen.
     */
    private void validarYJugar() {

        // Limpiamos todos los errores anteriores
        limpiarErrores();

        ArrayList<String> nombres = new ArrayList<>();
        boolean hayError = false;
        StringBuilder resumenErrores = new StringBuilder();

        for (int i = 0; i < 6; i++) {

            // Solo validamos filas visibles
            if (!cajasNombres[i].isVisible()) continue;

            String nombre = cajasNombres[i].getText().trim();

            // 1. Campo vacío
            if (nombre.isEmpty()) {
                marcarError(i, "El nombre no puede estar vacío.");
                resumenErrores.append("• Equipo ").append(i + 1).append(": nombre vacío.\n");
                hayError = true;
                continue;
            }

            // 2. Nombre repetido
            String nombreLower = nombre.toLowerCase();
            if (nombres.contains(nombreLower)) {
                marcarError(i, "Nombre repetido.");
                resumenErrores.append("• Equipo ").append(i + 1)
                              .append(": \"").append(nombre).append("\" ya está en uso.\n");
                hayError = true;
            } else {
                nombres.add(nombreLower);
            }
        }

        if (hayError) {
            // Ventanita emergente con resumen de todos los problemas
            JOptionPane.showMessageDialog(
                ventanaPadre,
                "Corrige los siguientes errores antes de jugar:\n\n" + resumenErrores,
                "Error — Datos incorrectos",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Todo OK → arrancamos
        JOptionPane.showMessageDialog(
            ventanaPadre,
            "¡Todo listo! Iniciando partida...",
            "ColdWar",
            JOptionPane.INFORMATION_MESSAGE
        );
        // Aquí irá la llamada real al arranque del juego cuando se integre
    }

    /** Pone el borde rojo y el mensaje de error en la fila indicada. */
    private void marcarError(int i, String mensaje) {
        cajasNombres[i].setBorder(BorderFactory.createLineBorder(new Color(255, 60, 60), 2));
        lblErrores[i].setText("⚠ " + mensaje);
        lblErrores[i].setVisible(true);
    }

    /** Restaura todos los bordes y oculta todos los mensajes de error. */
    private void limpiarErrores() {
        Color colorBordeNormal = new Color(0, 200, 80);
        for (int i = 0; i < 6; i++) {
            cajasNombres[i].setBorder(BorderFactory.createLineBorder(colorBordeNormal, 2));
            lblErrores[i].setVisible(false);
        }
    }
    // ─────────────────────────────────────────────────────────────────────────

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
        ImageIcon icono = new ImageIcon(ruta);
        Image imgOriginal = icono.getImage();
        ImageIcon iconoFinal = (imgOriginal.getWidth(null) > 0)
            ? new ImageIcon(imgOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH))
            : icono;

        JButton btn = new JButton(iconoFinal);
        btn.setBounds(x, y, w, h);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
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

    // ── Diálogo info personajes (sin cambios) ─────────────────────────────────

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

            JButton btnIzq = boton("recurso/flechaizqroja.png", 70, 270, 70, 50);
            btnIzq.addActionListener(e -> { indiceActual = (indiceActual - 1 + 6) % 6; actualizarVista(); });
            fondo.add(btnIzq);

            JButton btnDer = boton("recurso/flechadrcroja.png", 160, 270, 70, 50);
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
