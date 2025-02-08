package entities.enemies;

import BattleField.BattleField;
import core.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicEnemy extends Enemy{
    public BasicEnemy(int scale, BattleField battleField) {
        super(new Size(30,30),(int) ((1000*(scale))/100.0), 1, battleField);
    }
    //public void spawn();

    /*@Override
    public void update() {
        if (position.getX() < 0 || position.getX() > battleField.getFieldWidth()-size.getWidth()) {
            dirVector[0] *= -1;
            dirAngle = Math.PI - dirAngle;
        }
        if (position.getY() < 0 || position.getY() > battleField.getFieldHeight()-size.getHeight()) {
            dirVector[1] *= -1;
            dirAngle = -dirAngle;
        }
        position.setPosition((position.getX() + dirVector[0]*baseSpeed), (position.getY() + dirVector[1]*baseSpeed));
    }

     */

    public void updateSprite(){

    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.BLUE);
        g.fillOval(0, 0, size.getWidth(), size.getHeight());

        g.dispose();

        return image;
    }
}
