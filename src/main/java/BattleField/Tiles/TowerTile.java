package BattleField.Tiles;

import BattleField.BattleField;
import entities.towers.Tower;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TowerTile extends Tile{
    private Tower tower;
    public TowerTile(int x, int y, BattleField field) {
        super(x, y, field);
        baseColor = new Color(183, 255, 100);
        generateImage();
    }

    public Tower getTower() {
        return tower;
    }
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(baseColor);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();
        return image;
    }
}
