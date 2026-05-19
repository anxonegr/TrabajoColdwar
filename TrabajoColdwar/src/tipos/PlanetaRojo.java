package tipos;

import planeta.Planeta;

/**
 * Representa un planeta rojo.
 * Ventaja x2 contra Verde, desventaja x0.5 contra Azul.
 *
 * @version 2.0
 */
public class PlanetaRojo extends Planeta {

    public PlanetaRojo(String nombre) {
        super(nombre);
    }

    @Override
    protected double calcularMultiplicador(Planeta defensor) {
    	
        if (defensor instanceof PlanetaVerde) return 2.0;
        if (defensor instanceof PlanetaAzul)  return 0.5;
        return 1.0;
        
    }
}