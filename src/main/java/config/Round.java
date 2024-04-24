package config;

import java.util.ArrayList;
import java.util.List;
import entity.Cible;

/**
 * Représente un round dans le jeu, contenant une liste de cibles de type TypeCible.
 */
public class Round {
    private List<Cible> listeCibles;

    public Round() {
        this.listeCibles = new ArrayList<>();
    }

    public List<Cible> getListeCibles() {
        return listeCibles;
    }

    public void setListeCibles(List<Cible> listeCibles) {
        this.listeCibles = listeCibles;
    }

    public void addCible(Cible cible) {
        this.listeCibles.add(cible);
    }
}
