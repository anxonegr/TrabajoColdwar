package fichero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import planeta.Planeta;

public class Fichero {
	
    private static final String FICHERO_GANADORES = "src/ficheros/ganadores.txt";
    
    public static void mostrarGanadores() {
    	
        File fichero = new File(FICHERO_GANADORES);
        
        if (!fichero.exists()) {
            System.out.println("\nNo hay partidas registradas.\n");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            
            String linea;
            System.out.println("\nNombre de planetas ganadores - Identificador");
            
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            
        } catch (IOException e) {
            System.out.println("Error al leer el historial.");
        }
    }
    
    public static void guardarGanador(Planeta g) {
    	
    	File fichero = new File(FICHERO_GANADORES);
        fichero.getParentFile().mkdirs(); // crea la carpeta
        
        if (!fichero.exists()) {
            try {
                fichero.createNewFile(); // crea el fichero
            } catch (IOException e) {
                System.out.println("  [!] No se pudo crear el fichero.");
                return;
            }
        }
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true))) {
            bw.write(g.getNombre() + " - " + g.getIdentificador());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("  [!] No se pudo guardar el ganador: " + e.getMessage());
        }
    }
}