package config;
import java.io.Serializable;
import entity.Knife;
import entity.Cible;
import java.util.List;

public class GameSave implements Serializable {
    private Knife knife;
    private List<Cible> listeCible;

    // Constructeur
    public GameSave(Knife knife, List<Cible> listeCible) {
        this.knife = knife;
        this.listeCible = listeCible;
    }

    // Getters et Setters
    public Knife getKnife() {
        return knife;
    }

    public List<Cible> getListeCible() {
        return listeCible;
    }
}
