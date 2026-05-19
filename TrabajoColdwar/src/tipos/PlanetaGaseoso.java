package tipos;

import planeta.Planeta;

/**
 * Representa un gigante gaseoso.
 * 400 vidas, misiles = 10 × numRonda.
 *
 * @version 2.0
 */
public class PlanetaGaseoso extends Planeta {

    public PlanetaGaseoso(String nombre) {
        super(nombre);
        vidas *= 2;
        misilesRonda = 10;
        
        
    }

    @Override
    /**
     * Reponemos misiles del planeta gigante que se incrementan 10 cada ronda
     * */
    public void reponerMisiles(int numRonda) {
        
         misilesRonda = 10 * numRonda;
         
    }
    
    @Override
    protected double calcularMultiplicador(Planeta defensor) {
    	
        return 1.0;
        
    }
}