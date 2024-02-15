package config;

import java.awt.Color;
import java.awt.Graphics;

public class Solo {
    int score;
    int monnaie;
    int vie = 3;

    public Solo(int score, int monnaie) {
        this.score = score;
        this.monnaie = monnaie;
    }

    //Fonction pour augmentation du score
    //TODO : gagne plus de points si knife est bien centralisé sur la cible 
    void augmentescore() {
        score += 1;
        //if (knifehitcenter()) { score += 2 * 100}
    }

    //Fonction pour transformer le Score du joueur en argent que sera utilisé dans le Shop
    //TODO : il serait mieux de l'avoir en int même en divisant par 0.3 (pourrant changer valeur comme on veut)
    double transformScoreInGold() {
        return score * 0.3;
    }

    //Fonction pour reset le Score lorsqu'on sort du mode solo
    // void resetScore() {
    //     transformScoreInGold();
    //     score = 0;
    // }

    //Fonction pour afficher les vies du joueur en position x et y une fois l'interface graphique implementée
    //TODO : A transformer en croix pour l'affichage des vies du joueur
    private void afficheViesRestantes(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.drawString("Vies restantes : " + vie, 250, 100);
    } 

    //Fonction pour tout reset lorsqu'on sort du mode solo
    public void resetAll(){
        transformScoreInGold();
        score = 0;
        vie = 3;
    }

    //Fonction pour enlever une vie au joueur et s'il n'a plus de vie elle renvoie au menu game over
    public void enleverUneVie () {
        vie--;
        if (vie <= 0){
            //setStates(GAMEOVERMENU); 
        }
    }

    //Récupère les vies du joueurs
    public int getVies() {
        return vie;
    }
}
