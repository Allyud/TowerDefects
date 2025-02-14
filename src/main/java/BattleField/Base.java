package BattleField;

import core.Position;
import core.RenderableObject;
import core.Size;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Base extends RenderableObject {
    private int gridX, gridY;
    int health = 20;
    BattleField battleField;
    public Base(BattleField battleField) {
        Size size = new Size(battleField.getTileSize()*1.5, battleField.getTileSize()*1.5);
        super(new Position(battleField.getTileSize()*(battleField.getPath()[battleField.getPathLength()-1][1]-0.25),battleField.getTileSize()*battleField.getPath()[battleField.getPathLength()-1][0]+20), size );
        this.battleField = battleField;
        generateImage();
    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(108, 12, 12));
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();
        return image;
    }

    public void recieveDamage(int damage) {
        health -= damage;
        if (health <= 0){
            battleField.baseDefeated();
        }
    }
    public int getHP() {
        return health;
    }
}
