package config;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe représentant un compte à rebours.
 */
public class GameTimer {
    private int seconds;

    /**
     * Constructeur de la classe GameTimer.
     * @param minutes Le nombre de minutes pour initialiser le compte à rebours.
     */
    public GameTimer(int minutes) {
        this.seconds = minutes * 60;
    }

    /**
     * Démarre le compte à rebours.
     */
    public void start() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (seconds > 0) {
                    int mins = seconds / 60;
                    int secs = seconds % 60;
                    System.out.printf("Temps restant : %02d:%02d%n", mins, secs);
                    seconds--;
                } else {
                    System.out.println("Temps écoulé !");
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000); // Exécute la tâche toutes les 1000 ms (1 seconde)
    }

    /**
     * @return Le nombre de secondes restantes dans le compte à rebours.
     */
    public int getSeconds() {
        return this.seconds;
    }

}