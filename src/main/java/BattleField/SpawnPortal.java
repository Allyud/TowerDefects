package BattleField;

import core.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;

public class SpawnPortal extends RenderableObject {
    int gridX, gridY;
    double dirAngle;
    static final int[][] neighborsList = {{0,1},{1,0},{0,-1},{-1,0}};
    byte direction;
    public SpawnPortal(BattleField battleField) {
        super(new Position(0,0), new Size(0,0));
        int[][] path = battleField.getPath();
        int yDIF = path[0][0] - path[1][0];
        int xDIF = path[0][1] - path[1][1];
        if (xDIF == -1 && yDIF == 0){
            direction = 0;
        }
        else if (xDIF == 0 && yDIF == 1){
            direction = 1;
        }
        else if (xDIF == 1 && yDIF == 0){
            direction = 2;
        }
        else if (xDIF == 0 && yDIF == -1){
            direction = 3;
        }
        else{
            throw new IllegalArgumentException("Invalid Path");
        }
        int tileSize = battleField.getTileSize();
        size = new Size(tileSize/5 + tileSize*4/5*abs(yDIF), tileSize/5 + tileSize*4/5*abs(xDIF) );
        double xFac = (1-1.0*size.getHeight()/tileSize) *((yDIF+1)/2);
        double yFac = (1-1.0*size.getWidth()/tileSize) *((xDIF+1)/2);

        position.setPosition((int) ((path[0][1] + yFac)*tileSize),(int) ((path[0][0] + xFac)*tileSize));

        /*System.out.print(xDIF);
        System.out.println(yDIF);
        System.out.println(xFac);
        System.out.println(yFac);
        System.out.println(size.getWidth() + " " + size.getHeight());
        System.out.println(position.getX() + " " + position.getY());
        System.out.println(path[0][0] + " " + path[0][1]);


         */
        System.out.println(direction);
        generateImage();
    }

    @Override
    protected BufferedImage createImage() {
        BufferedImage image = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        g.dispose();

        return image;
    }

    public byte getDirection() {
        return direction;
    }

}
