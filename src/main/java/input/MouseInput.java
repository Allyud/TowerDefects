package input;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class MouseInput implements MouseListener, MouseMotionListener {
    private int mouseX, mouseY;
    private boolean isClicked = false;

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        //isClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("Mouse pressed at: " + e.getX() + ", " + e.getY());
        isClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("Mouse released at: " + e.getX() + ", " + e.getY());
        isClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isClicked = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("Mouse dragged at: " + e.getX() + ", " + e.getY());
    }

    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }
    public boolean isClicked() { return isClicked; }
}
