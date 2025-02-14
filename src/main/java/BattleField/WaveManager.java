package BattleField;

import BattleField.BattleField;
import game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaveManager {
    private static WaveManager instance;
    private ArrayList<Wave> waves = new ArrayList<>();
    private ArrayList<Wave> activeWaves = new ArrayList<>();
    private int currentWaveIndex;
    private boolean waveActive = false;
    private BattleField battleField;
    private long lastWaveTime;
    private final long timeBeetweenWaves = 5000;
    private WaveManager(BattleField battleField) {
        this.battleField = battleField;
        waves = new ArrayList<>();
        activeWaves = new ArrayList<>();
        loadWaves();
    }

    public static WaveManager getInstance(BattleField battleField) {
        if (instance == null) {
            instance = new WaveManager(battleField);
        }
        return instance;
    }

    public static WaveManager resetInstance(BattleField battleField) {
        instance = null;
        return WaveManager.getInstance(battleField);
    }
    private void loadWaves() {
        waves.add(new Wave(100,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy"),
                List.of(0L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(110,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy"),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(140,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy"),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,3000L,2000L,2000L,2000L,2000L,2000L)));
        waves.add(new Wave(160,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy",
                        "BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy"),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,3000L,2000L,2000L,2000L,2000L,2000L,2000L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(210,
                List.of("FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy"
                        ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L,2000L,2000L,2000L,2000L,2000L,2000L)));
        waves.add(new Wave(260,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy"
                ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L,2000L,2000L,2000L,2000L,2000L,2000L)));
        waves.add(new Wave(310,
                List.of("FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy"
                ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(400,
                List.of("FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy"
                ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,2000L,2000L,2000L,2000L,2000L,2000L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(450,
                List.of("FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy"
                ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L,2000L,2000L,2000L,2000L,2000L,2000L,1000L,1000L,1000L,1000L,1000L,1000L)));
        waves.add(new Wave(500,
                List.of("BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy","BasicEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy","FastEnemy",
                        "TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy","TankEnemy"
                ),
                List.of(0L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L,2000L,1000L,1000L,1000L,1000L,1000L,2000L,2000L,2000L,2000L,2000L,2000L)));

    }

    public void startNextWave() {
        if (currentWaveIndex < waves.size()) {
            System.out.println("Starting Wave " + (currentWaveIndex + 1));
            activeWaves.add(waves.get(currentWaveIndex));
            lastWaveTime = Game.getCurrentGameTime();
            currentWaveIndex++;
        } else {
            System.out.println("All waves completed!");
        }
    }

    public void update() {
        if (!battleField.isPaused()){
            ArrayList<Wave> wavesToRemove = new ArrayList<>();
            for (Wave wave : activeWaves) {
                if (wave.update(battleField)) {
                    wavesToRemove.add(wave);
                    System.out.println("Wave " + currentWaveIndex + " completed!");
                }
            }
            activeWaves.removeAll(wavesToRemove);
        }
    }

    public boolean waveCanBeSpawned(){
        return Game.getCurrentGameTime() - lastWaveTime > timeBeetweenWaves;
    }

    public boolean isWaveActive() {
        return waveActive;
    }
    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
