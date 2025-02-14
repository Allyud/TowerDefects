package display;

import javax.swing.*;
import java.awt.*;

import display.ui.LeftPanelUI;
import display.ui.ShopUI;
import game.Game;

import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class Display extends JFrame{
    private Canvas canvas;
    private Renderer renderer;
    private ShopUI shopUI;
    private LeftPanelUI leftPanelUI;
    public Display(int width, int height){
        setTitle("Tower Defects");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel canvasPanel = new JPanel(new BorderLayout());
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        setLayout(new BorderLayout());

        leftPanelUI = new LeftPanelUI();
        mainPanel.add(leftPanelUI, BorderLayout.WEST);
        shopUI = new ShopUI();

        canvasPanel.add(shopUI, BorderLayout.SOUTH);
        canvasPanel.add(canvas, BorderLayout.CENTER);

        mainPanel.add(canvasPanel, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        canvas.createBufferStrategy(4);
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
    public ShopUI getShopUI() {
        return shopUI;
    }

    public LeftPanelUI getLeftPanelUI() {
        return leftPanelUI;
    }
    public Canvas getCanvas(){
        return canvas;
    }
}
