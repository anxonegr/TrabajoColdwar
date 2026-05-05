package trabajoColdwar;

/**
 * Implementación concreta de los planetas estándar: normal, rojo, azul y verde.
 * <p>
 * Gestiona su propio campo {@code tipo} de forma privada e implementa
 * {@link #getTipo()} y {@link #getNombreTipo()}, requeridos por la superclase
 * abstracta {@link Planeta}. El resto del comportamiento (combate, reposición
 * de misiles) se hereda sin cambios.
 * </p>
 *
 * <p>Tipos admitidos:</p>
 * <ul>
 *   <li>{@code "normal"} → sin bonificaciones de tipo</li>
 *   <li>{@code "rojo"}   → ×2 al verde, ×0.5 al azul</li>
 *   <li>{@code "azul"}   → ×2 al rojo,  ×0.5 al verde</li>
 *   <li>{@code "verde"}  → ×2 al azul,  ×0.5 al rojo</li>
 * </ul>
 *
 * @see Planeta
 * @version 3.1
 */
public class PlanetaNormal extends Planeta {

    /**
     * Tipo interno del planeta en minúsculas.
     * Puede ser {@code "normal"}, {@code "rojo"}, {@code "azul"} o {@code "verde"}.
     */
    private TipoPlaneta tipo;

    /**
     * Crea un planeta estándar con los valores base heredados de {@link Planeta}:
     * 200 vidas y 50 misiles por ronda.
     *
     * @param nombre nombre identificador del equipo
     * @param tipo   tipo del planeta; debe ser {@code "normal"}, {@code "rojo"},
     *               {@code "azul"} o {@code "verde"}
     */
    public PlanetaNormal(String nombre, String tipo) {
        super(nombre);
        this.tipo = new TipoPlaneta(tipo);
    }

    /**
     * Devuelve el tipo interno del planeta en minúsculas.
     *
     * @return {@code "normal"}, {@code "rojo"}, {@code "azul"} o {@code "verde"}
     */
    @Override
    public String getTipo() { return tipo.getTipo(); }

    /**
     * Devuelve el nombre legible del tipo de planeta.
     *
     * @return {@code "Rojo"}, {@code "Azul"}, {@code "Verde"} o {@code "Normal"}
     *         según el tipo asignado en el constructor
     */
    @Override
    public String getNombreTipo() {
        return tipo.getNombreTipo();
    }
}
