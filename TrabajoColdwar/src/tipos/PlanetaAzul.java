package tipos;

import planeta.Planeta;

/**
 * Representa un planeta azul.
 * Ventaja x2 contra Rojo, desventaja x0.5 contra Verde.
 *
 * @version 2.0
 */
public class PlanetaAzul extends Planeta {

    public PlanetaAzul(String nombre) {
        super(nombre);
    }

    @Override
    protected double calcularMultiplicador(Planeta defensor) {
    	
    	//funciona de las dos formas
//        if (defensor instanceof PlanetaRojo)  return 2.0;
//        if (defensor instanceof PlanetaVerde) return 0.5;
//        return 1.0;
       
    	//si es rojo doble misiles, si es verde mitad, de lo contrario 1
        return defensor instanceof PlanetaRojo ? 2.0 : defensor instanceof PlanetaVerde ? 0.5 : 1.0;
    }
}