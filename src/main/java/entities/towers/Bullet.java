package entities.towers;

import core.*;
import entities.enemies.Enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends RenderableObject {
    double speed = 8;
    Enemy target;
    boolean active = true;
    Tower tower;
    double damage;
    public Bullet(Tower tower, Enemy target) {
        super(new Position(tower.getCenterPosition().getX(), tower.getCenterPosition().getY()), new Size(7,7));
        this.target = target;
        this.tower = tower;
        damage = tower.getDamage();
        moveTowards(this.target);
        generateImage();
    }
    @Override
    protected BufferedImage createImage() {
        BufferedImage img = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0, 0, img.getWidth(), img.getHeight());
        return img;
    }

    private void moveTowards(Enemy target){
        if (!active){
            return;
        }
        double dx = target.getCenterPosition().getX() - position.getX();
        double dy = target.getCenterPosition().getY() - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            position.setPosition(position.getX() + dx / distance * speed,
                    position.getY() + dy / distance * speed);
        } else {
            position.setPosition(target.getCenterPosition().getX(), target.getCenterPosition().getY());
            active = false;
            target.bulletHit(this);
        }
    }

    public void update() {
        moveTowards(target);
    }

    public boolean isActive() {
        return active;
    }

    public double getDamage() {
        return damage;
    }
}
