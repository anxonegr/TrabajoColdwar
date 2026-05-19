package ventanas; 

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCrearEquipos extends JFrame {
    
    // 1. variables: Preparamos los "huecos" para los botones, las cajas de texto y los menús
    private JButton btnAnadir, btnEmpezar, btnInfo;
    private JTextField[] cajasNombres;
    private JComboBox<String>[] combosTipos;

    public VentanaCrearEquipos() {
        // 2. la ventana: Configuramos el tamaño y cómo se comporta
        setTitle("Crear Equipos - Coldwar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Diseño libre para usar coordenadas exactas 

        // 3. botones: Los creamos y los colocamos en la parte de abajo de la ventana
        btnAnadir = new JButton("Añadir equipo");
        btnEmpezar = new JButton("Empezar Partida");
        btnInfo = new JButton("Info Planetas");
        
        btnAnadir.setBounds(150, 450, 150, 40);
        btnEmpezar.setBounds(320, 450, 150, 40);
        btnInfo.setBounds(490, 450, 150, 40);
        
        add(btnAnadir);
        add(btnEmpezar);
        add(btnInfo);

        // 4. arrays: Creamos 6 espacios para las cajas y combos de los equipos
        cajasNombres = new JTextField[6];
        combosTipos = new JComboBox[6];
        
        // Las opciones que saldrán en los menús desplegables
        String[] tiposPlaneta = {"Normal", "Rojo", "Azul", "Verde", "Gigante gaseoso", "Planeta enano"};
        int yInicial = 100; // La altura a la que empieza el primer equipo

        // 5. bucle: Construye los 6 equipos automáticamente de arriba abajo
        for (int i = 0; i < 6; i++) {
            cajasNombres[i] = new JTextField();
            combosTipos[i] = new JComboBox<>(tiposPlaneta);
            
            // Coloca cada equipo 50 píxeles más abajo que el anterior 
            cajasNombres[i].setBounds(150, yInicial + (i * 50), 200, 30);
            combosTipos[i].setBounds(370, yInicial + (i * 50), 150, 30);
            
            // Oculta los equipos 4, 5 y 6 al arrancar (índices 3, 4 y 5)
            if (i >= 3) {
                cajasNombres[i].setVisible(false);
                combosTipos[i].setVisible(false);
            }
            
            add(cajasNombres[i]);
            add(combosTipos[i]);
        }

        // 6. acción del botón: Qué pasa al hacer clic en "Añadir equipo"
        btnAnadir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Busca entre los equipos ocultos
                for (int i = 3; i < 6; i++) {
                    if (!cajasNombres[i].isVisible()) {
                        // Lo hace visible
                        cajasNombres[i].setVisible(true);
                        combosTipos[i].setVisible(true);
                        
                        // Si acabamos de mostrar el equipo 6, el botón desaparece
                        if (i == 5) {
                            btnAnadir.setVisible(false);
                        }
                        
                        break; // Para y no muestra el resto de golpe
                    }
                }
            }
        });

        btnEmpezar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 6; i++) {

            // Solo revisar equipos visibles
            if (cajasNombres[i].isVisible()) {

                String nombre = cajasNombres[i].getText().trim();

                // Nombre vacío
                if (nombre.isBlank()) {

                    JOptionPane.showMessageDialog(
                        null,
                        "El equipo " + (i + 1) + " no tiene nombre",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                // Nombre muy corto
                if (nombre.length() < 3) {

                    JOptionPane.showMessageDialog(
                        null,
                        "El nombre del equipo " + (i + 1) + " es demasiado corto",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            if (nombre.length() > 15) {

    JOptionPane.showMessageDialog(
        null,
        "El nombre es demasiado largo",
        "Error",
        JOptionPane.ERROR_MESSAGE
    );

    return;
            }
            
            }
            
        }

        // Comprobar nombres repetidos
for (int i = 0; i < 6; i++) {

    if (cajasNombres[i].isVisible()) {

        for (int j = i + 1; j < 6; j++) {

            if (cajasNombres[j].isVisible()) {

                String nombre1 = cajasNombres[i].getText().trim();
                String nombre2 = cajasNombres[j].getText().trim();

                if (nombre1.equalsIgnoreCase(nombre2)) {

                    JOptionPane.showMessageDialog(
                        null,
                        "Hay equipos con nombres repetidos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }
            }
        }
    }
}
        JOptionPane.showMessageDialog(
            null,
            "Todo correcto"
        );
    }
});
    }
}
