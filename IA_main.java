package trabajoColdwar;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
 * @version 2.0
 */
public class TrabajoColdwar {

    // ─────────────────────────────────────────────
    //  Punto de entrada
    // ─────────────────────────────────────────────

    /**
     * Punto de entrada del programa.
     * Muestra el menú principal en bucle hasta que el usuario elige salir.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            int opcion = menu(sc);
            switch (opcion) {
                case 1: jugar(sc, false); break;
                case 2: reglas();         break;
                case 3: informacion();    break;
                case 4: jugar(sc, true);  break;
                case 5: mostrarGanadores(); break; //añadimos una opción para ver los ganadores 
                case 0: salir = true;     break;
                default: System.out.println("  [!] Opcion invalida. Intentelo de nuevo.");
            }
        } while (!salir);

        sc.close();
        System.out.println("\n  Hasta la proxima, comandante!\n");
    }

    // ─────────────────────────────────────────────
    //  Utilidades de entrada
    // ─────────────────────────────────────────────

    /**
     * Lee un número entero desde la consola, descartando cualquier entrada
     * no numérica para evitar que el programa lance una excepción.
     *
     * @param sc el {@link Scanner} de entrada
     * @return el número entero válido introducido por el usuario
     */
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

    /**
     * Imprime el menú principal y devuelve la opción elegida por el usuario.
     *
     * @param sc el {@link Scanner} de entrada
     * @return número de opción seleccionada
     */
    public static int menu(Scanner sc) {
        System.out.println();
        System.out.println("  ╔══════════════════════════╗");
        System.out.println("  ║      ☢   COLDWAR   ☢    ║");
        System.out.println("  ╠══════════════════════════╣");
        System.out.println("  ║  1.  Jugar               ║");
        System.out.println("  ║  2.  Reglas              ║");
        System.out.println("  ║  3.  Informacion         ║");
        System.out.println("  ║  4.  Apartado extra      ║");
        System.out.println("  ║  0.  Salir               ║");
        System.out.println("  ╚══════════════════════════╝");
        System.out.print("  Seleccione una opcion: ");
        return leerNumero(sc);
    }

    // ─────────────────────────────────────────────
    //  Selección de tipo de planeta
    // ─────────────────────────────────────────────

    /**
     * Muestra el menú de tipos de planeta y devuelve el tipo elegido como String.
     * Repite la pregunta si la opción no es válida.
     *
     * @param sc el {@link Scanner} de entrada
     * @return tipo de planeta en minúsculas ("normal", "rojo", "azul", "verde",
     *         "gaseoso" o "enano")
     */
    public static String seleccionarTipo(Scanner sc) {
        System.out.println("  Elige el tipo de planeta:");
        System.out.println("    1. Normal          (200 vidas, 50 misiles/ronda)");
        System.out.println("    2. Rojo            (x2 al verde, /2 al azul)");
        System.out.println("    3. Azul            (x2 al rojo,  /2 al verde)");
        System.out.println("    4. Verde           (x2 al azul,  /2 al rojo)");
        System.out.println("    5. Gigante gaseoso (400 vidas, empieza con 10 misiles +10/ronda)");
        System.out.println("    6. Planeta enano   (100 vidas, 50% de esquive)");

        String tipo  = "";
        boolean valido = false;

        while (!valido) {
            System.out.print("  Tipo: ");
            int eleccion = leerNumero(sc);

            if (eleccion == 1) { tipo = "normal";  valido = true; }
            else if (eleccion == 2) { tipo = "rojo";    valido = true; }
            else if (eleccion == 3) { tipo = "azul";    valido = true; }
            else if (eleccion == 4) { tipo = "verde";   valido = true; }
            else if (eleccion == 5) { tipo = "gaseoso"; valido = true; }
            else if (eleccion == 6) { tipo = "enano";   valido = true; }
            else {
                System.out.println("  [!] Opcion no valida. Elige un numero entre 1 y 6.");
            }
        }

        return tipo;
    }

    // ─────────────────────────────────────────────
    //  Lógica del juego
    // ─────────────────────────────────────────────

