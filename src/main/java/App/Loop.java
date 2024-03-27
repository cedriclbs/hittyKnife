package App;

import config.Game;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Cette classe gère la boucle de jeu et l'exécution des mises à jour du jeu à intervalles réguliers.
 */
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
        long updateLength = now - lastLoopTime;
        lastLoopTime = now;

        double delta0 = updateLength / ((double) OPTIMAL_TIME);
        delta = delta0;
        g.update(delta0);
    }

    /**
     * Méthode pour démarrer la fonction de tick.
     */
    public void startTickFunction() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        int INITIAL_DELAY = 0;
        int TICK_INTERVAL = 1;
        executor.scheduleAtFixedRate(this::tickAction, INITIAL_DELAY, TICK_INTERVAL, TimeUnit.MILLISECONDS);
    }
}

/*public class Loop {
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private boolean isRunning;
    private Game g;
    public static double delta;

    public Loop(Game game) {
        isRunning = true;
        this.g = game;

        long lastLoopTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta0 = updateLength / ((double) OPTIMAL_TIME);

            updateGame(delta0);

            delta = delta0;

            long sleepTime = Math.max(0, (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame(double delta) {
        g.update(delta);
    }

}*/