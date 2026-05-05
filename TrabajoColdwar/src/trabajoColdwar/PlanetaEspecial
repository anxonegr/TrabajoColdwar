package trabajoColdwar;

/**
 * Implementación concreta de los planetas con mecánicas especiales:
 * <b>gigante gaseoso</b> y <b>planeta enano</b>.
 * <p>
 * Gestiona su propio campo {@code tipo} de forma privada e implementa
 * {@link #getTipo()} y {@link #getNombreTipo()}, requeridos por la superclase
 * abstracta {@link Planeta}. Además sobreescribe {@link #reponerMisiles(int)}
 * y {@link #combate(int, Planeta)} con comportamiento diferenciado por tipo.
 * </p>
 *
 * <ul>
 *   <li>
 *     <b>Gigante gaseoso</b> ({@code "gaseoso"}): 400 vidas, 10 misiles
 *     iniciales; la reserva crece linealmente ({@code 10 × numRonda}) cada ronda.
 *   </li>
 *   <li>
 *     <b>Planeta enano</b> ({@code "enano"}): 100 vidas, 50 misiles;
 *     tiene un 50 % de probabilidad de esquivar cualquier ataque recibido,
 *     consumiendo igualmente los misiles del atacante.
 *   </li>
 * </ul>
 *
 * @see Planeta
 * @version 3.1
 */
public class PlanetaEspecial extends Planeta {

    /**
     * Tipo interno del planeta en minúsculas.
     * Puede ser {@code "gaseoso"} o {@code "enano"}.
     */
    private TipoPlaneta tipo;

    /**
     * Crea un planeta especial ajustando las estadísticas iniciales según el tipo.
     * <ul>
     *   <li>{@code "gaseoso"}: vidas = 400, misiles iniciales = 10.</li>
     *   <li>{@code "enano"}:   vidas = 100, misiles iniciales = 50.</li>
     * </ul>
     *
     * @param nombre nombre identificador del equipo
     * @param tipo   tipo especial del planeta; debe ser {@code "gaseoso"} o {@code "enano"}
     */
    public PlanetaEspecial(String nombre, String tipo) {
        super(nombre);
        this.tipo = new TipoPlaneta(tipo);
        setVidas(tipo.equals("gaseoso") ? 400 : 100);
        setMisilesRonda(tipo.equals("gaseoso") ? 10 : 50);
    }

    /**
     * Devuelve el tipo interno del planeta en minúsculas.
     *
     * @return {@code "gaseoso"} o {@code "enano"}
     */
    @Override
    public String getTipo() { return tipo.getTipo(); }

    /**
     * Devuelve el nombre legible del tipo de planeta.
     *
     * @return {@code "Gigante Gaseoso"}, {@code "Planeta Enano"} o
     *         {@code "Planeta Desconocido"} si el tipo no es reconocido
     */
    @Override
    public String getNombreTipo() {
        return tipo.getNombreTipo();
    }

    /**
     * Repone los misiles al inicio de cada ronda según las reglas del tipo especial.
     * <ul>
     *   <li><b>Gaseoso</b>: recibe {@code 10 × numRonda} misiles, creciendo cada ronda.</li>
     *   <li><b>Enano</b>: sigue la reposición estándar de 50 misiles (delegada a la superclase).</li>
     * </ul>
     * Si el planeta está eliminado (vidas = 0) no se realiza ninguna acción.
     *
     * @param numRonda número de la ronda actual (1-based)
     */
    @Override
    public void reponerMisiles(int numRonda) {
        if (getVidas() <= 0) return;
        if (tipo.equals("gaseoso")) setMisilesRonda(10 * numRonda);
        else super.reponerMisiles(numRonda);
    }

    /**
     * Ejecuta un ataque sobre este planeta, aplicando la mecánica de esquive
     * exclusiva del planeta enano.
     * <p>
     * Si el tipo es {@code "enano"}, hay un 50 % de probabilidad de que el ataque
     * sea esquivado completamente: el daño no se aplica, pero el atacante sí
     * pierde los misiles utilizados. Si el ataque no es esquivado, o si el tipo
     * es {@code "gaseoso"}, el combate se resuelve con la lógica estándar de
     * {@link Planeta#combate(int, Planeta)}.
     * </p>
     *
     * @param misilesAtacar número de misiles que el atacante desea usar
     * @param atacante      planeta que lanza el ataque
     * @return cadena con el resumen del ataque para el log de ronda;
     *         incluye el mensaje de esquive si este se produce
     */
    @Override
    public String combate(int misilesAtacar, Planeta atacante) {
        if (tipo.equals("enano") && RANDOM.nextBoolean()) {
            atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);
            String msg = "  !! " + getNombre() + " ha ESQUIVADO el ataque!";
            System.out.println(msg + " (Vidas: " + getVidas() + ")");
            return msg + "\n";
        }
        return super.combate(misilesAtacar, atacante);
    }
}
