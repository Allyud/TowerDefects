package entities.towers;
import BattleField.BattleField;
import BattleField.Tiles.TowerTile;
import entities.enemies.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FireTower extends Tower {
    public FireTower(TowerTile towerTile, BattleField battleField) {
        super(towerTile, battleField);
        upgradeCosts = new int[] { 150, 50, 60, 72, 86, 102, 120, 140, 162, 186 };
        damageLevels = new int[] {230, 270, 318, 376, 446, 530, 630, 748, 886, 1046};
        reloadLevels = new double[] {1.5, 1.4, 1.3, 1.2, 1.1, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5};
        rangeLevels =  new double[] {3.05, 3.10, 3.15, 3.20, 3.25, 3.30, 3.35, 3.40, 3.45, 3.50};
        sellCost = upgradeCosts[0]/2;
        name = "FireTower";
        upgrade();
    }
    public void attack(Enemy enemy){
        bullets.add(new Bullet(this, enemy));
    }
    /*public Enemy findTarget(){
        switch(strategy){
            case "First":
                for (Enemy enemy : battleField.getCurrentEnemies());
        }
        return null;
    }

     */
    @Override
    public BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.red);
        g.fillRoundRect(0, 0, size.getWidth(), size.getHeight(), size.getWidth()/2,size.getHeight()/2);
        renderLevel(g);
        g.dispose();

        return image;
    }
}