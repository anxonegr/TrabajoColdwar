package trabajoColdwar;

import java.util.Random;

/**
 * Representa un planeta (equipo) participante en la partida de ColdWar.
 * <p>
 * El atributo {@code tipo} es un {@link String} con uno de los siguientes
 * valores posibles:
 * </p>
 * <ul>
 *   <li>{@code "normal"}   – sin ventajas ni desventajas.</li>
 *   <li>{@code "rojo"}     – x2 al verde, /2 al azul.</li>
 *   <li>{@code "azul"}     – x2 al rojo,  /2 al verde.</li>
 *   <li>{@code "verde"}    – x2 al azul,  /2 al rojo.</li>
 *   <li>{@code "gaseoso"}  – doble de vida (400), empieza con 10 misiles y
 *                            gana 10 más cada ronda.</li>
 *   <li>{@code "enano"}    – mitad de vida (100), 50 % de probabilidad de
 *                            esquivar cada ataque.</li>
 * </ul>
 *
 * @version 2.0
 */
public class Planeta {

    // ─────────────────────────────────────────────
    //  Atributos de instancia
    // ─────────────────────────────────────────────

    /** Vidas actuales del planeta. Nunca bajan de 0. */
    private int vidas;

    /** Nombre identificativo del equipo. */
    private String nombre;

    /** Misiles de que dispone el planeta en la ronda actual. */
    private int misilesRonda;

    /**
     * Tipo de planeta: "normal", "rojo", "azul", "verde", "gaseoso" o "enano".
     */
    private String tipo;

    /** Número total de equipos creados desde el inicio de la sesión. */
    private static int numeroEquipos = 0;

    /** Generador de números aleatorios compartido para el esquive del enano. */
    private static final Random RANDOM = new Random();
    
    /** Indica si el planeta ha sido eliminado en la ronda que transcurre para poder darle un último turno. */
    private boolean eliminadoEstaRonda = false;
    
    public boolean isEliminadoEstaRonda() { return eliminadoEstaRonda; }
    public void setEliminadoEstaRonda(boolean b) { eliminadoEstaRonda = b; }

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Crea un nuevo planeta con el nombre y tipo indicados.
     * <ul>
     *   <li>normal / rojo / azul / verde: 200 vidas, 50 misiles.</li>
     *   <li>gaseoso:                      400 vidas, 10 misiles.</li>
     *   <li>enano:                        100 vidas, 50 misiles.</li>
     * </ul>
     * Incrementa el contador estático de equipos.
     *
     * @param nombre nombre identificativo del equipo
     * @param tipo   tipo de planeta ("normal", "rojo", "azul", "verde", "gaseoso" o "enano")
     */
    public Planeta(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo   = tipo;

        if (tipo.equals("gaseoso")) {
            this.vidas        = 400;
            this.misilesRonda = 10;
        } else if (tipo.equals("enano")) {
            this.vidas        = 100;
            this.misilesRonda = 50;
        } else {
            this.vidas        = 200;
            this.misilesRonda = 50;
        }

        numeroEquipos++;
    }

    // ─────────────────────────────────────────────
    //  Getters y setters
    // ─────────────────────────────────────────────

    /**
     * Devuelve las vidas actuales del planeta.
     *
     * @return vidas actuales (siempre &ge; 0)
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Establece las vidas del planeta.
     * El valor se capea a 0 por abajo para evitar vidas negativas.
     *
     * @param vidas nuevo valor de vidas
     */
    public void setVidas(int vidas) {
        this.vidas = Math.max(0, vidas);
    }

    /**
     * Devuelve el nombre del planeta.
     *
     * @return nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve los misiles disponibles en la ronda actual.
     *
     * @return misiles de ronda actuales (siempre &ge; 0)
     */
    public int getMisilesRonda() {
        return misilesRonda;
    }

    /**
     * Establece la cantidad de misiles para la ronda actual.
     * El valor se capea a 0 por abajo.
     *
     * @param misilesRonda nuevo valor de misiles
     */
    public void setMisilesRonda(int misilesRonda) {
        this.misilesRonda = Math.max(0, misilesRonda);
    }

    /**
     * Devuelve el tipo de planeta en minúsculas.
     *
     * @return tipo del planeta ("normal", "rojo", "azul", "verde", "gaseoso" o "enano")
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Devuelve el nombre legible del tipo de planeta (con mayúscula inicial).
     *
     * @return cadena con el nombre del tipo
     */
    public String getNombreTipo() {
        if (tipo.equals("rojo"))    return "Rojo";
        if (tipo.equals("azul"))    return "Azul";
        if (tipo.equals("verde"))   return "Verde";
        if (tipo.equals("gaseoso")) return "Gigante Gaseoso";
        if (tipo.equals("enano"))   return "Planeta Enano";
        return "Normal";
    }

