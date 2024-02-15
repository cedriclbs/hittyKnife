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

    /**
     * Initialise une nouvelle sauvegarde de jeu.
     *
     * @param knife Le couteau du joueur.
     * @param listeCible Les cibles actuelles.
     */
    public GameSave(Knife knife, List<Cible> listeCible) {
        this.knife = knife;
        this.listeCible = listeCible;
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
}
