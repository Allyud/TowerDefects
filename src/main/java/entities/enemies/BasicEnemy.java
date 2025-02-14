package entities.enemies;

import BattleField.BattleField;
import core.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BasicEnemy extends Enemy{
    public BasicEnemy(int scale, BattleField battleField) {
        super(new Size(battleField.getTileSize()/2,battleField.getTileSize()/2),320, 1,scale,  battleField);
        moneyReward = 10;
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
        Graphics2D g = image.createGraphics();

        g.setColor(new Color(4, 112, 234));
        g.fillOval(0, 0, size.getWidth(), size.getHeight());
        g.setColor(Color.BLACK);
        int eyeRadius = size.getHeight()/6;
        g.fillOval((size.getWidth()-4*eyeRadius)/2, size.getWidth()/4+ size.getWidth()/20, eyeRadius, eyeRadius);
        g.fillOval((size.getWidth()+2*eyeRadius)/2, size.getWidth()/4+ size.getWidth()/20, eyeRadius, eyeRadius);
        int mouthRadius = size.getHeight()*2/3;
        g.setStroke(new BasicStroke(2));
        g.drawArc((size.getWidth()-4*eyeRadius)/2, size.getWidth()/10 + size.getWidth()/20, mouthRadius, mouthRadius, 225, 60);
        g.dispose();

        return image;
    }
}
