package display;

import javax.swing.*;
import java.awt.*;

import game.Game;

import java.awt.image.BufferStrategy;

public class Display extends JFrame{
    private Canvas canvas;
    private Renderer renderer;
    public Display(int width, int height){
        setTitle("Tower Defects");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));

        canvas.setFocusable(false);
        add(canvas);
        pack();


        canvas.createBufferStrategy(4);
        setLocationRelativeTo(null);
        setVisible(true);
        renderer = new Renderer();
    }

    public void render(Game game){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics g = bufferStrategy.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderer.render(game, g);

        g.dispose();
        bufferStrategy.show();
    }

    public Canvas getCanvas(){
        return canvas;
    }
}
