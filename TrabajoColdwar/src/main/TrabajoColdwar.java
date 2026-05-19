package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import planeta.Planeta;
import tipos.*;
import trabajoColdWar_Utils.Mensaje;
import trabajoColdWar_Utils.Validaciones;
import fichero.Fichero;


/**
 * Clase principal del juego ColdWar.
 * <p>
 * Gestiona el menú principal, la creación de planetas y el desarrollo
 * completo de la partida.
 * </p>
 *
 * @version 4.0
 */
public class TrabajoColdwar {
    
    private static Scanner sc = new Scanner(System.in);

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

    	
        boolean salir = false;

        do {
            int opcion = menu();

            switch (opcion) {
                case 1:
                    jugar(false);
                    break;
                case 2:
                    reglas();
                    break;
                case 3:
                    Mensaje.informacion();
                    break;
                case 4:
                    jugar(true);
                    break;
                case 5:
                    Fichero.mostrarGanadores();
                    break;
                case 0:
                    salir = true;
                    break;
                default:
                    System.out.println("  [!] Opción inválida.");
            }
        } while (!salir);

        sc.close();
        System.out.println("\nHasta la próxima, comandante.\n");
    }

    /**
     * Lee un número entero controlando errores de entrada.
     *
     * @param sc scanner de entrada
     * @return número introducido
     */
    public static int leerNumero() {
    	
        while (true) {
        	
            try {
            	
                return sc.nextInt();
                
            } catch (InputMismatchException e) {
            	
                System.out.print("  Entrada no válida. Introduce un número: ");
                sc.next();
                
            }
        }
    }

    /**
     * Muestra el menú principal.
     *
     * @param sc scanner de entrada
     * @return opción seleccionada
     */
    public static int menu() {
        Mensaje.menu();

        return leerNumero();
    }

    /**
     * Crea un planeta según el tipo seleccionado.
     *
     * @param sc scanner de entrada
     * @param nombre nombre del planeta
     * @return planeta creado
     */
    public static Planeta seleccionarTipo(String nombre) {
    	
        Mensaje.menuTipo();

        while (true) {
        	
            System.out.print("  Tipo: ");
            int eleccion = leerNumero();

            switch (eleccion) {
                case 1:
                    return new PlanetaNormal(nombre);
                case 2:
                    return new PlanetaRojo(nombre);
                case 3:
                    return new PlanetaAzul(nombre);
                case 4:
                    return new PlanetaVerde(nombre);
                case 5:
                    return new PlanetaGaseoso(nombre);
                case 6:
                    return new PlanetaEnano(nombre);
                default:
                    System.out.println("  [!] Opción no válida.");
            }
        }
    }




    /**
     * Ejecuta una partida completa.
     *
     * @param sc scanner de entrada
     * @param conBonus indica si se activa el modo bonus
     */
    public static void jugar(boolean conBonus) {
    	
    	int numPlanetas = 0;
    	ArrayList<Planeta> planetas = new ArrayList<>();
    	ArrayList<String> nombres = new ArrayList<>();
    	ArrayList<String> ids = new ArrayList<>();
    	
    	//pedimos equipos
        System.out.print("\n¿Cuántos equipos van a jugar? (mínimo 3): ");
        numPlanetas = leerNumero();
        
        //si el numero de planetas es menor de tres se vuelve a pedir
        while (numPlanetas < 3) {
        	
            System.out.print("Debe haber al menos 3 equipos: ");
            numPlanetas = leerNumero();
            
        }

        //pedimos equipos
        for (int i = 0; i < numPlanetas; i++) {
        	
        	String id = "";
        	String nombre = "";
        	boolean repetido = false;
        	
        	//pedimos nombre y comprobamos que no este repetido
        	do {
        		
	            System.out.print("Nombre del equipo " + (i + 1) + ": ");
	            nombre = sc.next();
	            
	            if(nombre.contains(nombre)) {
	            	
	            	System.out.println("Nombre repetido");
	            }
        	}while(nombres.contains(nombre));
        	
        	//añadimos a la lista para poder comprobar
            nombres.add(nombre);

            //pedimos tipo de planeta
            Planeta nuevo = seleccionarTipo(nombre);

            //pedimos y comprobamos el identificador
            do {
            	
            	
	            System.out.print("Identificador (ej. 1234ABC): ");
	            id = sc.next();
	            
	            repetido = !Validaciones.validarIdentificador(id);
	            
	            if(ids.contains(id)) {
	            	
	            	System.out.println("Id repetido");
	            	
	            }else if(repetido) {
	            	
	            	System.out.println("patron no valido");
	            	
	            }
	            
            }while(ids.contains(id) || repetido);
            
            //anhadimos a la lista para poder comprobar
            ids.add(id);
            
            //asignamos identificador
            nuevo.setIdentificador(id);
            planetas.add(nuevo);

            //informamos de equipo creado
            System.out.println("  -> " + nuevo.getNombre() + " creado como "
                    + Planeta.nombreTipo(nuevo) + " con "
                    + nuevo.getVidas() + " vidas y "
                    + nuevo.getMisilesRonda() + " misiles.");
        }

        
        Random random = new Random();
        int ronda = 0;
        boolean juegoActivo = true;

        
        while (juegoActivo) {
        	
            String logRonda = "";

            System.out.println("\n===== RONDA " + ++ronda + " =====");
            
            
            if (conBonus && ronda % 2 == 0) {
            	
            	//hacemos referencia a un planeta aleatorio entre los planetas que estan jugando 
                Planeta p = planetas.get(random.nextInt(planetas.size()));
                
                
                if (p.getVidas() > 0) {
                	
                	//le damos un bonus de 15 misiles
                    p.setMisilesRonda(p.getMisilesRonda() + 15);
                    
                    //informamos al usuario
                    System.out.println("BONUS: " + p.getNombre()
                            + " recibe 15 misiles extra.");
                }
            }

            //empezamos juego recorriendo todos los equipos y comprobando quien tiene vida y no esta eliminado
            for (Planeta p : planetas) {
                if (p.getVidas() > 0 || p.isEliminadoEstaRonda()) {
                    p.reponerMisiles(ronda);
                    logRonda += jugarTurno(p, planetas, ronda);
                    
                    if (p.isEliminadoEstaRonda()) {
                        p.setEliminadoEstaRonda(false);
                        p.setVidas(0);
                    }
                }
            }

            int vivos = resumenRonda(planetas, ronda, logRonda);

            if (vivos <= 1) {
            	
                juegoActivo = false;
                
            }

            
        }
        
        Planeta ganador = null;
        int vivosFinal = 0;
        
        //comprobamos el planeta que tiene vidas y le asignamos el plantata ganador
        for (Planeta p : planetas) {
        	
            if (p.getVidas() > 0) {
            	
                ganador = p;
                vivosFinal++;
                
            }
        }
        
        //ternario
       System.out.println(vivosFinal == 1 ? "\nGANADOR: " + ganador.getNombre() : "\nEMPATE: todos los planetas han sido destruidos");
        
        //guardamos el ganador en el fichero
        if (vivosFinal == 1) {

            Fichero.guardarGanador(ganador);
            
        }
        
     // Mostrar misiles esquivados de planetas enanos        
        for (Planeta p : planetas) {
        	
            if (p instanceof PlanetaEnano) {
            	
                PlanetaEnano enano = (PlanetaEnano) p;
                System.out.println(
                    enano.getNombre()
                    + " ha esquivado "
                    + enano.getMisilesEsquivados()
                    + " misiles en total."
                );
                
            }
        }
    }

    /**
     * Ejecuta el turno de un planeta.
     *
     * @param atacante planeta que ataca
     * @param listaPlanetas lista de planetas
     * @param sc scanner de entrada
     * @param ronda número de ronda
     * @return log del turno
     */
    public static String jugarTurno(Planeta atacante, ArrayList<Planeta> listaPlanetas, int ronda) {

        String logTurno = "";

        while (atacante.getMisilesRonda() > 0) {

            ArrayList<Integer> indicesValidos = new ArrayList<>();
            int opcion = 0;
            int eleccion = 0;

            System.out.println("\nTurno de " + atacante.getNombre()
                    + " (" + Planeta.nombreTipo(atacante) + ")");
            System.out.println("Misiles: " + atacante.getMisilesRonda());

            for (int i = 0; i < listaPlanetas.size(); i++) {
                Planeta p = listaPlanetas.get(i);
                if (!atacante.equals(p) && (p.getVidas() > 0 || p.isEliminadoEstaRonda())) {
                    System.out.println(++opcion + ". " + p.getNombre());
                    indicesValidos.add(i);
                }
            }

            // Si no hay objetivos validos salimos
            if (indicesValidos.isEmpty()) {
                System.out.println("No hay objetivos disponibles.");
                break;
            }

            if (ronda > 1) {
                System.out.println("0. Convertir misiles restantes en defensa");
            }

            System.out.print("Objetivo: ");
            eleccion = leerNumero();

            if (eleccion == 0 && ronda != 1) {
                logTurno += atacante.defensa();
                break;
            }

            if (eleccion < 1 || eleccion > indicesValidos.size()) {
                System.out.println("Opción no válida.");
                continue;
            }

            Planeta objetivo = listaPlanetas.get(indicesValidos.get(eleccion - 1));

            System.out.print("¿Cuántos misiles quieres usar? ");
            int misiles = leerNumero();

            if (misiles <= 0 || misiles > atacante.getMisilesRonda()) {
                System.out.println("Cantidad no válida.");
                continue;
            }

            logTurno += objetivo.combate(misiles, atacante);
        }

        return logTurno;
    }
    /**
     * Muestra el resumen de la ronda.
     *
     * @param planetas lista de planetas
     * @param ronda número de ronda
     * @param logRonda registro de ataques
     * @return número de planetas vivos
     */
    public static int resumenRonda(ArrayList<Planeta> planetas, int ronda, String logRonda) {

        System.out.println("\n===== RESUMEN RONDA " + ronda + " =====");

        int vivos = 0;

        for (Planeta p : planetas) {
        		System.out.println(p.getNombre() + " - "
        	        + p.getVidas() + " vidas - "
        	        + (p.getVidas() > 0
        	        ? Planeta.nombreTipo(p)
        	        : "Eliminado"));

            if (p.getVidas() > 0) {
            	
                vivos++;
                
            }
        }

        System.out.println("\nATAQUES DE LA RONDA:");
        System.out.println(logRonda);

        return vivos;
    }

    /**
     * Muestra las reglas del juego.
     */
    public static void reglas() {
        System.out.println("\nCada planeta tiene habilidades distintas.");
        System.out.println("Gana el último planeta que permanezca con vida.");
        
    }

   
}

