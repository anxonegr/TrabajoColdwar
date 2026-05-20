package ventanas; 

import javax.swing.*;
import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCrearEquipos extends JFrame {
    
    // variables
    private JButton btnAnadir, btnJugar, btnInfo;
    private JTextField[] cajasNombres;
    private JComboBox<String>[] combosTipos;
    private JLabel[] etiqEquipos; 
    private JLabel[] miniFotos; // Novedad: Las fotos en pequeñito

    // Arrays con los nombres exactos y sus archivos correspondientes
    private String[] nombresPersonajes = {"Nobita", "Agallas", "Homer", "Peter Griffin", "Finn el Humano", "Fanboy & Chum Chum"};
    private String[] archPersonajes = {"nobita.png", "agallas.png", "homer.png", "peter.png", "finn.png", "fanboychumchum.png"};

    public VentanaCrearEquipos() {
        // ventana y fondo
        setTitle("Crear Equipos - Coldwar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setContentPane(new JLabel(new ImageIcon("src/recurso/fondo.png")));
        setLayout(null); 

        // titulo principal
        JLabel labelTitulo = new JLabel(new ImageIcon("src/recurso/crearEquipos.png"));
        labelTitulo.setBounds(200, 20, 400, 80);
        add(labelTitulo);

        // etiquetas de columna 
        JLabel labelEtiqNombre = new JLabel(new ImageIcon("src/recurso/nombreEquipo.png"));
        labelEtiqNombre.setBounds(250, 100, 200, 40);
        add(labelEtiqNombre);

        JLabel labelEtiqTipo = new JLabel(new ImageIcon("src/recurso/personajes.png"));
        labelEtiqTipo.setBounds(470, 100, 200, 40);
        add(labelEtiqTipo);

        // arrays y componentes
        cajasNombres = new JTextField[6];
        combosTipos = new JComboBox[6];
        etiqEquipos = new JLabel[6]; 
        miniFotos = new JLabel[6];
        
        int yInicial = 150; 
        
        Color colorFondoInput = new Color(30, 30, 30); 
        Color colorTextoInput = Color.WHITE;
        Color colorBordeInput = Color.GREEN; 
        Font fuenteInput = new Font("Arial", Font.PLAIN, 14);

        //construye los equipos 
        for (int i = 0; i < 6; i++) {
            String rutaEtiq = "src/recurso/equipo" + (i + 1) + ".png";
            etiqEquipos[i] = new JLabel(new ImageIcon(rutaEtiq));
            etiqEquipos[i].setBounds(100, yInicial + (i * 50), 130, 30);
            add(etiqEquipos[i]);

            cajasNombres[i] = new JTextField();
            combosTipos[i] = new JComboBox<>(nombresPersonajes); 
            
            cajasNombres[i].setFont(fuenteInput);
            cajasNombres[i].setBackground(colorFondoInput);
            cajasNombres[i].setForeground(colorTextoInput);
            cajasNombres[i].setBorder(BorderFactory.createLineBorder(colorBordeInput, 2));

            combosTipos[i].setFont(fuenteInput);
            combosTipos[i].setBackground(colorFondoInput);
            combosTipos[i].setForeground(colorTextoInput);

            cajasNombres[i].setBounds(250, yInicial + (i * 50), 200, 30);
            combosTipos[i].setBounds(470, yInicial + (i * 50), 150, 30);
            
            // mini foto
            miniFotos[i] = new JLabel();
            miniFotos[i].setBounds(630, yInicial + (i * 50) - 5, 40, 40); // A la derecha del JComboBox
            actualizarMiniFoto(miniFotos[i], 0); // Por defecto carga la de Nobita

            // Acción para que al cambiar el desplegable, cambie la mini foto al momento
            final int index = i;
            combosTipos[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actualizarMiniFoto(miniFotos[index], combosTipos[index].getSelectedIndex());
                }
            });
            
            // Ocultar los sobrantes al inicio
            if (i >= 3) {
                etiqEquipos[i].setVisible(false);
                cajasNombres[i].setVisible(false);
                combosTipos[i].setVisible(false);
                miniFotos[i].setVisible(false);
            }
            
            add(cajasNombres[i]);
            add(combosTipos[i]);
            add(miniFotos[i]);
        }

        // botones de accion
        btnAnadir = new JButton(new ImageIcon("src/recurso/añadirEquipo.png"));
        btnAnadir.setFocusPainted(false);
        btnAnadir.setBorderPainted(false);
        btnAnadir.setContentAreaFilled(false); 
        btnAnadir.setBounds(150, 480, 150, 50);
        add(btnAnadir);

        btnJugar = new JButton(new ImageIcon("src/recurso/jugar.png"));
        btnJugar.setFocusPainted(false);
        btnJugar.setBorderPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setBounds(320, 480, 150, 50);
        add(btnJugar);

        btnInfo = new JButton(new ImageIcon("src/recurso/Infopersonajes.png"));
        btnInfo.setFocusPainted(false);
        btnInfo.setBorderPainted(false);
        btnInfo.setContentAreaFilled(false);
        btnInfo.setBounds(490, 480, 150, 50);
        add(btnInfo);

        // 8. botones
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 3; i < 6; i++) {
                    if (!etiqEquipos[i].isVisible()) {
                        etiqEquipos[i].setVisible(true);
                        cajasNombres[i].setVisible(true);
                        combosTipos[i].setVisible(true);
                        miniFotos[i].setVisible(true); // Mostrar también la fotito
                        
                        if (i == 5) {
                            btnAnadir.setVisible(false);
                        }
                        break; 
                    }
                }
            }
        });

        // Abrir la  ventana de Información al hacer clic
        btnInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DialogoInfoPersonajes dialogo = new DialogoInfoPersonajes(VentanaCrearEquipos.this);
                dialogo.setVisible(true);
            }
        });
    }

    // Método para escalar las imágenes pequeñas al lado de los nombres
    private void actualizarMiniFoto(JLabel label, int indice) {
        ImageIcon icono = new ImageIcon("src/recurso/" + archPersonajes[indice]);
        Image img = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

   
    class DialogoInfoPersonajes extends JDialog {
        private int indiceActual = 0;
        private JLabel fotoGrande;
        private LabelTextoBordeado textoInfo;

        // La información detallada de cada personaje usando saltos de línea (\n)
        private String[] detalles = {
            "NOBITA\n\nPlaneta: Enano\nVida: 100 ♥\nPasiva: Esquiva 50%\nTorpe pero escurridizo por suerte.",
            "AGALLAS\n\nPlaneta: Azul\nDaño: x2 Rojo, /2 Verde\nCobarde pero con ventaja oculta.",
            "HOMER\n\nPlaneta: Gaseoso\nVida: 400 ♥\nMisiles crecen por ronda.\nLento pero aguanta todo.",
            "PETER GRIFFIN\n\nPlaneta: Rojo\nDaño: x2 Verde, /2 Azul\nAgresivo y bruto.",
            "FINN EL HUMANO\n\nPlaneta: Verde\nDaño: x2 Azul, /2 Rojo\nHéroe aventurero.",
            "FANBOY & CHUM CHUM\n\nPlaneta: Normal\nVida: 200 ♥ | Energía: 50 ⚡\nDos juntos, equilibrados."
        };

        public DialogoInfoPersonajes(JFrame parent) {
            super(parent, "Info Personajes", true); // "true" bloquea la ventana de atrás hasta que cierres esta
            setSize(600, 400);
            setLocationRelativeTo(parent); // Centrar en la pantalla
            setLayout(null);
            
            // Reutilizamos el fondo del juego
            setContentPane(new JLabel(new ImageIcon("src/recurso/fondo.png")));

            // Foto grande a la izquierda
            fotoGrande = new JLabel();
            fotoGrande.setBounds(50, 50, 200, 200);
            add(fotoGrande);

            // Texto a la derecha
            textoInfo = new LabelTextoBordeado();
            textoInfo.setBounds(280, 50, 280, 250);
            add(textoInfo);

            // Flecha Izquierda
            JButton btnIzq = new JButton(new ImageIcon("src/recurso/flechaizq.png"));
            btnIzq.setFocusPainted(false);
            btnIzq.setBorderPainted(false);
            btnIzq.setContentAreaFilled(false);
            btnIzq.setBounds(70, 270, 70, 50);
            btnIzq.addActionListener(e -> {
                indiceActual--;
                if (indiceActual < 0) indiceActual = 5; // Vuelve al final si nos pasamos
                actualizarVista();
            });
            add(btnIzq);

            // Flecha Derecha
            JButton btnDer = new JButton(new ImageIcon("src/recurso/flechader.png"));
            btnDer.setFocusPainted(false);
            btnDer.setBorderPainted(false);
            btnDer.setContentAreaFilled(false);
            btnDer.setBounds(160, 270, 70, 50);
            btnDer.addActionListener(e -> {
                indiceActual++;
                if (indiceActual > 5) indiceActual = 0; // Vuelve al principio si nos pasamos
                actualizarVista();
            });
            add(btnDer);

            actualizarVista(); // Carga el primer personaje (Nobita)
        }

        private void actualizarVista() {
            ImageIcon icono = new ImageIcon("src/recurso/" + archPersonajes[indiceActual]);
            Image img = icono.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoGrande.setIcon(new ImageIcon(img));
            textoInfo.setText(detalles[indiceActual]);
            textoInfo.repaint();
        }
    }

    class LabelTextoBordeado extends JTextArea {
        public LabelTextoBordeado() {
            setOpaque(false); // Fondo transparente
            setEditable(false);
            setFocusable(false);
            setFont(new Font("Arial", Font.BOLD, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            // Suavizado para que las letras no se vean pixeladas
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIASING_ON);
            
            String[] lineas = getText().split("\n");
            FontMetrics fm = g2.getFontMetrics();
            int y = fm.getAscent() + 10; 

            for (String linea : lineas) {
                // Dibujar el contorno negro (dibujando la letra desplazada)
                g2.setColor(Color.BLACK);
                int grosor = 2; // Grosor del borde
                for (int i = -grosor; i <= grosor; i++) {
                    for (int j = -grosor; j <= grosor; j++) {
                        if (i != 0 || j != 0) {
                            g2.drawString(linea, 10 + i, y + j);
                        }
                    }
                }
                //Dibujar el texto blanco original encima
                g2.setColor(Color.WHITE);
                g2.drawString(linea, 10, y);
                
                y += fm.getHeight(); // Bajar a la siguiente línea
            }
        }
    }
}
