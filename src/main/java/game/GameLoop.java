package game;

public class GameLoop implements Runnable{

    private final Game game;
    private boolean running;
    private final double updateRate = 1.0d/60.0d;

    private long nextStatTime;
    private int fps, ups;

    public GameLoop(Game game) {
        this.game = game;
    }
    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTime = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTime;
            lastUpdate = currentTime;

            if (accumulator >= updateRate) {
                while (accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                }
                render();
            }
            printStats();
        }
    }
    private void update() {
        game.update();
        ups++;
    }
    private void render() {
        game.render();
        fps++;
    }
    private void printStats() {
        if (System.currentTimeMillis() > nextStatTime ) {
            nextStatTime = System.currentTimeMillis() + 1000;
            System.out.println("FPS: " + fps + "; UPS: " + ups);
            fps = 0;
            ups = 0;
        }
    }
}
