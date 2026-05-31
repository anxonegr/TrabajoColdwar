package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaBatalla extends JFrame {

    private JComboBox<String> comboObjetivos;
    private JTextField cajaMisiles;
    private JButton btnAtacar;
    private JLabel lblTurno;
    private JLabel lblMisilesDisponibles;
    private JLabel lblFotoAtacante;

    // ── NUEVO: etiqueta de error rojo que aparece bajo la caja de misiles ──
    private JLabel lblError;

    public VentanaBatalla() {
        setTitle("ColdWar - Batalla");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        setContentPane(new JLabel(new ImageIcon("recurso/fondoBatalla.png")));

        lblTurno = new JLabel("Turno de: ---");
        lblTurno.setFont(new Font("Arial", Font.BOLD, 28));
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setBounds(250, 50, 400, 40);
        add(lblTurno);

        lblFotoAtacante = new JLabel();
        lblFotoAtacante.setBounds(120, 30, 110, 110);
        add(lblFotoAtacante);

        lblMisilesDisponibles = new JLabel("Misiles en reserva: 0");
        lblMisilesDisponibles.setFont(new Font("Arial", Font.BOLD, 18));
        lblMisilesDisponibles.setForeground(Color.GREEN);
        lblMisilesDisponibles.setBounds(250, 100, 300, 30);
        add(lblMisilesDisponibles);

        JLabel lblObjetivo = new JLabel("Selecciona el objetivo:");
        lblObjetivo.setFont(new Font("Arial", Font.BOLD, 16));
        lblObjetivo.setForeground(Color.WHITE);
        lblObjetivo.setBounds(200, 200, 200, 30);
        add(lblObjetivo);

        comboObjetivos = new JComboBox<>();
        comboObjetivos.setFont(new Font("Arial", Font.PLAIN, 14));
        comboObjetivos.setBackground(new Color(30, 30, 30));
        comboObjetivos.setForeground(Color.WHITE);
        comboObjetivos.setBounds(400, 200, 200, 30);
        add(comboObjetivos);

        JLabel lblMisiles = new JLabel("Misiles a lanzar:");
        lblMisiles.setFont(new Font("Arial", Font.BOLD, 16));
        lblMisiles.setForeground(Color.WHITE);
        lblMisiles.setBounds(200, 270, 200, 30);
        add(lblMisiles);

        cajaMisiles = new JTextField();
        cajaMisiles.setFont(new Font("Arial", Font.BOLD, 16));
        cajaMisiles.setBackground(new Color(30, 30, 30));
        cajaMisiles.setForeground(Color.WHITE);
        cajaMisiles.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        cajaMisiles.setBounds(400, 270, 100, 30);
        add(cajaMisiles);

        // ── NUEVO: etiqueta de error, invisible hasta que haga falta ──────────
        lblError = new JLabel("");
        lblError.setFont(new Font("Arial", Font.BOLD, 13));
        lblError.setForeground(new Color(255, 60, 60));   // rojo vivo
        lblError.setBounds(400, 305, 300, 22);
        lblError.setVisible(false);
        add(lblError);
        // ──────────────────────────────────────────────────────────────────────

        btnAtacar = new JButton(new ImageIcon("recurso/btnAtacar.png"));
        btnAtacar.setFocusPainted(false);
        btnAtacar.setBorderPainted(false);
        btnAtacar.setContentAreaFilled(false);
        btnAtacar.setBounds(300, 400, 200, 60);
        add(btnAtacar);

        btnAtacar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lanzarAtaque();
            }
        });
    }

    public void actualizarTurno(String nombreAtacante, int misilesTotales, String archivoImagen) {
        lblTurno.setText("Turno de: " + nombreAtacante);
        lblMisilesDisponibles.setText("Misiles en reserva: " + misilesTotales);
        cajaMisiles.setText("");
        ocultarError();   // limpiamos el error al cambiar de turno

        ImageIcon icono = new ImageIcon("recurso/" + archivoImagen);
        Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        lblFotoAtacante.setIcon(new ImageIcon(img));
    }

    public void cargarObjetivos(ArrayList<String> nombresVivos) {
        comboObjetivos.removeAllItems();
        for (String nombre : nombresVivos) {
            comboObjetivos.addItem(nombre);
        }
    }

    // ── Lanzar ataque con validación gráfica completa ─────────────────────────
    private void lanzarAtaque() {

        String objetivoSeleccionado = (String) comboObjetivos.getSelectedItem();
        String misilesTexto = cajaMisiles.getText().trim();

        // 1. Caja vacía
        if (misilesTexto.isEmpty()) {
            mostrarError("Escribe cuántos misiles quieres lanzar.");
            return;
        }

        int misiles;

        // 2. El usuario ha escrito letras u otros caracteres no numéricos
        //    Antes esto tiraba una excepción en la consola; ahora salta ventanita
        try {
            misiles = Integer.parseInt(misilesTexto);
        } catch (NumberFormatException ex) {
            // ── CAMBIO: antes -> crash silencioso o System.out.println
            //           ahora  -> JOptionPane de error + aviso rojo inline
            JOptionPane.showMessageDialog(
                this,
                "\"" + misilesTexto + "\" no es un número válido.\nIntroduce solo dígitos (ej: 10).",
                "Error — Misiles no válidos",
                JOptionPane.ERROR_MESSAGE
            );
            mostrarError("Solo se admiten números enteros.");
            cajaMisiles.requestFocus();
            cajaMisiles.selectAll();
            return;
        }

        // 3. Número menor que 1
        if (misiles < 1) {
            JOptionPane.showMessageDialog(
                this,
                "Debes lanzar al menos 1 misil.",
                "Error — Cantidad inválida",
                JOptionPane.WARNING_MESSAGE
            );
            mostrarError("El mínimo es 1 misil.");
            return;
        }

        // 4. Más misiles de los disponibles
        //    (parseamos los disponibles desde el label para no necesitar referencia externa)
        int disponibles = parsearMisilesDisponibles();
        if (disponibles >= 0 && misiles > disponibles) {
            JOptionPane.showMessageDialog(
                this,
                "Solo tienes " + disponibles + " misiles disponibles.",
                "Error — Sin misiles suficientes",
                JOptionPane.WARNING_MESSAGE
            );
            mostrarError("No tienes " + misiles + " misiles. Máximo: " + disponibles + ".");
            return;
        }

        // Todo correcto → ocultamos el error y procesamos
        ocultarError();
        System.out.println("Has atacado a " + objetivoSeleccionado + " con " + misiles + " misiles");
        // Aquí iría la llamada real a la lógica de combate cuando se integre
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    /** Muestra el aviso rojo inline bajo la caja de misiles. */
    private void mostrarError(String mensaje) {
        lblError.setText("⚠ " + mensaje);
        lblError.setVisible(true);
        // Ponemos el borde de la caja en rojo para reforzar la señal visual
        cajaMisiles.setBorder(BorderFactory.createLineBorder(new Color(255, 60, 60), 2));
    }

    /** Oculta el aviso de error y restaura el borde normal. */
    private void ocultarError() {
        lblError.setVisible(false);
        cajaMisiles.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
    }

    /**
     * Extrae el número de misiles disponibles del texto del label.
     * Devuelve -1 si no se puede leer (para no bloquear el juego).
     */
    private int parsearMisilesDisponibles() {
        try {
            String texto = lblMisilesDisponibles.getText(); // "Misiles en reserva: 42"
            return Integer.parseInt(texto.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
