package trabajoColdwar;

import java.util.Random;

/**
 * Clase abstracta que representa un planeta-equipo en el juego <b>ColdWar</b>.
 * <p>
 * Define el estado base (vidas, misiles) y la lógica de combate común
 * a todos los planetas. Las subclases son responsables de declarar su propio
 * tipo y de implementar {@link #getTipo()} y {@link #getNombreTipo()}.
 * También pueden sobreescribir {@link #reponerMisiles(int)} y
 * {@link #combate(int, Planeta)} para añadir comportamiento especial.
 * </p>
 *
 * <p>Tabla de ventajas de tipo:</p>
 * <pre>
 *   Atacante → Defensor  | Multiplicador
 *   ─────────────────────┼──────────────
 *   rojo     → verde     |  ×2.0
 *   rojo     → azul      |  ×0.5
 *   azul     → rojo      |  ×2.0
 *   azul     → verde     |  ×0.5
 *   verde    → azul      |  ×2.0
 *   verde    → rojo      |  ×0.5
 *   mismo / normal       |  ×1.0
 * </pre>
 *
 * @version 3.1
 */
public abstract class Planeta {

    /** Puntos de vida actuales del planeta. Nunca baja de 0. */
    private int vidas;

    /** Nombre identificador del equipo. */
    private String nombre;

    /** Misiles disponibles para la ronda en curso. Nunca baja de 0. */
    private int misilesRonda;
    
    private String identificador;
    
    /** Contador global de planetas creados durante la sesión. */
    private static int numeroEquipos = 0;

    /**
     * Generador de números aleatorios compartido por todas las subclases.
     * Se declara {@code protected} para que {@link PlanetaEspecial} lo use
     * en la lógica de esquive.
     */
    protected static final Random RANDOM = new Random();

    /**
     * Indica si el planeta fue eliminado <em>durante la ronda actual</em>.
     * Se usa para que el planeta pueda consumir su turno de ataque aunque
     * haya sido destruido por un rival en la misma ronda, y se resetea al
     * finalizar dicho turno.
     */
    private boolean eliminadoEstaRonda = false;

    // ── getters / setters de eliminadoEstaRonda ──────────────────

    /**
     * Devuelve {@code true} si el planeta fue eliminado en la ronda actual.
     *
     * @return {@code true} si está marcado como eliminado esta ronda
     */
    public boolean isEliminadoEstaRonda()        { return eliminadoEstaRonda; }

    /**
     * Establece la marca de eliminado-esta-ronda.
     *
     * @param b {@code true} para marcar al planeta como eliminado en la ronda actual
     */
    public void setEliminadoEstaRonda(boolean b) { eliminadoEstaRonda = b; }

    // ── Constructor ──────────────────────────────────────────────

    /**
     * Crea un nuevo planeta con los valores base: 200 vidas y 50 misiles por ronda.
     * Incrementa el contador estático {@link #numeroEquipos}.
     * <p>
     * El tipo no se recibe aquí; cada subclase lo gestiona internamente.
     * </p>
     *
     * @param nombre nombre identificador del equipo; no puede estar vacío
     */
    public Planeta(String nombre) {
        this.nombre       = nombre;
        this.vidas        = 200;
        this.misilesRonda = 50;
        numeroEquipos++;
    }

    // ── Getters / setters ────────────────────────────────────────

    /**
     * Devuelve los puntos de vida actuales del planeta.
     *
     * @return vidas actuales (siempre ≥ 0)
     */
    public int getVidas() { return vidas; }

    /**
     * Establece las vidas del planeta. Si el valor es negativo se fija a 0.
     *
     * @param vidas nuevos puntos de vida
     */
    public void setVidas(int vidas) { this.vidas = Math.max(0, vidas); }

    /**
     * Devuelve el nombre identificador del equipo.
     *
     * @return nombre del planeta
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve los misiles disponibles para la ronda en curso.
     *
     * @return misiles restantes (siempre ≥ 0)
     */
    public int getMisilesRonda() { return misilesRonda; }

    /**
     * Establece los misiles disponibles para la ronda en curso.
     * Si el valor es negativo se fija a 0.
     *
     * @param m nueva cantidad de misiles
     */
    public void setMisilesRonda(int m) { this.misilesRonda = Math.max(0, m); }

    /**
     * Devuelve el número total de planetas creados desde el inicio de la sesión.
     *
     * @return número de equipos registrados
     */
    public static int getNumeroEquipos() { return numeroEquipos; }

    // ── Métodos abstractos ───────────────────────────────────────

    /**
     * Devuelve el tipo interno del planeta en minúsculas
     * (p. ej. {@code "rojo"}, {@code "gaseoso"}).
     * <p>
     * Cada subclase concreta <b>debe</b> implementar este método gestionando
     * su propio campo de tipo.
     * </p>
     *
     * @return tipo del planeta en minúsculas, nunca {@code null}
     */
    public abstract String getTipo();

