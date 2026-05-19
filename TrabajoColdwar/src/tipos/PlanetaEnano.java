package tipos;

import planeta.Planeta;

/**
 * Representa un planeta enano.
 * 100 vidas, 50 misiles, esquiva cada misil con un 50% de probabilidad.
 *
 * @version 2.0
 */
public class PlanetaEnano extends Planeta {

    private int misilesEsquivados;

    public PlanetaEnano(String nombre) {
        super(nombre);
        vidas = 100;
        misilesEsquivados = 0;
    }

    public int getMisilesEsquivados() {
        return misilesEsquivados;
    }

    
    
    @Override
    public String combate(int misilesAtacar, Planeta atacante) {

        int esquivados = 0;

        for (int i = 0; i < misilesAtacar; i++) {
        	
            if (RANDOM.nextBoolean()) esquivados++;
        }

        int danioRecibido = misilesAtacar - esquivados;

        atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);
        misilesEsquivados += esquivados;
        setVidas(getVidas() - danioRecibido);

        return getNombre()
                + " ha esquivado " + esquivados
                + " misiles de " + misilesAtacar
                + ". Daño recibido: " + danioRecibido
                + ". Vidas restantes: " + getVidas() + ".\n";
    }
    
    @Override
    protected double calcularMultiplicador(Planeta defensor) {
    	
        return 1.0;
        
    }
}