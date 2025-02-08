package BattleField;

import entities.enemies.Enemy;

public class Wave {
    private int enemyCount;
    private Enemy[] enemies;
    private double[] timeDelays;

    public Wave(Enemy[] enemies, double[] timeDelays) {
        this.enemies = enemies;
        this.timeDelays = timeDelays;
        enemyCount = enemies.length;
    }


}
