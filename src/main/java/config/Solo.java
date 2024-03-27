package config;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe représentant le mode solo du jeu.
 */
public class Solo {
    private int score;
    private int monnaie;
    private int vie = 3;
    private boolean isBoss;
    private GameTimer timer;

    /**
     * Constructeur de la classe Solo.
     * @param score Le score initial du joueur.
     * @param monnaie La quantité initiale de monnaie du joueur.
     */
    public Solo(int score, int monnaie) {
        this.score = score;
        this.monnaie = monnaie;
        this.isBoss = false;
    }

    /**
     * Récupère le nombre de vies restantes du joueur.
     * @return Le nombre de vies restantes.
     */
    public int getVies() {
        return vie;
    }

    /**
     * Augmente le score du joueur.
     * TODO : Implementer une logique pour augmenter le score en fonction de la précision du tir.
     */
     void augmentescore() {
        score += 1;
        //if (knifehitcenter()) { score += 2 * 100}
    }

    /**
     * Convertit le score du joueur en monnaie pour être utilisé dans la boutique.
     * TODO : Modifier le type de retour en int après avoir défini une conversion appropriée.
     */
    double transformScoreInGold() {
        return score * 0.3;
    }

    //Fonction pour reset le Score lorsqu'on sort du mode solo
    // void resetScore() {
    //     transformScoreInGold();
    //     score = 0;
    // }

    /**
     * Affiche les vies restantes du joueur.
     * @param graphics L'objet Graphics utilisé pour l'affichage.
     * TODO : Transformer l'affichage en représentation graphique des vies du joueur.
     */
    private void afficheViesRestantes(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.drawString("Vies restantes : " + vie, 250, 100);
    }

    /**
     * Réinitialise tous les paramètres du mode solo.
     */
    public void resetAll(){
        transformScoreInGold();
        score = 0;
        vie = 3;
    }

    /**
     * Enlève une vie au joueur et vérifie si le joueur a perdu toutes ses vies.
     */
    public void enleverUneVie () {
        vie--;
        if (vie <= 0){
            //setStates(GAMEOVERMENU);
        }
    }

    /**
     * Initialise le timer pour le boss si nécessaire.
     */
    public void initTimer() {
        if (isBoss) {
            this.timer = new GameTimer(3);
            timer.start();
        }
    }

    /**
     * Vérifie si le timer du boss est écoulé.
     * @return true si le timer est écoulé, sinon false.
     */
    private boolean timerOver() {
        return (timer.getSeconds() == 0);
    }

}