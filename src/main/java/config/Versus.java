package config;

/**
 * Classe représentant le mode Versus du jeu.
 */
public class Versus {
    private int scoreJ1;
    private int scoreJ2;
    private GameTimer timer;

    /**
     * Constructeur de la classe Versus.
     * @param s1 Le score initial du joueur 1.
     * @param s2 Le score initial du joueur 2.
     */
    public Versus(int s1, int s2) {
        // Vérification des scores initiaux
        if (s1 > 5 || s2 > 5) {
            // Gestion des valeurs invalides
            // System.out.println("Valeur Invalide");
        }
        scoreJ1 = s1;
        scoreJ2 = s2;
    }

    /**
     * Méthode qui détermine le vainqueur du versus.
     * TODO : Adapter la logique en fonction de l'avancement du jeu.
     */
    private void determinerVainqueur() {
        if (scoreJ1 == 5) {
            // System.out.println("J1 a gagné");
            return;
        }
        if (scoreJ2 == 5) {
            // System.out.println("J2 a gagné");
        }
    }

    /**
     * Méthode appelée à la fin d'une manche pour mettre à jour les scores.
     */
    private void finDeManche() {
        if (j1BoardEstVide() && !j2BoardEstVide()) {
            scoreJ1++;
        }
        if (j2BoardEstVide() && !j1BoardEstVide()) {
            scoreJ2++;
        }
    }

    /**
     * Méthode permettant de savoir si le plateau du joueur 1 est vide.
     * @return true si le plateau du joueur 1 est vide, sinon false.
     */
    private boolean j1BoardEstVide() {
        // TODO : Implémenter cette méthode pour vérifier si le joueur 1 a vidé son terrain.
        return true;
    }

    /**
     * Méthode permettant de savoir si le plateau du joueur 2 est vide.
     * @return true si le plateau du joueur 2 est vide, sinon false.
     */
    private boolean j2BoardEstVide() {
        // TODO : Implémenter cette méthode pour vérifier si le joueur 2 a vidé son terrain.
        return true;
    }

    /**
     * Réinitialise les scores pour pouvoir rejouer.
     */
    private void resetScores() {
        scoreJ1 = 0;
        scoreJ2 = 0;
    }

    /**
     * Initialise le timer pour le versus.
     */
    private void initTimer() {
        this.timer = new GameTimer(2);
        timer.start();
    }

    /**
     * Vérifie si le timer est écoulé.
     * @return true si le timer est écoulé, sinon false.
     */
    private boolean timerTermine() {
        return (timer.getSeconds() == 0);
    }

}
