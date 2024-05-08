package config;

/**
 * Enumération représentant les différents états du jeu.
 */
public enum States {
    HOMEMENU,     // Menu principal
    SOLOMODE,     // Mode solo
    VERSUSMODE,   // Mode versus
    SHOPMENU,
    GAMEOVERMENU; // Menu de fin de partie

    // Variable statique pour stocker l'état actuel du jeu
    public static States states = HOMEMENU;

    /**
     * Définit l'état actuel du jeu.
     * @param state Le nouvel état du jeu.
     */
    public static void setStates(States state) {
        states = state;
    }

}
