package trabajoColdWar_Utils;

public class Mensaje {
	
	public static void menu() {
        System.out.println();
        System.out.println("  ╔══════════════════════════╗");
        System.out.println("  ║      ☢   COLDWAR   ☢    ║");
        System.out.println("  ╠══════════════════════════╣");
        System.out.println("  ║  1. Jugar               ║");
        System.out.println("  ║  2. Reglas              ║");
        System.out.println("  ║  3. Información         ║");
        System.out.println("  ║  4. Apartado extra      ║");
        System.out.println("  ║  5. Historial ganadores ║");
        System.out.println("  ║  0. Salir               ║");
        System.out.println("  ╚══════════════════════════╝");
        System.out.print("  Seleccione una opción: ");
    }
	
	public static void menuTipo() {
		System.out.println("  Elige el tipo de planeta:");
        System.out.println("    1. Normal          (200 vidas, 50 misiles/ronda)");
        System.out.println("    2. Rojo            (x2 al verde, /2 al azul)");
        System.out.println("    3. Azul            (x2 al rojo,  /2 al verde)");
        System.out.println("    4. Verde           (x2 al azul,  /2 al rojo)");
        System.out.println("    5. Gigante gaseoso (400 vidas, 10 misiles iniciales)");
        System.out.println("    6. Planeta enano   (100 vidas, 50% de esquive)");
	}
	
	public static void informacion() {
		System.out.println();
		System.out.println("  ╔══════════════════════════════════════════╗");
		System.out.println("  ║               INFORMACION                ║");
		System.out.println("  ╠══════════════════════════════════════════╣");
		System.out.println("  ║    Version : 3.0");
		System.out.println("  ║    Autores : Miguel Riveiro");
		System.out.println("  ║    Autores : Rubén González");
		System.out.println("  ║    Autores : Anxo Negreira");
		System.out.println("  ║    Autores : Xoel Mauri");
		System.out.println("  ║    Autores : Bieito Martínez");
		System.out.println("  ║    Autores : Enrique Martín");
		System.out.println("  ║    Autores : Lukas Ouro");
		System.out.println("  ║    Contacto: xoelmauri@icloud.com");
		System.out.println("  ╚══════════════════════════════════════════╝");

	}
	
}
