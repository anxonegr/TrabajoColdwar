package fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import planeta.Planeta;

public class Fichero {

    private static final String FICHERO_GANADORES = "src/ficheros/ganadores.txt";

    public static void mostrarGanadores() {

        File fichero = new File(FICHERO_GANADORES);

        if (!fichero.exists()) {
            // ── CAMBIO: antes -> System.out.println
            //           ahora  -> ventanita informativa
            JOptionPane.showMessageDialog(
                null,
                "No hay partidas registradas todavía.",
                "Historial de ganadores",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {

            StringBuilder contenido = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

            if (contenido.length() == 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "El historial está vacío.",
                    "Historial de ganadores",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                // ── CAMBIO: antes -> System.out.println línea a línea
                //           ahora  -> JOptionPane con todo el historial
                JOptionPane.showMessageDialog(
                    null,
                    "Nombre del planeta  —  Identificador\n\n" + contenido,
                    "Historial de ganadores",
                    JOptionPane.PLAIN_MESSAGE
                );
            }

        } catch (IOException e) {
            // ── CAMBIO: antes -> System.out.println("Error al leer el historial.")
            //           ahora  -> JOptionPane de error
            JOptionPane.showMessageDialog(
                null,
                "No se pudo leer el historial de ganadores.\nDetalle: " + e.getMessage(),
                "Error — Lectura de fichero",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void guardarGanador(Planeta g) {

        File fichero = new File(FICHERO_GANADORES);
        fichero.getParentFile().mkdirs();

        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException e) {
                // ── CAMBIO: antes -> System.out.println("  [!] No se pudo crear el fichero.")
                //           ahora  -> JOptionPane de error
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo crear el fichero de ganadores.\nDetalle: " + e.getMessage(),
                    "Error — Creación de fichero",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true))) {
            bw.write(g.getNombre() + " - " + g.getIdentificador());
            bw.newLine();
        } catch (IOException e) {
            // ── CAMBIO: antes -> System.out.println("  [!] No se pudo guardar el ganador: ...")
            //           ahora  -> JOptionPane de error
            JOptionPane.showMessageDialog(
                null,
                "No se pudo guardar el ganador en el historial.\nDetalle: " + e.getMessage(),
                "Error — Escritura de fichero",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
