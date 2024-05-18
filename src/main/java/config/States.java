package config;

/**
 * Enumération représentant les différents états du jeu.
 */
public enum States {
    HOMEMENU,       // Menu principal
    SOLOMODE,       // Mode solo du jeu
    VERSUSMODE,     // Mode versus du jeu
    SHOPMENU,       // Menu de la boutique
    GAMEOVERMENU;   // Menu de fin de jeu

    /**
     * Variable statique pour stocker l'état actuel du jeu
     */
    public static States states = HOMEMENU; // L'état initial du jeu est le menu principal

    /**
     * Définit l'état actuel du jeu.
     *
     * @param s Le nouvel état du jeu.
     */
    public static void setStates(States s) {
        states = s;
    }
}
