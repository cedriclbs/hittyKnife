package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Game;
import entity.Knife;
import geometry.Geometry;

import java.io.File;
import java.io.IOException;

import static java.lang.Math.round;

public class Debug {

    static int lastPos;
    final static int RATIO_AFFICHE = 1;
    final static int LONGUEUR_VECT = 5;




    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche le knife sur le terminal où "A" représente le knife et "o" le vecteur de longueur LONGUEUR_VECT.
     * À noter : pour un meilleur rendu mieux vaut l'exécuter dans le cmd plutôt que dans l'IDE.
     */
    public static void affichage(Knife knife){
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);
        clearScreen();
        //System.out.println(" y : "+knife.getY()+" velocite : "+knife.getVelocite());
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (j == knife.getX() && i == 40-round(knife.getY() / RATIO_AFFICHE)) {
                    System.out.print(" A ");
                }
                else if (Geometry.estDansVecteur(LONGUEUR_VECT,knife.getAngle(),knife.getX(),knife.getY(),j,40-i)) System.out.print(" o ");
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);

    }
    public static void testSerialization() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Game gameTest = new Game();
            gameTest.setNomUtilisateur("testUser"); // Définir un nomUtilisateur valide

            // Sérialisation
            mapper.writeValue(new File("testGame.json"), gameTest);

            // Désérialisation pour tester
            Game loadedGame = mapper.readValue(new File("testGame.json"), Game.class);
            System.out.println("Test chargé: " + loadedGame.getNomUtilisateur()); // Afficher le nomUtilisateur pour vérifier.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
