package entities.enemies;

import BattleField.BattleField;
import core.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TankEnemy extends Enemy{
    public TankEnemy(int scale, BattleField battleField) {
        super(new Size(battleField.getTileSize()*3/4, battleField.getTileSize()*3/4),1700, 0.7,scale,  battleField);
        moneyReward = 20;
    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(4, 97, 9));
        ///g.fillOval(0, 0, size.getWidth(), size.getHeight());
        Polygon p = createHexagon(size.getWidth()/2, size.getHeight()/2,size.getHeight()/2);
        g.fillPolygon(p);
        int eyeRadius = size.getHeight()/8;
        g.setColor(Color.BLACK);
        g.fillOval((int) (size.getWidth()-4.5*eyeRadius)/2, size.getWidth()*3/10, eyeRadius, eyeRadius);
        g.fillOval((int) (size.getWidth()+2.5*eyeRadius)/2, size.getWidth()*3/10, eyeRadius, eyeRadius);
        g.setStroke(new BasicStroke(4));
        g.drawLine((int) (size.getWidth()-3*eyeRadius)/2, size.getWidth()*7/10, (int) (size.getWidth()+3*eyeRadius)/2, size.getWidth()*7/10);
        g.dispose();



        return image;
    }
    private Polygon createHexagon(int centerX, int centerY, int size) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(30 + 60 * i);
            xPoints[i] = centerX + (int) (size * Math.cos(angle));
            yPoints[i] = centerY + (int) (size * Math.sin(angle));
        }

        return new Polygon(xPoints, yPoints, 6);
    }
}
