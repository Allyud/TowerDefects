package game;

import BattleField.BattleField;
import display.Display;
import gfx.SpriteLibrary;
import input.MouseInput;

public class Game {
    private static Game instance;
    public Display display;
    private BattleField battleField;
    private SpriteLibrary spriteLibrary;
    private MouseInput mouseInput;
    private Game(int width, int height) {
        display = new Display(width, height);

        mouseInput = new MouseInput();
        display.getCanvas().addMouseListener(mouseInput);
        display.getCanvas().addMouseMotionListener(mouseInput);
        battleField = new BattleField(14, 10,
        new int[][]{
                {2, 0, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 1, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2},
                {2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 0, 0, 0, 2, 1, 2, 2, 2, 2},
                {2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2}},
        new int[][]{ {0,1},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7}, {1,8},{1,9},{1,10},{1,11},{1,12},{2,12},{2,11},{1,11},{1,12},{2,12},{2,11},{3,11},{3,10},{3,9},{4,9},{4,8},{4,7},{5,7},{5,6},{5,5},{6,5},{6,4},{6,3},{7,3},{7,2},{7,1}, {8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8},{8,9},{8,10},{8,11},{8,12},{9,12} }, 50);

        spriteLibrary = new SpriteLibrary();

        update();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(800, 600);
        }
        return instance;
    }
    public void update() {
        if (mouseInput.isClicked()) {
            battleField.handleMouseClick(mouseInput.getMouseX(), mouseInput.getMouseY());
        }
        battleField.update();
    }

    public void render() {
        display.render(this);
    }

    public BattleField getBattleField() {
        return battleField;
    }

}
