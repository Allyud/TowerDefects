package BattleField;

import entities.enemies.*;
import game.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Wave {
    private final int enemyCount;
    private LinkedList<String> enemies = new LinkedList<>();
    private LinkedList<Long> spawnDelays = new LinkedList<>();
    private int currentEnemyCount;
    private Long currentSpawnDelay;
    private boolean waveFinished;
    private Map.Entry<String, Integer> currentEnemyData;
    private long lastSpawnTime;
    private int scale;
    public Wave(int scale, List<String> enemies, List<Long> spawnDelays) {
        //this.enemiesData.addAll(enemies);
        this.scale = scale;
        this.enemies.addAll(enemies);
        this.spawnDelays.addAll(spawnDelays);
        enemyCount = enemies.size();
        this.lastSpawnTime = Game.getCurrentGameTime();
        this.currentSpawnDelay = this.spawnDelays.poll();
    }

    public boolean update(BattleField battleField) {
        if (currentEnemyCount < enemyCount){
            long currentTime = Game.getCurrentGameTime();

            if (currentTime-lastSpawnTime >= currentSpawnDelay){
                lastSpawnTime = currentTime;
                currentSpawnDelay = spawnDelays.poll();
                spawnEnemy(battleField, enemies.poll(), scale);
                currentEnemyCount++;
            }
            return false;
        }
        else{
            waveFinished = true;
            return true;
        }
    }

    private void spawnEnemy(BattleField battleField, String enemyType, int scale) {
        Enemy enemy = createEnemy(battleField,enemyType, scale);
        if (enemy != null){
            battleField.spawnEnemy(enemy);
        }
    }

    private Enemy createEnemy(BattleField battleField, String enemyType, int scale) {
        return switch (enemyType) {
            case "BasicEnemy" -> new BasicEnemy(scale, battleField);
            case "FastEnemy" -> new FastEnemy(scale, battleField);
            case "TankEnemy" -> new TankEnemy(scale, battleField);
            default -> null;
        };
    }

    public boolean isWaveFinished() {
        return waveFinished;
    }

}
