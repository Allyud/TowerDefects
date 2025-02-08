package BattleField.Tiles;

import BattleField.BattleField;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PathTile extends Tile {

    public PathTile(int x, int y, BattleField field) {
        super(x, y, field);
        baseColor = Color.YELLOW;
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
