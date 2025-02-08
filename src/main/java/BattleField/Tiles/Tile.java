package BattleField.Tiles;

import BattleField.BattleField;
import core.RenderableObject;
import core.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends RenderableObject {
    //BattleField field;
    int gridX;
    int gridY;

    public Tile(int x, int y, BattleField field) {
        super(new Position(x* field.getTileSize(), y* field.getTileSize()), new Size(field.getTileSize(), field.getTileSize()));
        gridX = x;
        gridY = y;
        size = new Size(field.getTileSize(),field.getTileSize());
        generateImage();
        //position = new Position(gridX* size.getWidth(),gridY* size.getHeight());
        //this.field = field;
    }



}
