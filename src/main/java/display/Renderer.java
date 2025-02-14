package display;

import BattleField.BattleField;
import game.Game;

import java.awt.*;

public class Renderer {
    public void render(Game game, Graphics graphics) {
        BattleField field = game.getBattleField();
        field.render();
        graphics.drawImage(field.getImage(), (int) field.getPosition().getX(), (int)  field.getPosition().getY(), null );
    }
}
