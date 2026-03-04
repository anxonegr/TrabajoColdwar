package trabajoColdwar;
import java.util.Scanner;

public class TrabajoColdwar {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int opcion = 0;
		
		do {
			
			opcion = menu(sc);
		
		}while (opcion != 0);
		sc.close();
	}
	
	//Imprime el menú. Recibe el Scanner. Devuelve la opción seleccionada
	public static int menu(Scanner sc) {

		// Mostrar el menú
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
}



