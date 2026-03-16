package trabajoColdwar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TrabajoColdwar {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		boolean salir = false;
		
		do {
			try {
				opcion = menu(sc);
				
				switch(opcion){
		        case 1: jugar();
		        break;
	
		        case 2: reglas();
		        break;
	
		        case 3: informacion();
		        break;
	
		        case 4: extra();
		        break;
	
		        case 0: salir = true;
		        break;
		        
		        default:
		        	System.out.println("Opción inválida.");
				}
			} catch (InputMismatchException ime) {
				System.out.println("Opción inválida.");
				sc.next();
			}
		
		}while (!salir);
		sc.close();
	}
	
	//Imprime el menú. Recibe el Scanner. Devuelve la opción seleccionada
	public static int menu(Scanner sc) {

		// Mostrar el menú
        System.out.println("\n----   COLDWAR   ----");
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1º JUGAR");
        System.out.println("2º REGLAS DEL JUEGO");
        System.out.println("3º INFORMACIÓN");
        System.out.println("4º APARTADO ABIERTO");
        System.out.println("0º SALIR");
        
        //Devuelve la opción seleccionada
        System.out.print("\nSeleccione una opción: ");
        return sc.nextInt();
        
		
	}
	
	public static void jugar() {
		
	}
	
	//Imprime las reglas del juego
	public static void reglas() {
		
		System.out.println("\nReglas del juego:");
        System.out.println("Cada equipo empieza con 200 vidas.");
        System.out.println("Cada ronda tiene 50 misiles al comienzo de cada ronda.");
        System.out.println("Los jugadores atacan a otros equipos.");
        System.out.println("Cuando un equipo llega a 0 vidas queda eliminado.");
        
	}
	
	//Imprime la información del desarrollo.
	public static void informacion() {
		
		System.out.println("\nInformación:");
        System.out.println("Versión: 1.0");
        System.out.println("Autores: ");
        System.out.println("Contacto: ");
		
	}
	
	public static void extra() {
		
	}
}
