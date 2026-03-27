package trabajoColdwar;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TrabajoColdwar {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        boolean salir = false;

        do {
            opcion = menu(sc);
            switch (opcion) {
                case 1: jugar(); break;
                case 2: reglas(); break;
                case 3: informacion(); break;
                case 4: extra(); break;
                case 0: salir = true; break;
                default: System.out.println("Opción inválida.");
            }
        } while (!salir);

        sc.close();
    }

    // FUNCIÓN PARA LEER NÚMEROS (Evita que el programa se rompa si metes letras)
    public static int leerNumero(Scanner sc) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                numero = sc.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("No es un número. Por favor, introduzca un número");
                sc.next(); // limpiar entrada incorrecta
            }
        }
        return numero;
    }

    // Imprime el menú. Recibe el Scanner. Devuelve la opción seleccionada
    public static int menu(Scanner sc) {
        // Mostrar el menú
        System.out.println("\n----   COLDWAR   ----");
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1º JUGAR");
        System.out.println("2º REGLAS DEL JUEGO");
        System.out.println("3º INFORMACIÓN");
        System.out.println("4º APARTADO ABIERTO");
        System.out.println("0º SALIR");

        // Devuelve la opción seleccionada
        System.out.print("\nSeleccione una opción: ");
        return leerNumero(sc); 
    }

    public static void jugar() {
        // NOTA: Para que funcione hay que crear la clase 'Planeta'.
        // He comentado el bloque de combate para que el menú funcione mientras se termina.
        
        /*
        Planeta[] planetas = { }; // AQUI HAY QUE INICIALIZAR LOS PLANETAS
        int ronda = 1;
        boolean juegoActivo = true;

        while (juegoActivo) {
            System.out.println("RONDA " + ronda);

            // Turno de cada planeta
            for (int i = 0; i < planetas.length; i++) {
                planetas[i].combate(planetas); 
            }

            // Contar vivos
            int vivos = resumenRonda(planetas, ronda);

            if (vivos <= 1) {
                juegoActivo = false;
            }
            ronda++;
        }

        // Al salir del bucle decidir ganador o empate
        int vivosFinal = 0;
        Planeta ganador = null;

        for (int i = 0; i < planetas.length; i++) {
            if (planetas[i].getVidas() > 0) {
                vivosFinal++;
                ganador = planetas[i];
            }
        }

        if (vivosFinal == 1) {
            System.out.println("El ganador es: " + ganador.getNombre());
        } else if (vivosFinal == 0) {
            System.out.println("Empate: todos los planetas han sido destruidos");
        }
        */
        
    }

    public static int resumenRonda(Object[] planetas, int ronda) {
        int vivos = 0;
        System.out.println("\n--- RESUMEN RONDA " + ronda + " ---");
        
//        for (int i = 0; i < planetas.length; i++) {
//            // Aquí imprimes el nombre y las vidas de cada uno
//            System.out.println(planetas[i].getNombre() + " -> Vidas: " + planetas[i].getVidas());
//
//            // Y aquí compruebas si sigue vivo (si tiene más de 0 vidas)
//            if (planetas[i].getVidas() > 0) {
//                vivos++; // Si está vivo, lo sumas al contador
//            }
//        }
        
        System.out.println("Equipos vivos: " + vivos);
        System.out.println("-----------------------------");
        return vivos;
    }

    // Imprime las reglas del juego
    public static void reglas() {
        System.out.println("\nReglas del juego:");
        System.out.println("Cada equipo empieza con 200 vidas.");
        System.out.println("Cada ronda tiene 50 misiles al comienzo de cada ronda.");
        System.out.println("Los jugadores atacan a otros equipos.");
        System.out.println("Cuando un equipo llega a 0 vidas queda eliminado.");
    }

    // Imprime la información del desarrollo.
    public static void informacion() {
        System.out.println("\nInformación:");
        System.out.println("Versión: 1.0");
        System.out.println("Autores: ");
        System.out.println("Contacto: ");
    }

    public static void extra() {
        System.out.println("\nApartado extra abierto.");
    }
}
