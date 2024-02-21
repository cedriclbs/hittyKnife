package config;

public class Versus {
    int scoreJ1;
    int scoreJ2;
    GameTimer timer;

    public Versus(int s1, int s2) {
        if (s1 > 5 || s2 > 5) {
            //System.out.println("Valeur Invalide");
        }
        scoreJ1 = s1;
        scoreJ2 = s2;
    }

    //Fonction qu'informe qui sera le vainqueur
    //TODO : adaptez la fonction en fonction de l'avancement du jeu
    private void vainqueur() {
        if (this.scoreJ1 == 5) {
            //System.out.println("J1 a gagné");
            return;
        }
        if (this.scoreJ2 == 5) {
            //System.out.println("J2 a gagné");
        }
    }

    private void finDeManche() {
        if (J1BoardisClear() && !J2BoardisClear()) {
            scoreJ1++;
        }
        if (J2BoardisClear() && !J1BoardisClear()) {
            scoreJ2++;
        }
    }

    private boolean J1BoardisClear() {
        // TODO : Implementer cela pour informer que le joueur 1 a vidé son terrain
        return true;
    }

    private boolean J2BoardisClear() {
        // TODO : Implementer cela pour informer que le joueur 2 a vidé son terrain
        return true;
    }

    //Fonction pour reset les scores pour pouvoir rejouer
    private void resetScores() {
        scoreJ1 = 0;
        scoreJ2 = 0;
    }

    private void initTimer() {
        this.timer = new GameTimer(2);
        timer.start();
    }

    private boolean timerOver() {
        return (timer.getSeconds() == 0);
    }
}
