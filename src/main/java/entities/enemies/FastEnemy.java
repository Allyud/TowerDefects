package entities.enemies;

import BattleField.BattleField;
import core.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FastEnemy extends Enemy{
    public FastEnemy(int scale, BattleField battleField) {
        super(new Size(battleField.getTileSize()/2, battleField.getTileSize()/2),250, 1.5,scale,  battleField);
        moneyReward = 15;
    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(221, 100, 255));
        g.rotate(Math.toRadians(45), size.getWidth()/2, size.getHeight()/2);
        g.fillRoundRect(size.getWidth()*3/20, size.getWidth()*3/20, size.getWidth()*7/10, size.getHeight()*7/10, size.getWidth()*7/20, size.getHeight()*7/20) ;
        g.rotate(Math.toRadians(-45), size.getWidth()/2, size.getHeight()/2);

        g.setColor(Color.BLACK);
        int eyeRadius = size.getHeight()/7;
        g.fillOval((size.getWidth()-4*eyeRadius)/2, size.getWidth()*2/5, eyeRadius, eyeRadius);
        g.fillOval((size.getWidth()+2*eyeRadius)/2, size.getWidth()*2/5, eyeRadius, eyeRadius);
        int mouthRadius = size.getHeight()/3;
        g.setStroke(new BasicStroke(2));
        g.drawArc((int)(size.getWidth()-2.4*eyeRadius)/2, size.getWidth()/3, mouthRadius, mouthRadius, 225, 90);
        //g.setColor(new Color(204, 48, 76));
        //g.fillRect(0, 0, size.getWidth(), 5);
        g.dispose();

        return image;
    }
}