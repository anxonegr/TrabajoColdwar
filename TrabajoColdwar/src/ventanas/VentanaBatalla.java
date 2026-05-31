package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VentanaBatalla extends JFrame {

    // Variables de las cosas que salen en la pantalla
    private JComboBox<String> comboObjetivos;
    private JTextField cajaMisiles;
    private JButton btnAtacar;
    private JLabel lblTurno;
    private JLabel lblMisilesDisponibles;
    private JLabel lblFotoAtacante;

    public VentanaBatalla() {
        // Configuracion basica de la ventana
        setTitle("ColdWar - Batalla");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla al abrir
        setLayout(null); // Usamos null para poner las coordenadas a mano

        // Ponemos la foto de fondo
        setContentPane(new JLabel(new ImageIcon("recurso/fondoBatalla.png")));

        // Texto que dice de quien es el turno
        lblTurno = new JLabel("Turno de: ---");
        lblTurno.setFont(new Font("Arial", Font.BOLD, 28));
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setBounds(250, 50, 400, 40);
        add(lblTurno);

        // Foto del personaje que ataca (al lado del texto)
        lblFotoAtacante = new JLabel();
        lblFotoAtacante.setBounds(120, 30, 110, 110);
        add(lblFotoAtacante);

        // Texto que dice los misiles que le quedan al jugador
        lblMisilesDisponibles = new JLabel("Misiles en reserva: 0");
        lblMisilesDisponibles.setFont(new Font("Arial", Font.BOLD, 18));
        lblMisilesDisponibles.setForeground(Color.GREEN);
        lblMisilesDisponibles.setBounds(250, 100, 300, 30);
        add(lblMisilesDisponibles);

        // Etiqueta "Selecciona el objetivo"
        JLabel lblObjetivo = new JLabel("Selecciona el objetivo:");
        lblObjetivo.setFont(new Font("Arial", Font.BOLD, 16));
        lblObjetivo.setForeground(Color.WHITE);
        lblObjetivo.setBounds(200, 200, 200, 30);
        add(lblObjetivo);

        // Desplegable para elegir a quien queremos atacar
        comboObjetivos = new JComboBox<>();
        comboObjetivos.setFont(new Font("Arial", Font.PLAIN, 14));
        comboObjetivos.setBackground(new Color(30, 30, 30));
        comboObjetivos.setForeground(Color.WHITE);
        comboObjetivos.setBounds(400, 200, 200, 30);
        add(comboObjetivos);

        // Etiqueta "Misiles a lanzar"
        JLabel lblMisiles = new JLabel("Misiles a lanzar:");
        lblMisiles.setFont(new Font("Arial", Font.BOLD, 16));
        lblMisiles.setForeground(Color.WHITE);
        lblMisiles.setBounds(200, 270, 200, 30);
        add(lblMisiles);

        // Caja donde el usuario escribe la cantidad de misiles
        cajaMisiles = new JTextField();
        cajaMisiles.setFont(new Font("Arial", Font.BOLD, 16));
        cajaMisiles.setBackground(new Color(30, 30, 30));
        cajaMisiles.setForeground(Color.WHITE);
        cajaMisiles.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        cajaMisiles.setBounds(400, 270, 100, 30);
        add(cajaMisiles);

        // Boton de atacar (escondemos los bordes para que solo se vea tu imagen)
        btnAtacar = new JButton(new ImageIcon("recurso/btnAtacar.png"));
        btnAtacar.setFocusPainted(false);
        btnAtacar.setBorderPainted(false);
        btnAtacar.setContentAreaFilled(false);
        btnAtacar.setBounds(300, 400, 200, 60); 
        add(btnAtacar);

        // Le decimos al boton que ejecute un metodo al hacer clic
        btnAtacar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lanzarAtaque();
            }
        });
    }

    // Metodo que usara el que hace la logica para cambiar el turno
    public void actualizarTurno(String nombreAtacante, int misilesTotales, String archivoImagen) {
        lblTurno.setText("Turno de: " + nombreAtacante);
        lblMisilesDisponibles.setText("Misiles en reserva: " + misilesTotales);
        cajaMisiles.setText(""); // Vaciamos la caja para que no quede lo de antes

        // Cargamos la foto del nuevo turno y la adaptamos para que quede suave
        ImageIcon icono = new ImageIcon("recurso/" + archivoImagen);
        Image img = icono.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        lblFotoAtacante.setIcon(new ImageIcon(img));
    }

    // Metodo para llenar el desplegable solo con los equipos vivos
    public void cargarObjetivos(ArrayList<String> nombresVivos) {
        comboObjetivos.removeAllItems(); // Borramos los nombres viejos
        for (String nombre : nombresVivos) {
            comboObjetivos.addItem(nombre); // Añadimos los nuevos
        }
    }

    // Lo que pasa internamente cuando haces clic en el boton de atacar
    private void lanzarAtaque() {
        String objetivoSeleccionado = (String) comboObjetivos.getSelectedItem();
        String misilesEscritos = cajaMisiles.getText();
        
        // Aqui el que hace los errores comprobara que no pongan letras
        System.out.println("Has atacado a " + objetivoSeleccionado + " con " + misilesEscritos + " misiles");
    }
}
