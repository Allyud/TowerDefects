package entities.towers;

import BattleField.BattleField;
import BattleField.Tiles.Tile;
import BattleField.Tiles.TowerTile;
import core.*;
import entities.enemies.*;
import game.Game;

import java.awt.*;
import java.util.ArrayList;

public abstract class Tower extends RenderableObject {
    int lvl = 0;
    int[]  upgradeCosts;
    int[] damageLevels;
    double[] reloadLevels;
    double[] rangeLevels;
    int upgradeCost;
    int sellCost = 0;
    int damage;
    double reloadTime;;
    double range;
    String name;
    int dealtDamage;
    boolean isVisible;
    Enemy target;
    ArrayList<Bullet> bullets;
    private long lastAttackTime;
    TowerTile tile;
    String strategy = "First";
    boolean targetFixed = true;
    int gridX;
    int gridY;
    BattleField battleField;
    protected Tower(TowerTile towerTile, BattleField battleField) {
        super(new Position((towerTile.getGridX()+0.1)* battleField.getTileSize(), (towerTile.getGridY()+0.1)* battleField.getTileSize()), new Size((int) (battleField.getTileSize()*0.8), (int) (battleField.getTileSize()*0.8)));
        gridX = towerTile.getGridX();
        gridY = towerTile.getGridY();
        tile = towerTile;
        this.battleField = battleField;
        isVisible = true;
        bullets = new ArrayList<>();
    }

    public void update() {
        long currentTime = Game.getCurrentGameTime();
        if ((currentTime - lastAttackTime) >= (reloadTime * 1000)) {
            Enemy target = findTarget(battleField.getCurrentEnemies());
            if (target != null) {
                //System.out.println("Target: " + target);
                attack(target);
                lastAttackTime = currentTime;
            }
        }
        for (Bullet bullet : bullets){
            bullet.update();
        }
    }
    public Enemy findTarget(ArrayList<Enemy> enemies){
        double squaredDistance;
        if (targetFixed){
            if (target != null && target.isAlive()){
                if (target.getSquaredDistance(getCenterPosition()) <= range*range){
                    return target;
                }
            }

        }
        boolean found = false;
        switch (strategy) {
            case "First":
                double maxTraveledDistance = 0;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());

                    if (squaredDistance < Math.pow(range, 2)) {
                        if (enemy.getTraveledDistance() > maxTraveledDistance) {
                            found = true;
                            target = enemy;
                            maxTraveledDistance = enemy.getTraveledDistance();
                        }
                    }
                }
                break;
            case "Last":
                double minTraveledDistance = 0;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());
                    if (squaredDistance < Math.pow(range, 2)) {
                        if (enemy.getTraveledDistance() < minTraveledDistance) {
                            found = true;
                            target = enemy;
                            minTraveledDistance = enemy.getTraveledDistance();
                        }
                    }
                }
                break;
            case "Closest":
                double minSquaredDistance = range * range * 2;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());
                    if (squaredDistance < Math.pow(range, 2)) {
                        if (squaredDistance < minSquaredDistance) {
                            found = true;
                            target = enemy;
                            minSquaredDistance = squaredDistance;
                        }
                    }
                }
                break;
            case "Farthest":
                double maxSquaredDistance = 0;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());
                    if (squaredDistance < Math.pow(range, 2)) {
                        if (squaredDistance > maxSquaredDistance) {
                            found = true;
                            target = enemy;
                            maxSquaredDistance = squaredDistance;
                        }
                    }
                }
                break;
            case "Strongest":
                double maxHealth = 0;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());
                    if (squaredDistance < Math.pow(range, 2)) {
                        if (enemy.getHealth() > maxHealth) {
                            found = true;
                            target = enemy;
                            maxHealth = enemy.getHealth();
                        }
                    }
                }
                break;
            case "Weakest":
                double minHealth = 999999999;
                for (Enemy enemy : enemies) {
                    squaredDistance = enemy.getSquaredDistance(getCenterPosition());
                    if (squaredDistance < Math.pow(range, 2)) {
                        if (enemy.getHealth() < minHealth) {
                            found = true;
                            target = enemy;
                            minHealth = enemy.getHealth();
                        }
                    }
                }
                break;
        }
        if (found){
            return target;
        }
        return null;
    }

    public void upgrade(){
        sellCost += upgradeCost/2;
        lvl++;
        upgradeCost = upgradeCosts[lvl];
        damage = damageLevels[lvl - 1];
        reloadTime = reloadLevels[lvl - 1];
        range = rangeLevels[lvl - 1] * battleField.getTileSize();
        generateImage();
    }

    protected void renderLevel(Graphics2D g){
        g.setFont(new Font("Shafarik", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.BLACK);
        g.drawString( Integer.toString(lvl), (size.getWidth() - fm.stringWidth(Integer.toString(lvl)))/2, size.getHeight()-fm.getHeight() + fm.getAscent());
    }

    public boolean isVisible(){
        return isVisible;
    }

    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    public abstract void attack(Enemy enemy);

    public int getPrice(){
        return upgradeCosts[0];
    }
    public double getDamage() {
        return damage;
    }
    public int getUpgradeCost(){
        return upgradeCost;
    }
    public double getReloadTime(){
        return reloadTime;
    }
    public double getRange(){
        return range/ battleField.getTileSize();
    }

    public String getName() {
        return name;
    }
    public int getLevel(){
        return lvl;
    }
    public int getSellCost(){
        return sellCost;
    }
    public TowerTile getTile(){
        return tile;
    }
}
