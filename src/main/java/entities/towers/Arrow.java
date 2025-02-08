package entities.towers;

import core.*;
import entities.enemies.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Arrow extends RenderableObject {
    double speed = 4;
    Enemy target;
    boolean active = true;
    public Arrow(Tower tower, Enemy target) {
        super(tower.getPosition(), new Size(5,16));
        this.target = target;
        moveTowards(this.target);
    }
    @Override
    protected BufferedImage createImage() {
        BufferedImage img = new BufferedImage(5, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        return img;
    }

    private void moveTowards(Enemy target){
        if (!active){
            return;
        }
        double dx = target.getPosition().getX() - position.getX();
        double dy = target.getPosition().getY() - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            position.setPosition(position.getX() + dx / distance * speed,
                    position.getY() + dy / distance * speed);
        } else {
            position.setPosition(target.getPosition().getX(), target.getPosition().getY());
            active = false;
        }
    }

    public void update() {
        moveTowards(target);
    }

}
