package config;

/**
 * Enumération représentant les différents états du jeu.
 */
public enum States {
    HOMEMENU,
    SOLOMODE,
    VERSUSMODE,
    SHOPMENU,
    GAMEOVERMENU;


    /**
     * Variable statique pour stocker l'état actuel du jeu
     */
    public static States states = HOMEMENU;

    /**
     * Définit l'état actuel du jeu.
     * @param s Le nouvel état du jeu.
     */
    public static void setStates(States s) {
        states = s;

    }

}
