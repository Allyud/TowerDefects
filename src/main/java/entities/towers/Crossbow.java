package entities.towers;
import BattleField.BattleField;
import BattleField.Tiles.TowerTile;
import entities.enemies.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crossbow extends Tower {
    public Crossbow(TowerTile towerTile, BattleField battleField) {
        super(towerTile, battleField);
        upgradeCosts = new int[] { 100, 60, 75, 88, 104, 122, 142, 165,189,216 };
        damageLevels = new int[] {100, 140, 188, 246,316,400,500,618,756,916};
        reloadLevels = new double[] {1, 0.95,0.9,0.85,0.8,0.75,0.7,0.65,0.6,0.55};
        rangeLevels =  new double[] {2.45, 2.5, 2.55,2.6,2.65,2.7,2.75,2.8,2.85,2.9};
        sellCost = upgradeCosts[0]/2;
        name = "Crossbow";
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
        g.setColor(new Color(128, 107, 7));
        g.fillRoundRect(0, 0, size.getWidth(), size.getHeight(), size.getWidth()/2,size.getHeight()/2);
        renderLevel(g);
        g.dispose();

        return image;
    }
}