    /**
     * Devuelve el nombre legible del tipo de planeta para mostrarlo en pantalla
     * (p. ej. {@code "Rojo"}, {@code "Gigante Gaseoso"}).
     * <p>
     * Cada subclase concreta <b>debe</b> implementar este método.
     * </p>
     *
     * @return cadena con el nombre del tipo, nunca {@code null}
     */
    public abstract String getNombreTipo();
    
    public String getIdentificador() { 
        return identificador; 
    }

    public void setIdentificador(String identificador) { 
        this.identificador = identificador; 
    }

    // ── Lógica de ronda ──────────────────────────────────────────

    /**
     * Repone los misiles del planeta al inicio de cada ronda.
     * La implementación base restaura el valor a 50, siempre que el planeta
     * siga vivo. Las subclases pueden sobreescribir este método para aplicar
     * reglas de reposición distintas (p. ej. el gigante gaseoso).
     *
     * @param numRonda número de la ronda actual (1-based); ignorado en la
     *                 implementación base, pero disponible para las subclases
     */
    public void reponerMisiles(int numRonda) {
        if (vidas <= 0) return;
        setMisilesRonda(50);
    }

    // ── Lógica de combate ────────────────────────────────────────

    /**
     * Ejecuta un ataque de {@code atacante} sobre este planeta.
     * <p>
     * El daño base es igual a los misiles usados, multiplicado por el
     * factor de ventaja de tipo obtenido de {@link #calcularMultiplicador}.
     * Los misiles consumidos se descuentan del atacante.
     * Si el planeta llega a 0 vidas, se marca como eliminado esta ronda.
     * </p>
     *
     * <p>Condiciones de rechazo (no se realiza el ataque):</p>
     * <ul>
     *   <li>El atacante no tiene suficientes misiles.</li>
     *   <li>El planeta objetivo es el propio atacante.</li>
     *   <li>El planeta objetivo ya ha sido destruido.</li>
     * </ul>
     *
     * @param misilesAtacar número de misiles que el atacante desea usar; debe ser
     *                      mayor que 0 y menor o igual a {@link #getMisilesRonda()}
     *                      del atacante
     * @param atacante      planeta que lanza el ataque
     * @return cadena con el resumen del ataque para el log de ronda;
     *         cadena vacía si el ataque fue rechazado
     */
    public String combate(int misilesAtacar, Planeta atacante) {

        if (misilesAtacar > atacante.getMisilesRonda()) {
            System.out.println("  [!] No tienes suficientes misiles para atacar.");
            return "";
        }
        if (nombre.equals(atacante.getNombre())) {
            System.out.println("  [!] No puedes atacarte a ti mismo.");
            return "";
        }
        if (vidas <= 0) {
            System.out.println("  [!] El planeta " + nombre + " ya ha sido destruido.");
            return "";
        }

        double multiplicador = calcularMultiplicador(atacante.getTipo(), this.getTipo());
        int    danio         = (int)(misilesAtacar * multiplicador);
        setVidas(vidas - danio);
        atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);

        String msg = "  >> " + atacante.getNombre() + " (" + atacante.getNombreTipo() + ")"
                + " ataca a " + nombre + " (" + getNombreTipo() + ")"
                + " con " + misilesAtacar + " misiles."
                + " Danio aplicado: " + danio
                + " | Vidas de " + nombre + ": " + vidas;

        if (multiplicador > 1.0) System.out.println("  Ventaja de tipo: danio DOBLE!");
        else if (multiplicador < 1.0) System.out.println("  Desventaja de tipo: danio a la MITAD.");

        if (vidas == 0) {
            String eliminado = "  !! El equipo " + nombre + " ha sido ELIMINADO !!";
            System.out.println(eliminado);
            this.eliminadoEstaRonda = true;
            return msg + "\n" + eliminado + "\n";
        }
        return msg + "\n";
    }

    /**
     * Calcula el multiplicador de daño según la relación de tipos entre atacante
     * y defensor, siguiendo el triángulo de ventajas rojo–azul–verde.
     *
     * @param tipoAtacante tipo del planeta atacante en minúsculas
     * @param tipoDefensor tipo del planeta defensor en minúsculas
     * @return {@code 2.0} si hay ventaja, {@code 0.5} si hay desventaja,
     *         {@code 1.0} en caso neutro
     */
    protected double calcularMultiplicador(String tipoAtacante, String tipoDefensor) {
        if (tipoAtacante.equals("rojo")  && tipoDefensor.equals("verde")) return 2.0;
        if (tipoAtacante.equals("rojo")  && tipoDefensor.equals("azul"))  return 0.5;
        if (tipoAtacante.equals("azul")  && tipoDefensor.equals("rojo"))  return 2.0;
        if (tipoAtacante.equals("azul")  && tipoDefensor.equals("verde")) return 0.5;
        if (tipoAtacante.equals("verde") && tipoDefensor.equals("azul"))  return 2.0;
        if (tipoAtacante.equals("verde") && tipoDefensor.equals("rojo"))  return 0.5;
        return 1.0;
    }
}
