package BattleField.Tiles;

import BattleField.BattleField;
import entities.towers.Tower;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TowerTile extends Tile{
    private Tower tower;
    private BufferedImage defaultImage;
    private BufferedImage purchaseModeImage;
    public TowerTile(int x, int y, BattleField field) {
        super(x, y, field);
        generateImage();
    }

    public Tower getTower() {
        return tower;
    }
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void setDefaultImage(){
        image = defaultImage;
    }
    public void setPurchaseModeImage(){
        image = purchaseModeImage;
    }
    @Override
    protected BufferedImage createImage() {
        defaultImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = defaultImage.getGraphics();
        g.setColor(new Color(66, 3, 78));
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();
        purchaseModeImage = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g = purchaseModeImage.getGraphics();
        g.setColor(new Color(10, 209, 1));
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();
        return defaultImage;
    }

    public void removeTower(){
        tower = null;
    }

}