    /**
     * Devuelve el número total de equipos creados durante la sesión.
     *
     * @return contador estático de equipos
     */
    public static int getNumeroEquipos() {
        return numeroEquipos;
    }

    // ─────────────────────────────────────────────
    //  Lógica de ronda
    // ─────────────────────────────────────────────

    /**
     * Repone los misiles al inicio de una nueva ronda.
     * <ul>
     *   <li>gaseoso: 10 * numRonda misiles (crece cada ronda).</li>
     *   <li>Resto:   50 misiles fijos.</li>
     * </ul>
     * No hace nada si el planeta ya está eliminado.
     *
     * @param numRonda número de la ronda que comienza (empezando en 1)
     */
    public void reponerMisiles(int numRonda) {
        if (vidas < 0) {
            return;
        }
        if (tipo.equals("gaseoso")) {
            setMisilesRonda(10 * numRonda);
        } else {
            setMisilesRonda(50);
        }
    }

    // ─────────────────────────────────────────────
    //  Lógica de combate
    // ─────────────────────────────────────────────

    /**
     * Procesa un ataque recibido de otro planeta.
     * <p>
     * Aplica las validaciones en este orden:
     * </p>
     * <ol>
     *   <li>El atacante no puede gastar más misiles de los que posee.</li>
     *   <li>Un planeta no puede atacarse a sí mismo.</li>
     *   <li>No se puede atacar a un planeta ya eliminado.</li>
     * </ol>
     * <p>
     * Si pasa todas las restricciones, se calcula el multiplicador de daño
     * según la interacción de tipos y, en el caso del planeta enano, se
     * resuelve el posible esquive (50 %) antes de aplicar el daño.
     * </p>
     *
     * @param misilesAtacar cantidad de misiles empleados en el ataque
     * @param atacante      {@link Planeta} que realiza el ataque
     */
    public void combate(int misilesAtacar, Planeta atacante) {

        if (misilesAtacar > atacante.getMisilesRonda()) {
            System.out.println("  [!] No tienes suficientes misiles para atacar.");
            return;
        }

        if (nombre.equals(atacante.getNombre())) {
            System.out.println("  [!] No puedes atacarte a ti mismo.");
            return;
        }

        if (vidas <= 0) {
            System.out.println("  [!] El planeta " + nombre + " ya ha sido destruido.");
            return;
        }

        // Calcular multiplicador por interaccion de tipos
        double multiplicador = calcularMultiplicador(atacante.getTipo(), this.tipo);

        // Esquive del planeta enano (50 %)
        if (tipo.equals("enano") && RANDOM.nextBoolean()) {
            atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);
            System.out.println("  >> " + atacante.getNombre() + " ataca a " + nombre
                    + " con " + misilesAtacar + " misiles.");
            System.out.println("  !! " + nombre + " ha ESQUIVADO el ataque! (Vidas: " + vidas + ")");
            return;
        }

        int danio = (int) (misilesAtacar * multiplicador);
        setVidas(vidas - danio);
        atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);

        System.out.println("  >> " + atacante.getNombre() + " (" + atacante.getNombreTipo() + ")"
                + " ataca a " + nombre + " (" + getNombreTipo() + ")"
                + " con " + misilesAtacar + " misiles."
                + " Danio aplicado: " + danio
                + " | Vidas de " + nombre + ": " + vidas);

        if (multiplicador > 1.0) {
            System.out.println("  Ventaja de tipo: danio DOBLE!");
        } else if (multiplicador < 1.0) {
            System.out.println("  Desventaja de tipo: danio a la MITAD.");
        }

        if (vidas == 0) {
            System.out.println("  !! El equipo " + nombre + " ha sido ELIMINADO !!");
            this.eliminadoEstaRonda = true;
        }
    }

    /**
     * Calcula el multiplicador de daño según la interacción de tipos.
     *
     * @param tipoAtacante tipo del planeta que ataca
     * @param tipoDefensor tipo del planeta que recibe el ataque
     * @return 2.0 si hay ventaja, 0.5 si hay desventaja, 1.0 en caso neutro
     */
    private double calcularMultiplicador(String tipoAtacante, String tipoDefensor) {
        if (tipoAtacante.equals("rojo")  && tipoDefensor.equals("verde")) return 2.0;
        if (tipoAtacante.equals("rojo")  && tipoDefensor.equals("azul"))  return 0.5;
        if (tipoAtacante.equals("azul")  && tipoDefensor.equals("rojo"))  return 2.0;
        if (tipoAtacante.equals("azul")  && tipoDefensor.equals("verde")) return 0.5;
        if (tipoAtacante.equals("verde") && tipoDefensor.equals("azul"))  return 2.0;
        if (tipoAtacante.equals("verde") && tipoDefensor.equals("rojo"))  return 0.5;
        return 1.0;
    }
}
