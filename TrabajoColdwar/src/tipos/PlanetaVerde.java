package tipos;

import planeta.Planeta;

/**
 * Representa un planeta verde.
 * Ventaja x2 contra Azul, desventaja x0.5 contra Rojo.
 *
 * @version 2.0
 */
public class PlanetaVerde extends Planeta {

    public PlanetaVerde(String nombre) {
        super(nombre);
    }

    @Override
    protected double calcularMultiplicador(Planeta defensor) {
        if (defensor instanceof PlanetaAzul) return 2.0;
        if (defensor instanceof PlanetaRojo) return 0.5;
        return 1.0;
    }
}