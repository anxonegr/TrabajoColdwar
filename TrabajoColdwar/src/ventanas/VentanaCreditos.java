package ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

// Utilizamos extends JFrame para que herede de la clase JFrame de Java y tengamos una ventana como
// en Windows 10.
public class VentanaCreditos extends JFrame {

    public VentanaCreditos() {
        // Configuración de la ventana:
        // setTitle indica el título de la ventana.
    	// setSize define el tamaño de la ventana.
    	// DISPOSE_ON_CLOSE define que, cuando se cierre la ventana el programa te devuelve al menú
    	// principal en vez de pararse por completo.
        // setLocationRelativeTo obliga a la ventana a aparecer en el centro de la pantalla y no en
    	// la esquina superior izquierda donde aparece predeterminado, y por último setResizable
    	// deniega el permiso de maximizar la ventana o ajustar su resolución
    	setTitle("Créditos");
        
    	setSize(627, 647); 
        
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    	setLocationRelativeTo(null);
        
    	setResizable(false); 

        // Imagen de fondo:
        // La línea 35 intenta localizar el archivo 'fondocreditos.png'. Si existe, lo escala a las
        // medidas exactas de la ventana para que no se deforme y lo inserta en un JLabel.
        URL urlFondo = getClass().getResource("/fondocreditos.png");
        
        JLabel panelFondo;
        
        ImageIcon iconoFondo = new ImageIcon(urlFondo);
        Image imagenEscaladaFondo = iconoFondo.getImage().getScaledInstance(627, 647, Image.SCALE_SMOOTH);
        
        panelFondo = new JLabel(new ImageIcon(imagenEscaladaFondo));
        panelFondo.setLayout(null); 
        
        // Imagen de creditos: 
        // Con esta orden le pedimos que busque en su propia clase la imagen que se llame creditos.png
        URL urlCreditos = getClass().getResource("recurso/creditos.png");
        
        // Utiliza la URL creditos.png para seleccionar la imagen, y luego la reescala a un tamaño de
        // 200 pixeles por 50 pixeles. Image.SCALE_SMOOTH es un filtro de calidad para que no se vea borrosa
        ImageIcon iconoTitulo = new ImageIcon(urlCreditos);
        Image imagenEscaladaTitulo = iconoTitulo.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH);
        
       // Generamos un contenedor con un JLabel con las mismas dimensiones que la imagen para que encaje
        JLabel lblTitulo = new JLabel(new ImageIcon(imagenEscaladaTitulo));
        
        // Usamos un setBounds para ajustar la imagen los píxeles que queremos, y un .add en el panel para que
        // aparezca físicamente en la pantalla y en el contenedor.
        lblTitulo.setBounds(205, 20, 200, 50);
        panelFondo.add(lblTitulo);

