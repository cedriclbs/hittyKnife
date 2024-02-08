package config;
import java.util.ArrayList;
import java.util.List;
import entity.*;
import static java.lang.Math.round;

public class Game {
    Knife knife;
    List<Cible> listeCible;
    int life;
    int lastPos;
    final int RATIO_AFFICHE = 1;
    final int LONGUEUR_VECT = 5;


    Game(){
        knife = new Knife();
        listeCible = new ArrayList<>();
        life = 3;
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche le knife sur le terminal où "A" représente le knife et "o" le vecteur de longueur LONGUEUR_VECT.
     * À noter : pour un meilleur rendu mieux vaux l'exécuter dans le cmd plutôt que dans l'IDE.
     */
    public void affichage(){
            clearScreen();
            System.out.println(knife.getY());
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 20; j++) {
                    if (j == 10 && i == 40-round(knife.getY() / RATIO_AFFICHE)) {
                        System.out.print(" A ");
                    }
                    else if (knife.estDansVecteur(LONGUEUR_VECT,j,40-i)) System.out.print(" o ");
                    else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
            }
            lastPos = (int) round(knife.getY()/RATIO_AFFICHE);

    }


    /**
     * Boucle principale du jeu
     * @param delta timer du jeu.
     */
     public void update(double delta){
        knife.saut();
        knife.addAngle(2);
        affichage();
     }


}
