import core.GlobalConstants;
import game.Game;
import game.GameLoop;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //display.Display display = new display.Display(800,600);
        GlobalConstants.displayHeight = 900;
        GlobalConstants.displayWidth = 1200;

        new Thread(new GameLoop(Game.getInstance())).start();
        //GamePanel gamePanel = new GamePanel();
        //window.add(gamePanel);

        //gamePanel.startGameThread();
    }
}