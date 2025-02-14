package core;

public class Size {
    private int width;
    private int height;
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public Size(double width, double height) {
        this((int) width, (int) height);
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
