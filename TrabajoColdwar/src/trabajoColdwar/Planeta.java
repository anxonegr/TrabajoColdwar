package trabajoColdwar;

public class Planeta {
	
	private int vidas;
    private String nombre;
    private int misilesRonda;
    private static int numeroEquipos = 0;


    //constructor

    public Planeta(String nombre) {
		
        this.nombre = nombre;
        this.vidas = 200;
        this.misilesRonda = 50;
        numeroEquipos++;

    }


    //getters y setters

    public int getVidas() {
        return vidas;

    }


    public void setVidas(int vidas) {
        this.vidas = vidas;

    }


    public String getNombre() {
        return nombre;

    }


    public int getMisilesRonda() {
        return misilesRonda;

    }


    public void setMisilesRonda(int misilesRonda) {
        this.misilesRonda = misilesRonda;

    }


    public static int getNumeroEquipos() {
        return numeroEquipos;

    }

}

