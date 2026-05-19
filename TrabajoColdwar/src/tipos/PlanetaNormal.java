package tipos;

import planeta.Planeta;

/**
 * Representa un planeta de tipo normal.
 * Sin ventajas ni desventajas. 200 vidas, 50 misiles por ronda.
 *
 * @version 2.0
 */
public class PlanetaNormal extends Planeta {

    public PlanetaNormal(String nombre) {
        super(nombre);
    }
    
    @Override
    protected double calcularMultiplicador(Planeta defensor) {
    	
        return 1.0;
        
    }
}