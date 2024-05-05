package App;

import config.Game;

import java.util.Timer;
import java.util.TimerTask;


public class Loop {

    private final Game g;
    private long lastLoopTime;
    private static final int TARGET_FPS = 100;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
    public static double delta;

    /**
     * Constructeur de la classe Loop.
     *
     * @param g Le jeu à mettre à jour.
     */
    public Loop(Game g){
        this.g = g;
        lastLoopTime = System.nanoTime();
    }

    /**
     * Méthode d'action à exécuter à chaque tick de la boucle de jeu.
     */
    void tickAction() {
        long now = System.nanoTime();
        double delta0 = (now - lastLoopTime) / ((double) OPTIMAL_TIME);
        delta = delta0;
        g.update(delta0);
        lastLoopTime = now;
    }

    /**
     * Méthode pour démarrer la fonction de tick.
     */
    public void startTickFunction() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tickAction();
            }
        }, 0, 100 / TARGET_FPS);
    }
}