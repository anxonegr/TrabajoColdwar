package trabajoColdwar;

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

/**
 * Clase principal del juego <b>ColdWar</b>.
 * <p>
 * Gestiona el menú principal, el flujo de la partida y la lógica de cada ronda.
 * Los equipos (planetas) se turnan para atacarse o curarse hasta que solo
 * queda uno en pie o todos son destruidos al mismo tiempo.
 * </p>
 *
 * <b>Apartado extra:</b> ejecuta el juego base añadiendo un bonus aleatorio
 * de 15 misiles a un equipo vivo al inicio de cada ronda par.
 *
 * @version 3.0
 */
public class TrabajoColdwar {

    /** Nombre del fichero donde se persiste el historial de ganadores. */
    private static final String FICHERO_GANADORES = "ganadores.txt";

    // ─────────────────────────────────────────────
    //  Punto de entrada
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            int opcion = menu(sc);
            switch (opcion) {
                case 1: jugar(sc, false);    break;
                case 2: reglas();            break;
                case 3: informacion();       break;
                case 4: jugar(sc, true);     break;
                case 5: mostrarGanadores();  break;
                case 0: salir = true;        break;
                default: System.out.println("  [!] Opcion invalida. Intentelo de nuevo.");
            }
        } while (!salir);

        sc.close();
        System.out.println("\n  Hasta la proxima, comandante!\n");
    }

    // ─────────────────────────────────────────────
    //  Utilidades de entrada
    // ─────────────────────────────────────────────

    public static int leerNumero(Scanner sc) {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("  Entrada no valida. Introduce un numero: ");
                sc.next();
            }
        }
    }

    // ─────────────────────────────────────────────
    //  Menú principal
    // ─────────────────────────────────────────────

    public static int menu(Scanner sc) {
        System.out.println();
        System.out.println("  ╔══════════════════════════╗");
        System.out.println("  ║      ☢   COLDWAR   ☢    ║");
        System.out.println("  ╠══════════════════════════╣");
        System.out.println("  ║  1.  Jugar               ║");
        System.out.println("  ║  2.  Reglas              ║");
        System.out.println("  ║  3.  Informacion         ║");
        System.out.println("  ║  4.  Apartado extra      ║");
        System.out.println("  ║  5.  Historial ganadores ║");
        System.out.println("  ║  0.  Salir               ║");
        System.out.println("  ╚══════════════════════════╝");
        System.out.print("  Seleccione una opcion: ");
        return leerNumero(sc);
    }

    // ─────────────────────────────────────────────
    //  Selección de tipo de planeta  ← ÚNICA PARTE QUE CAMBIA
    // ─────────────────────────────────────────────

    /**
     * Muestra el menú de tipos de planeta y devuelve la instancia correcta.
     * <p>
     * Si el tipo elegido es "gaseoso" o "enano" crea un {@link PlanetaEspecial};
     * para el resto crea un {@link Planeta} normal.
     * El tipo de retorno es siempre {@link Planeta} (polimorfismo).
     * </p>
     *
     * @param sc     el {@link Scanner} de entrada
     * @param nombre nombre del equipo ya elegido
     * @return instancia de {@link Planeta} o {@link PlanetaEspecial}
     */
    public static Planeta seleccionarTipo(Scanner sc, String nombre) {
        System.out.println("  Elige el tipo de planeta:");
        System.out.println("    1. Normal          (200 vidas, 50 misiles/ronda)");
        System.out.println("    2. Rojo            (x2 al verde, /2 al azul)");
        System.out.println("    3. Azul            (x2 al rojo,  /2 al verde)");
        System.out.println("    4. Verde           (x2 al azul,  /2 al rojo)");
        System.out.println("    5. Gigante gaseoso (400 vidas, empieza con 10 misiles +10/ronda)");
        System.out.println("    6. Planeta enano   (100 vidas, 50% de esquive)");

        boolean valido = false;
        Planeta nuevo  = null;

        while (!valido) {
            System.out.print("  Tipo: ");
            int eleccion = leerNumero(sc);

            if      (eleccion == 1) { nuevo = new PlanetaNormal(nombre, "normal");    valido = true; }
            else if (eleccion == 2) { nuevo = new PlanetaNormal(nombre, "rojo");      valido = true; }
            else if (eleccion == 3) { nuevo = new PlanetaNormal(nombre, "azul");      valido = true; }
            else if (eleccion == 4) { nuevo = new PlanetaNormal(nombre, "verde");     valido = true; }
            else if (eleccion == 5) { nuevo = new PlanetaEspecial(nombre, "gaseoso"); valido = true; }
            else if (eleccion == 6) { nuevo = new PlanetaEspecial(nombre, "enano");   valido = true; }
        }

        return nuevo;
    }

    // ─────────────────────────────────────────────
    //  Historial de ganadores
    // ─────────────────────────────────────────────

    public static void guardarGanador(String nombreGanador, String identificador) {
        File fichero = new File(FICHERO_GANADORES);
        boolean existia = fichero.exists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true))) {
            if (!existia) {
                bw.write("Nombre de planetas ganadores - Identificador");
                bw.newLine();
            }
            bw.write(nombreGanador + " - " + identificador);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("  [!] No se pudo guardar el ganador: " + e.getMessage());
        }
    }

    public static void mostrarGanadores() {
        File fichero = new File(FICHERO_GANADORES);

        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║         HISTORIAL DE GANADORES          ║");
        System.out.println("  ╠══════════════════════════════════════════╣");

        if (!fichero.exists()) {
            System.out.println("  ║  Aun no hay partidas registradas.       ║");
            System.out.println("  ╚══════════════════════════════════════════╝");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea = "";
            int partida = 1;
            boolean hayRegistros = false;

            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {
                    System.out.println("  ║  Partida " + partida + ": " + linea);
                    partida++;
                    hayRegistros = true;
                }
            }

            if (!hayRegistros) {
                System.out.println("  ║  Aun no hay partidas registradas.       ║");
            }

        } catch (IOException e) {
            System.out.println("  ║  Error al leer el historial: " + e.getMessage());
        }

        System.out.println("  ╚══════════════════════════════════════════╝");
    }

    // ─────────────────────────────────────────────
    //  Lógica del juego
    // ─────────────────────────────────────────────

    public static void jugar(Scanner sc, boolean conBonus) {

        if (conBonus) {
            System.out.println("\n  [APARTADO EXTRA] Modo con bonus aleatorio de misiles activado.");
        }

        System.out.print("\n  Cuantos equipos van a jugar? (minimo 3): ");
        int numPlanetas = leerNumero(sc);
        while (numPlanetas < 3) {
            System.out.print("  Debe haber al menos 3 equipos. Introduce otro numero: ");
            numPlanetas = leerNumero(sc);
        }

        ArrayList<Planeta> planetas = new ArrayList<>();

        for (int i = 0; i < numPlanetas; i++) {
            String nombre = "";
            boolean repetido = false;

            do {
                repetido = false;
                System.out.print("  Nombre del equipo " + (i + 1) + ": ");
                nombre = sc.next();

                for (Planeta p : planetas) {
                    if (p.getNombre().equalsIgnoreCase(nombre)) {
                        repetido = true;
                        System.out.println("Ese nombre ya está en uso, prueba otro.");
                        break;
                    }
                }
            } while (repetido);

            
            Planeta nuevo = seleccionarTipo(sc, nombre);
            
         // BUCLE PARA EL IDENTIFICADOR 
            String id = "";
            boolean idValido = false;
            
            while (!idValido) {
                System.out.print("  Introduce el identificador (4 números y 3 letras mayúsculas, ej: 1111ABC): ");
                id = sc.next();

                if (!trabajoColdwar_Utils.Validaciones.validarIdentificador(id)) {
                    System.out.println("  [!] Error: El identificador es incorrecto. Debe tener 4 números y 3 letras mayúsculas.");
                } else {
                    
                    boolean idRepetido = false;
                    for (Planeta p : planetas) {
                        if (p.getIdentificador().equalsIgnoreCase(id)) {
                            idRepetido = true;
                            break;
                        }
                    }

                    if (idRepetido) {
                        System.out.println("  [!] Ese identificador ya está en uso, prueba otro.");
                    } else {
                        idValido = true;
                    }
                }
            }
            
            nuevo.setIdentificador(id);
            
            
            planetas.add(nuevo);
            System.out.println("  -> " + nombre + " creado como " + nuevo.getNombreTipo()
                    + " con " + nuevo.getVidas() + " vidas y "
                    + nuevo.getMisilesRonda() + " misiles iniciales.");
        }

        Random  random      = new Random();
        int     ronda       = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {

            String logRonda = "";

            System.out.println("\n  ╔══════════════════════════════╗");
            System.out.println("  ║         RONDA  " + ronda + "              ║");
            System.out.println("  ╚══════════════════════════════╝");

            for (Planeta p : planetas) {
                p.reponerMisiles(ronda);
            }

            if (conBonus && ronda % 2 == 0) {
                int idx = random.nextInt(planetas.size());
                Planeta p = planetas.get(idx);
                if (p.getVidas() > 0) {
                    p.setMisilesRonda(p.getMisilesRonda() + 15);
                    System.out.println("\n  BONUS: el equipo '" + p.getNombre()
                            + "' recibe 15 misiles extra.");
                }
            }

            for (Planeta p : planetas) {
                if (p.getVidas() > 0 || p.isEliminadoEstaRonda()) {
                    logRonda += jugarTurno(p, planetas, sc, ronda);
                    p.setEliminadoEstaRonda(false);
                }
            }

            int vivos = resumenRonda(planetas, ronda, logRonda);
            if (vivos <= 1) {
                juegoActivo = false;
            }
            ronda++;
        }

        Planeta ganador = null;
        int vivosFinal  = 0;

        for (Planeta p : planetas) {
            if (p.getVidas() > 0) {
                vivosFinal++;
                ganador = p;
            }
        }

        System.out.println();
        System.out.println("  ╔══════════════════════════════╗");
        if (vivosFinal == 1) {
            System.out.println("  ║  GANADOR: " + ganador.getNombre() + "           ║");
            guardarGanador(ganador.getNombre(), ganador.getIdentificador());
            System.out.println("  ║  (resultado guardado)        ║");
        } else {
            System.out.println("  ║  EMPATE: todos destruidos    ║");
            System.out.println("  ║  (el empate no se registra)  ║");
        }
        System.out.println("  ╚══════════════════════════════╝");
    }

    static String jugarTurno(Planeta atacante, ArrayList<Planeta> listaPlanetas, Scanner sc, int ronda) {

        String logTurno = "";

        while (atacante.getMisilesRonda() > 0) {

            System.out.println("\n  ┌──────────────────────────────────────┐");
            System.out.println("  │  TURNO: " + atacante.getNombre()
                    + " (" + atacante.getNombreTipo() + ")");
            System.out.println("  │  Misiles disponibles: " + atacante.getMisilesRonda());
            System.out.println("  │  Vidas: " + atacante.getVidas());
            System.out.println("  └──────────────────────────────────────┘");

            System.out.println("  A que planeta quieres atacar?");

            ArrayList<Integer> indicesValidos = new ArrayList<>();
            int opc = 1;

            for (int i = 0; i < listaPlanetas.size(); i++) {
                Planeta p = listaPlanetas.get(i);
                if (p == atacante) continue;
                indicesValidos.add(i);
                
                String estadoStr = p.getVidas() > 0 ? p.getNombreTipo() : "Eliminado";
                
                System.out.println("    " + opc + ". " + p.getNombre() + " " + estadoStr);
                opc++;
            }

            if (ronda > 1) {
                System.out.println("    0. Convertir misiles restantes en defensa");
            }

            int numObjetivo       = -2;
            boolean objetivoValido = false;

            while (!objetivoValido) {
                System.out.print("  Objetivo: ");
                int eleccion = leerNumero(sc);

                if (eleccion == 0 && ronda == 1) eleccion = -99;

                if (eleccion == 0) {
                    numObjetivo = -1;
                    objetivoValido = true;
                } else if (eleccion >= 1 && eleccion <= indicesValidos.size()) {
                    int indiceReal = indicesValidos.get(eleccion - 1);
                    if (listaPlanetas.get(indiceReal).getVidas() <= 0) {
                        System.out.println("  [!] Ese planeta ya ha sido eliminado.");
                    } else {
                        numObjetivo = indiceReal;
                        objetivoValido = true;
                    }
                } else {
                    System.out.println("  [!] Opcion no valida.");
                }
            }

            if (numObjetivo == -1) {
                int curacion = atacante.getMisilesRonda() / 2;
                atacante.setVidas(atacante.getVidas() + curacion);
                String msgCuracion = "  [+] " + atacante.getNombre() + " se ha curado "
                        + curacion + " puntos. Vidas totales: " + atacante.getVidas();
                logTurno += msgCuracion + "\n";
                atacante.setMisilesRonda(0);
                break;
            }

            int     misilesAtacar  = 0;
            boolean cantidadValida = false;
            while (!cantidadValida) {
                System.out.print("  Cuantos misiles quieres usar? (max "
                        + atacante.getMisilesRonda() + "): ");
                misilesAtacar = leerNumero(sc);
                if (misilesAtacar <= 0) {
                    System.out.println("  [!] Debes usar al menos 1 misil.");
                } else if (misilesAtacar > atacante.getMisilesRonda()) {
                    System.out.println("  [!] No tienes suficientes misiles.");
                } else {
                    cantidadValida = true;
                }
            }

            logTurno += listaPlanetas.get(numObjetivo).combate(misilesAtacar, atacante);
        }

        System.out.println("  >> " + atacante.getNombre() + " ha agotado sus misiles.");
        return logTurno;
    }

    public static int resumenRonda(ArrayList<Planeta> planetas, int ronda, String logRonda) {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      RESUMEN  RONDA  " + ronda + "                   ║");
        System.out.println("  ╠═══════════════════╦══════╦═══════════════╣");
        System.out.println("  ║ Equipo            ║ Vida ║ Tipo          ║");
        System.out.println("  ╠═══════════════════╬══════╬═══════════════╣");

        int vivos = 0;
        for (Planeta p : planetas) {
            String estadoStr = "";
            if (p.getVidas() > 0) {
                estadoStr = p.getNombreTipo();
                vivos++;
            } else {
                estadoStr = "Eliminado";
            }
            System.out.println("  ║ " + p.getNombre()
                    + "                   ║ " + p.getVidas()
                    + "   ║ " + estadoStr + "       ║");
        }

        System.out.println("  ╠═══════════════════╩══════╩═══════════════╣");
        System.out.println("  ║  Equipos vivos: " + vivos + "                          ║");
        System.out.println("  ╚══════════════════════════════════════════╝");

        System.out.println("  ╠═══════════════════════════════════════════╣");
        System.out.println("  ║            ATAQUES DE LA RONDA            ║");
        System.out.println("  ╠═══════════════════════════════════════════╣");
        System.out.println(logRonda);
        System.out.println("  ╚══════════════════════════════════════════╝");

        return vivos;
    }

    public static void reglas() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════════╗");
        System.out.println("  ║               REGLAS DEL JUEGO                   ║");
        System.out.println("  ╠═══════════════════════════════════════════════════╣");
        System.out.println("  ║  * Cada equipo empieza con vidas segun su tipo.  ║");
        System.out.println("  ║  * Se reparten misiles por ronda segun el tipo.  ║");
        System.out.println("  ║  * Ataca a otros equipos con misiles.            ║");
        System.out.println("  ║  * Elige 0 para curar (misiles / 2).            ║");
        System.out.println("  ║  * A 0 vidas, el equipo es eliminado.           ║");
        System.out.println("  ╠═══════════════════════════════════════════════════╣");
        System.out.println("  ║               TIPOS DE PLANETA                   ║");
        System.out.println("  ╠═══════════════════════════════════════════════════╣");
        System.out.println("  ║  Normal:          200 vidas, 50 misiles/ronda.  ║");
        System.out.println("  ║  Rojo:            x2 al verde, /2 al azul.      ║");
        System.out.println("  ║  Azul:            x2 al rojo,  /2 al verde.     ║");
        System.out.println("  ║  Verde:           x2 al azul,  /2 al rojo.      ║");
        System.out.println("  ║  Gigante gaseoso: 400 vidas, 10 mis. +10/ronda. ║");
        System.out.println("  ║  Planeta enano:   100 vidas, 50% de esquive.    ║");
        System.out.println("  ╠═══════════════════════════════════════════════════╣");
        System.out.println("  ║  APARTADO EXTRA: bonus aleatorio cada 2 rondas. ║");
        System.out.println("  ╚═══════════════════════════════════════════════════╝");
    }

    public static void informacion() {
        File fichero = new File("informacion.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            bw.write("  Version : 3.0");           bw.newLine();
            bw.write("  Autores : Miguel Riveiro"); bw.newLine();
            bw.write("  Autores : Rubén González"); bw.newLine();
            bw.write("  Autores : Anxo Negreira");  bw.newLine();
            bw.write("  Autores : Xoel Mauri");     bw.newLine();
            bw.write("  Autores : Bieito Martínez");bw.newLine();
            bw.write("  Autores : Enrique Martín"); bw.newLine();
            bw.write("  Autores : Lukas Ouro");     bw.newLine();
            bw.write("  Contacto: xoelmauri@icloud.com"); bw.newLine();
        } catch (IOException e) {
            System.out.println("  [!] No se pudo guardar la informacion: " + e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║               INFORMACION                ║");
        System.out.println("  ╠══════════════════════════════════════════╣");

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("  ║  " + linea);
            }
        } catch (IOException e) {
            System.out.println("  [!] No se pudo leer la informacion: " + e.getMessage());
        }

        System.out.println("  ╚══════════════════════════════════════════╝");
    }
}
