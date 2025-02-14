package core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class RenderableObject {

    protected Position position;
    protected Position centerPosition;
    protected Size size;
    protected Color baseColor;
    protected BufferedImage image;

    public RenderableObject(Position position, Size size) {
        this.size = size;
        this.position = position;
    }

    protected abstract BufferedImage createImage();
    public void generateImage() {
        image = createImage();
    }
    public BufferedImage getImage() {
        return image;
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public Position getCenterPosition() {
        centerPosition = new Position(position.getX()+ size.getWidth()/2, position.getY() + size.getHeight()/2);
        return centerPosition;
    }
    public Size getSize() {
        return size;
    }
    public void setSize(Size size) {
        this.size = size;
    }
}
