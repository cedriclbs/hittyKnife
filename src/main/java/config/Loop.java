package config;
public class Loop {
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    private boolean isRunning;
    private Game g;
    public static double delta;

    public Loop() {
        isRunning = true;
        this.g = new Game();

        long lastLoopTime = System.nanoTime();

        while (isRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta0 = updateLength / ((double) OPTIMAL_TIME);

            updateGame(delta0);

            this.delta = delta0;

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

}