    /**
     * Gestiona el flujo completo de una partida.
     * <p>
     * Solicita el número de equipos, sus nombres y sus tipos de planeta.
     * Ejecuta las rondas en bucle y, al terminar, anuncia al ganador o declara empate.
     * </p>
     * <p>
     * Si {@code conBonus} es {@code true} (apartado extra), al inicio de cada
     * ronda par se otorgan 15 misiles extra a un equipo vivo elegido al azar.
     * </p>
     *
     * @param sc       el {@link Scanner} de entrada
     * @param conBonus {@code true} para activar el bonus aleatorio de misiles
     */
    public static void jugar(Scanner sc, boolean conBonus) {

        if (conBonus) {
            System.out.println("\n  [APARTADO EXTRA] Modo con bonus aleatorio de misiles activado.");
        }

        // Número de equipos (mínimo 2)
        System.out.print("\n  Cuantos equipos van a jugar? (minimo 2): ");
        int numPlanetas = leerNumero(sc);
        while (numPlanetas < 2) {
            System.out.print("  Debe haber al menos 2 equipos. Introduce otro numero: ");
            numPlanetas = leerNumero(sc);
        }

        // Creación de planetas
        Planeta[] planetas = new Planeta[numPlanetas];
        for (int i = 0; i < numPlanetas; i++) {
            System.out.print("  Nombre del equipo " + (i + 1) + ": ");
            String nombre = sc.next();
            String tipo   = seleccionarTipo(sc);
            planetas[i]   = new Planeta(nombre, tipo);
            System.out.println("  -> " + nombre + " creado como " + planetas[i].getNombreTipo()
                    + " con " + planetas[i].getVidas() + " vidas y "
                    + planetas[i].getMisilesRonda() + " misiles iniciales.");
        }

        Random  random     = new Random();
        int     ronda      = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {

            System.out.println("\n  ╔══════════════════════════════╗");
            System.out.println("  ║         RONDA  " + ronda + "              ║");
            System.out.println("  ╚══════════════════════════════╝");

            // Reposición de misiles al inicio de la ronda
            for (Planeta p : planetas) {
                p.reponerMisiles(ronda);
            }

            // Bonus aleatorio cada 2 rondas (solo modo extra)
            if (conBonus && ronda % 2 == 0) {
                int idx = random.nextInt(planetas.length);
                if (planetas[idx].getVidas() > 0) {
                    planetas[idx].setMisilesRonda(planetas[idx].getMisilesRonda() + 15);
                    System.out.println("\n  BONUS: el equipo '" + planetas[idx].getNombre()
                            + "' recibe 15 misiles extra.");
                }
            }

            // Turno de cada planeta que siga vivo
            for (Planeta p : planetas) {
                if (p.getVidas() > 0) {
                    jugarTurno(p, planetas, sc);
                }
            }

            // Resumen al final de la ronda
            int vivos = resumenRonda(planetas, ronda);
            if (vivos <= 1) {
                juegoActivo = false;
            }
            ronda++;
        }

        // Determinar ganador o empate
        Planeta ganador    = null;
        int     vivosFinal = 0;
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
        } else {
            System.out.println("  ║  EMPATE: todos destruidos    ║");
        }
        System.out.println("  ╚══════════════════════════════╝");
    }

    /**
     * Gestiona el turno completo de un planeta atacante.
     * <p>
     * El bucle continúa mientras el atacante tenga misiles disponibles.
     * En cada iteración el jugador elige:
     * </p>
     * <ul>
     *   <li>Un planeta enemigo al que atacar y cuántos misiles usar.</li>
     *   <li>La opción {@code 0} para convertir todos los misiles restantes
     *       en puntos de vida (ratio 1 misil → 0,5 vidas).</li>
     * </ul>
     *
     * @param atacante      el {@link Planeta} que realiza el ataque en este turno
     * @param listaPlanetas array con todos los planetas de la partida
     * @param sc            el {@link Scanner} de entrada
     */
    static void jugarTurno(Planeta atacante, Planeta[] listaPlanetas, Scanner sc) {

        while (atacante.getMisilesRonda() > 0) {

            // Cabecera del turno
            System.out.println("\n  ┌──────────────────────────────────────┐");
            System.out.println("  │  TURNO: " + atacante.getNombre()
                    + " (" + atacante.getNombreTipo() + ")");
            System.out.println("  │  Misiles disponibles: " + atacante.getMisilesRonda());
            System.out.println("  │  Vidas: " + atacante.getVidas());
            System.out.println("  └──────────────────────────────────────┘");

            // Mostrar lista de objetivos
            System.out.println("  A que planeta quieres atacar?");
            for (int i = 0; i < listaPlanetas.length; i++) {
                String estadoStr;
                if (listaPlanetas[i].getVidas() > 0) {
                    estadoStr = "(Vidas: " + listaPlanetas[i].getVidas()
                            + " | " + listaPlanetas[i].getNombreTipo() + ")";
                } else {
                    estadoStr = "[ELIMINADO]";
                }
                System.out.println("    " + (i + 1) + ". " + listaPlanetas[i].getNombre()
                        + " " + estadoStr);
            }
            System.out.println("    0. Convertir misiles restantes en defensa");

            int     numObjetivo    = -2;
            boolean objetivoValido = false;

            while (!objetivoValido) {
                System.out.print("  Objetivo: ");
                int eleccion = leerNumero(sc);
                numObjetivo  = eleccion - 1;

                if (numObjetivo == -1) {
                    objetivoValido = true;

                } else if (numObjetivo >= 0 && numObjetivo < listaPlanetas.length) {
                    if (listaPlanetas[numObjetivo].getVidas() <= 0) {
                        System.out.println("  [!] Ese planeta ya ha sido eliminado. Elige otro.");
                    } else if (listaPlanetas[numObjetivo].getNombre().equals(atacante.getNombre())) {
                        System.out.println("  [!] No puedes atacarte a ti mismo.");
                    } else {
                        objetivoValido = true;
                    }
                } else {
                    System.out.println("  [!] Opcion no valida. Introduce un numero"
                            + " entre 0 y " + listaPlanetas.length + ".");
                }
            }

            // Opción defensa
            if (numObjetivo == -1) {
                int curacion = atacante.getMisilesRonda() / 2;
                atacante.setVidas(atacante.getVidas() + curacion);
                System.out.println("  [+] Te has curado " + curacion
                        + " puntos de vida. Vidas totales: " + atacante.getVidas());
                atacante.setMisilesRonda(0);
                break;
            }

            // Leer cantidad de misiles para el ataque
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

            // Ejecutar el combate
            listaPlanetas[numObjetivo].combate(misilesAtacar, atacante);
        }

        System.out.println("  >> " + atacante.getNombre() + " ha agotado sus misiles.");
    }

    /**
     * Imprime el resumen de estado al final de una ronda y cuenta los equipos vivos.
     *
     * @param planetas array de planetas participantes
     * @param ronda    número de la ronda recién terminada
     * @return número de equipos que siguen con vida
     */
    public static int resumenRonda(Planeta[] planetas, int ronda) {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      RESUMEN  RONDA  " + ronda + "                   ║");
        System.out.println("  ╠═══════════════════╦══════╦═══════════════╣");
        System.out.println("  ║ Equipo            ║ Vida ║ Tipo          ║");
        System.out.println("  ╠═══════════════════╬══════╬═══════════════╣");

        int vivos = 0;
        for (Planeta p : planetas) {
            String estadoStr;
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

        return vivos;
    }

    // ─────────────────────────────────────────────
    //  Secciones informativas
    // ─────────────────────────────────────────────

    /**
     * Imprime las reglas del juego.
     */
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

    /**
     * Imprime información sobre el juego y sus autores.
     */
    public static void informacion() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║               INFORMACION               ║");
        System.out.println("  ╠══════════════════════════════════════════╣");
        System.out.println("  ║  Version : 2.0                          ║");
        System.out.println("  ║  Autores : —                            ║");
        System.out.println("  ║  Contacto: —                            ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
    }
}
