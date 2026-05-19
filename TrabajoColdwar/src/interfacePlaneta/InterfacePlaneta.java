package interfacePlaneta;

import planeta.Planeta;

/**
 * Interfaz que define el contrato completo de un planeta en el juego ColdWar.
 * <p>
 * Toda clase que implemente esta interfaz deberá proporcionar los métodos
 * de acceso, modificación y combate que componen el comportamiento de un planeta.
 * </p>
 *
 * @version 1.0
 */
public interface InterfacePlaneta {

    // ── Getters ──────────────────────────────────────────────────────────────

    /** @return nombre del planeta */
    String getNombre();

    /** @return puntos de vida actuales */
    int getVidas();

    /** @return misiles disponibles en la ronda actual */
    int getMisilesRonda();

    /** @return identificador único del planeta */
    String getIdentificador();

    /** @return {@code true} si el planeta fue eliminado durante la ronda actual */
    boolean isEliminadoEstaRonda();

    // ── Setters ──────────────────────────────────────────────────────────────

    /** @param vidas nuevos puntos de vida (nunca negativos) */
    void setVidas(int vidas);

    /** @param misilesRonda nueva cantidad de misiles (nunca negativos) */
    void setMisilesRonda(int misilesRonda);

    /** @param identificador identificador único del planeta */
    void setIdentificador(String identificador);

    /** @param eliminadoEstaRonda indica si el planeta fue eliminado esta ronda */
    void setEliminadoEstaRonda(boolean eliminadoEstaRonda);

    // ── Comportamiento ────────────────────────────────────────────────────────

    /**
     * Repone los misiles del planeta al inicio de cada ronda.
     *
     * @param numRonda número de la ronda actual
     */
    void reponerMisiles(int numRonda);

    /**
     * Ejecuta un combate en el que este planeta es el defensor.
     *
     * @param misilesAtacar misiles utilizados por el atacante
     * @param atacante      planeta que realiza el ataque
     * @return mensaje descriptivo del resultado del combate
     */
    String combate(int misilesAtacar, Planeta atacante);


    
    
}