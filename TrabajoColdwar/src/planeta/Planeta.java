package planeta;

import java.util.Random;
import interfacePlaneta.InterfacePlaneta;
import tipos.PlanetaAzul;
import tipos.PlanetaEnano;
import tipos.PlanetaGaseoso;
import tipos.PlanetaRojo;
import tipos.PlanetaVerde;

/**
 * Clase abstracta que representa un planeta dentro del juego.
 * Define los atributos básicos, gestión de recursos y lógica de combate.
 * 
 * @author TuNombre
 * @version 5.0
 */
public abstract class Planeta implements InterfacePlaneta {

    /** Cantidad actual de puntos de vida del planeta. */
    protected int vidas;
    
    /** Nombre identificativo del planeta. */
    protected String nombre;
    
    /** Cantidad de misiles disponibles para usar en la ronda actual. */
    protected int misilesRonda;
    
    /** Código o hash alfanumérico único del planeta. */
    protected String identificador;
    
    /** Indica si el planeta ha quedado fuera de juego durante la ronda activa. */
    protected boolean eliminadoEstaRonda = false;

    /** Generador de números aleatorios para lógicas derivadas. */
    protected static final Random RANDOM = new Random();
    
    /**
     * Constructor principal de la clase Planeta.
     * Inicializa las vidas a 200 y los misiles de la ronda a 50 de forma predeterminada.
     *
     * @param nombre El nombre que se le asignará al planeta.
     */
    public Planeta(String nombre) {
        this.nombre = nombre;
        vidas = 200;
        misilesRonda = 50;
    }

    /**
     * Devuelve el nombre legible del tipo de un planeta usando comprobación de instancias.
     *
     * @param p El planeta del cual se desea identificar el tipo.
     * @return Una cadena de texto con el nombre del tipo de planeta ("Rojo", "Azul", etc.).
     */
    public static String nombreTipo(Planeta p) {
        if (p instanceof PlanetaRojo)    return "Rojo";
        if (p instanceof PlanetaAzul)    return "Azul";
        if (p instanceof PlanetaVerde)   return "Verde";
        if (p instanceof PlanetaGaseoso) return "Gigante Gaseoso";
        if (p instanceof PlanetaEnano)   return "Planeta Enano";
        return "Normal";
    }
    
    /**
     * Obtiene los puntos de vida actuales del planeta.
     *
     * @return Cantidad de vidas restantes.
     */
    public int getVidas() { return vidas; }

    /**
     * Actualiza los puntos de vida del planeta.
     * Asegura que el valor nunca sea inferior a cero de forma negativa.
     *
     * @param vidas Nueva cantidad de puntos de vida.
     */
    public void setVidas(int vidas) { this.vidas = Math.max(0, vidas); }

    /**
     * Obtiene el nombre del planeta.
     *
     * @return El nombre asignado.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la cantidad de misiles disponibles para la ronda actual.
     *
     * @return Número de misiles en reserva.
     */
    public int getMisilesRonda() { return misilesRonda; }

    /**
     * Actualiza la cantidad de misiles disponibles para la ronda.
     * Evita valores negativos mediante un filtrado de mínimos.
     *
     * @param misilesRonda Nueva cantidad de misiles disponibles.
     */
    public void setMisilesRonda(int misilesRonda) {
        this.misilesRonda = Math.max(0, misilesRonda);
    }

    /**
     * Obtiene el identificador único del planeta.
     *
     * @return Cadena de texto con el identificador.
     */
    public String getIdentificador() { return identificador; }

    /**
     * Asigna un identificador único al planeta.
     *
     * @param identificador Código alfanumérico de destino.
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Comprueba si el planeta fue eliminado en el transcurso de la presente ronda.
     *
     * @return Verdadero si fue eliminado en esta ronda, falso en caso contrario.
     */
    public boolean isEliminadoEstaRonda() { return eliminadoEstaRonda; }

    /**
     * Modifica el estado de eliminación del planeta para la ronda actual.
     *
     * @param eliminadoEstaRonda Estado lógico a asignar.
     */
    public void setEliminadoEstaRonda(boolean eliminadoEstaRonda) {
        this.eliminadoEstaRonda = eliminadoEstaRonda;
    }

    /**
     * Restablece la reserva a 50 misiles si el planeta sigue con vida.
     *
     * @param numRonda El número de la ronda que se va a iniciar.
     */
    public void reponerMisiles(int numRonda) {
        if (vidas > 0 || eliminadoEstaRonda) {
            setMisilesRonda(50);
        }
    }

    /**
     * Calcula la bonificación o penalización de daño que ejerce este planeta sobre un objetivo.
     * Debe ser implementado por las subclases según sus ventajas de tipo.
     *
     * @param p El planeta objetivo que recibirá el impacto.
     * @return El factor multiplicador aplicable al ataque.
     */
    protected abstract double calcularMultiplicador(Planeta p);

    /**
     * Procesa la recepción de un ataque enemigo ejecutado por otro planeta.
     * Reduce la vida del planeta defensor y descuenta los proyectiles consumidos por el atacante.
     *
     * @param misilesAtacar Cantidad de proyectiles enviados en el asalto.
     * @param atacante El planeta de procedencia que realiza la acción ofensiva.
     * @return Un informe detallado con los nombres, daños calculados y consecuencias del combate.
     */
    @Override
    public String combate(int misilesAtacar, Planeta atacante) {

        Planeta atacantePlaneta = (Planeta) atacante;

        int danio = (misilesAtacar * (int) atacantePlaneta.calcularMultiplicador(this));

        vidas -= danio;
        
        atacante.setMisilesRonda(atacante.getMisilesRonda() - misilesAtacar);
        
        String mensaje = atacante.getNombre()
                + " (" + Planeta.nombreTipo(atacantePlaneta) + ") ataca a "
                + nombre
                + " (" + Planeta.nombreTipo(this) + ") con "
                + misilesAtacar
                + " misiles. Daño: " + danio;
                

        if (vidas <= 0) {
            eliminadoEstaRonda = true;
            mensaje += "\n¡¡ " + nombre + " ha sido eliminado !!";
        }

        return mensaje + "\n";
    }
    
    /**
     * Ejecuta una maniobra defensiva sacrificando la capacidad de ataque.
     * Convierte la mitad de los misiles acumulados en la ronda en puntos de salud y vacía la reserva.
     *
     * @return Un informe en formato de texto indicando los puntos de curación obtenidos.
     */
    public String defensa() {
    	
    	int curacion = misilesRonda / 2;
    	
    	vidas += curacion;
    	misilesRonda = 0;
        
    	return nombre + " se cura " + curacion + " puntos. \n";
    }
}
