package entities.towers;

import BattleField.BattleField;
import BattleField.Tiles.Tile;
import core.*;
import entities.enemies.*;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Tower extends RenderableObject {
    int lvl;
    double price;
    double baseDamage;
    double baseReload;
    double baseRange;
    boolean isVisible;
    ArrayList<Arrow> arrows;
    private long lastAttackTime;
    Tile tile;
    String strategy = "First";
    boolean targetFixed = true;
    int gridX;
    int gridY;
    BattleField battleField;
    protected Tower(int x, int y, BattleField battleField) {
        super(new Position(x* battleField.getTileSize(), y* battleField.getTileSize()), new Size(battleField.getTileSize(), battleField.getTileSize()));
        tile = battleField.getTileGrid()[y][x];
        this.battleField = battleField;
        isVisible = true;
        gridX = x;
        gridY = y;
    }

    public Enemy findTarget(){
        switch(strategy){
            case "First":
                for (Enemy enemy : battleField.getCurrentEnemies()){
                    Position enemyPos = enemy.getPosition();
                    if (enemyPos.getX()*enemyPos.getX() + enemyPos.getY()+enemyPos.getY() < baseRange*baseRange){
                        return enemy;
                    }
                }
        }
        return null;
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void update() {
        long currentTime = System.currentTimeMillis(); // Get current time in milliseconds

        // Check if enough time has passed for next attack
        if ((currentTime - lastAttackTime) >= (baseReload * 1000)) {
            Enemy target = findTarget();
            if (target != null) {
                attack(target);
                lastAttackTime = currentTime; // Reset cooldown timer
            }
        }

        for (Arrow arrow : arrows){
            arrow.update();
        }

    }

    public abstract void attack(Enemy enemy);


}
