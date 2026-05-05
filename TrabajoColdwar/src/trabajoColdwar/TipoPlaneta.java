package trabajoColdwar;

import interfaz.InterfaceTipoPlaneta;

public class TipoPlaneta implements InterfaceTipoPlaneta {

    private String tipo;

    public TipoPlaneta(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public String getNombreTipo() {
        switch (tipo) {
            case "rojo":    return "Rojo";
            case "azul":    return "Azul";
            case "verde":   return "Verde";
            case "gaseoso": return "Gigante Gaseoso";
            case "enano":   return "Planeta Enano";
            default:        return "Normal";
        }
    }
}
