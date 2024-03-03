package config;

import entity.Knife;

/**
 * Classe pour sauvegarder l'état du jeu.
 * Permet de restaurer l'état du jeu à partir d'une sauvegarde.
 */
public class GameSave {
    private Knife knife;
    private int argent;

    public GameSave(){}

    /**
     * Initialise une nouvelle sauvegarde de jeu.
     *
     * @param knife Le couteau du joueur.
     */
    public GameSave(Knife knife, int argent) {
        this.knife = knife;
        this.argent = argent;
    }

    /**
     * @return Le couteau sauvegardé.
     */
    public Knife getKnife() {
        return knife;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public void setKnife(Knife knife) {
        this.knife = knife;
    }
}