        // Texto de la versión:
        // Generamos otro JLabel de texto donde pone la versión, SwingConstants sirve para centrar el texto
        // (predeterminado va a la izquierda).
        JLabel lblVersion = new JLabel("Versión 3.0", SwingConstants.CENTER);
        // Asignamos la fuente del texto, con tamaño 11
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 11));
        // setForeground sirve para cambiar el color de la letra, en este caso blanco
        lblVersion.setForeground(Color.WHITE);
        // setBounds funciona igual que la imagen de creditos, se ajusta el texto a donde queremos que esté.
        lblVersion.setBounds(213, 72, 200, 20);
        // Añadimos de nuevo el .add en el panel para que se vea físicamente el texto
        panelFondo.add(lblVersion);

        // Texto "Desarrollado por":
        // Las características de este texto son casi iguales a las de la versión, lo único que cambia es
        // el tamaño de fuente y que está en negrita.
        JLabel creadoresTitulo = new JLabel("Desarrollado por:", SwingConstants.CENTER);
        
        creadoresTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        creadoresTitulo.setForeground(Color.WHITE);
        creadoresTitulo.setBounds(213, 110, 200, 25);
        
        panelFondo.add(creadoresTitulo);

        // Lista de autores: 
        // Creamos un array de String con los nombres de los autores. Se hace de esta manera para que sea
        // mucho más rápido añadir autores nuevos en un futuro, sólo habría que añadirles en el array.
        String[] nombres = {
            "Anxo Negreira", "Miguel Riveiro", "Xoel Mauri", 
            "Lukas Ouro", "Ruben González", "Enrique Martín", "Bieito Martínez"
        };
        
        // Definimos el borde de los contenedores que van a tener cada autor con la variable bordeSimple
        // para no tener que definirla luego cuando tengamos que usarla.
        javax.swing.border.Border bordeSimple = BorderFactory.createLineBorder(Color.BLACK, 2);
        int posicionY = 145; 
        
        // Con un bucle for each recorremos el array de los autores "nombres" y para cada autor creamos un
        // JLabel con el nombre y las características de ese texto (centrado, color, negrita, tamaño y borde).
        for (String nombre : nombres) {
        	
            JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
            lblNombre.setFont(new Font("Arial", Font.BOLD, 12)); 
            lblNombre.setForeground(Color.WHITE);
            // LLamamos a la variable bordeSimple creada anteriormente.
            lblNombre.setBorder(bordeSimple); 
            
            lblNombre.setBounds(183, posicionY, 260, 30); 
            panelFondo.add(lblNombre);
            
            // Esta es la posición vertical que tendrá cada recuadro de autor, se le suma un 42 porque si no se
            // hiciera estarían todos los autores uno encima de otro.
            posicionY = posicionY + 42; 
        }

        // Texto de contacto:
        // Generamos otro contenedor con JLabel con la misma plantilla que el resto de textos, lo único que cambia
        // es que es font.ITALIC para que se vea en cursiva.
        JLabel lblContacto = new JLabel("Contacto: xoelmauri@icloud.com", SwingConstants.CENTER);
        lblContacto.setFont(new Font("Arial", Font.ITALIC, 12));
        lblContacto.setForeground(Color.WHITE);
        lblContacto.setBounds(113, 470, 400, 25);
        panelFondo.add(lblContacto);

        // Botón de salir/volver al menú:
        // Generamos otra URL para poner la imagen salir.png y que se vea bien.
        URL urlSalir = getClass().getResource("recurso/salir.png");
        
        // Generamos un JButton donde estará el botón de salir, específicamente en la imagen salir.png.
        JButton btnSalir;
            ImageIcon iconoBoton = new ImageIcon(urlSalir);
            // En esta línea asignamos las escalas de la imagen para que no se vea ni mal posicionada ni borrosa.
            btnSalir = new JButton(new ImageIcon(iconoBoton.getImage().getScaledInstance(150, 40, Image.SCALE_SMOOTH)));
            // Estos tres parámetros desactivan las características del botón predeterminado de Windows, como son el borde,
            // el relleno gris y el recuadro de puntos cuando haces clic, así únicamente estará la imagen como botón.
            btnSalir.setBorderPainted(false); 
            btnSalir.setContentAreaFilled(false); 
            btnSalir.setFocusPainted(false);
        
        // Asignamos la imagen en los píxeles que queremos y usamos un .setCursor, para que cuando tengamos el cursor sobre
        // el botón el cursor automáticamente cambie al de la mano cuando estamos a punto de clickear en un sitio.
        btnSalir.setBounds(238, 540, 150, 40);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR)); 

        // Controlador del evento del botón:
        // Se queda escuchando el evento, cuando se clickea el botón la acción dispose(); cierra la ventana Créditos.
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        // Nos aseguramos de que salga el botón Salir con un .add
        panelFondo.add(btnSalir);

        // Asignación del contenedor: lo que hace esta línea es definir que el contenido del panel sea el que hemos hecho
        // anteriormente, ya que predeterminado sale un recuadro gris que quita absolutamente todo.
        setContentPane(panelFondo);
    }
}
