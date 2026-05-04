package trabajoColdWar_Utils;

public class Validaciones {
    public static boolean validarIdentificador(String id) {
        return id.matches("^[0-9]{4}[A-Z]{3}$");
    }
}
