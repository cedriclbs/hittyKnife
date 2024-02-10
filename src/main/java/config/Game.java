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
        knife.jump();
    }



    /**
     * Boucle principale du jeu
     * @param delta timer du jeu.
     */
     public void update(double delta){
         if (knife.isInTheAir){
             knife.updateJump();
             knife.addAngle(4);
         }


        Debug.affichage(knife);
     }


}
