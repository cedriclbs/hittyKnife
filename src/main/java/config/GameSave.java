package config;
import java.io.Serializable;
import entity.Knife;
import entity.Cible;
import java.util.List;

/**
 * Classe sérialisable pour sauvegarder l'état du jeu, incluant un couteau ({@link Knife})
 * et une liste de cibles ({@link List} de {@link Cible}). Permet de restaurer l'état du jeu
 * à partir d'une sauvegarde.
 */
public class GameSave implements Serializable {
    private Knife knife;
    private List<Cible> listeCible;
    private int argent;

    /**
     * Initialise une nouvelle sauvegarde de jeu.
     *
     * @param knife Le couteau du joueur.
     * @param listeCible Les cibles actuelles.
     */
    public GameSave(Knife knife, List<Cible> listeCible, int argent) {
        this.knife = knife;
        this.listeCible = listeCible;
        this.argent = argent;
    }

    /**
     * @return Le couteau sauvegardé.
     */
    public Knife getKnife() {
        return knife;
    }

    /**
     * @return Les cibles sauvegardées.
     */
    public List<Cible> getListeCible() {
        return listeCible;
    }

    public int getArgent() {
        return argent;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }
}
