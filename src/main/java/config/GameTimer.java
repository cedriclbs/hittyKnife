package config;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int seconds;

    public GameTimer(int minutes) {
        this.seconds = minutes * 60;
    }

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

    public int getSeconds() {
        return this.seconds;
    }

}