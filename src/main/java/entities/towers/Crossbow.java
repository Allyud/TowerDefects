package entities.towers;
import BattleField.BattleField;
import entities.enemies.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crossbow extends Tower {
    public Crossbow(int x, int y, BattleField battleField) {
        super(x, y, battleField);
        price = 100;
        baseDamage = 100;
        baseReload = 1;
        baseRange = 2.45;
        generateImage();
    }
    public void attack(Enemy enemy){
        arrows.add(new Arrow(this, enemy));
    }
    public Enemy findTarget(){
        switch(strategy){
            case "First":
                for (Enemy enemy : battleField.getCurrentEnemies());
        }
        return null;
    }
    @Override
    public BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(69, 9, 9, 255));
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();
        return image;
    }
}
