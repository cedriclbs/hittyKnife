package config;
import java.util.ArrayList;
import java.util.List;
import entity.*;


public class Game {
    Knife knife;
    List<Cible> listeCible;
    int life;


    Game(){
        knife = new Knife();
        listeCible = new ArrayList<>();
        life = 3;
    }



    /**
     * Boucle principale du jeu
     * @param delta timer du jeu.
     */
     public void update(double delta){
        knife.saut();
        knife.addAngle(2);
        Debug.affichage(knife);
     }


}
