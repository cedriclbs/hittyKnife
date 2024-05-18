package config;

import java.util.ArrayList;
import java.util.List;
import entity.Cible;

/**
 * Représente un round dans le jeu, contenant une liste de cibles de type Cible.
 */
public class Round {
    private List<Cible> listeCibles;

    /**
     * Constructeur par défaut qui initialise la liste de cibles.
     */
    public Round() {
        this.listeCibles = new ArrayList<>();
    }

    /**
     * Retourne la liste des cibles dans ce round.
     *
     * @return La liste des cibles.
     */
    public List<Cible> getListeCibles() {
        return listeCibles;
    }

    /**
     * Ajoute une cible à la liste des cibles de ce round.
     *
     * @param cible La cible à ajouter.
     */
    public void addCible(Cible cible) {
        this.listeCibles.add(cible);
    }
}
