package BattleField.Tiles;

import BattleField.BattleField;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WallTile extends Tile {

    public WallTile(int x, int y, BattleField field, int type) {
        super(x, y, field);
        baseColor = Color.GREEN;
        generateImage();
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
