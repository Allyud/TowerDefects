package gfx;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SpriteSet {

    private Map<String, Image> animationSheets;

    public SpriteSet() {
        animationSheets = new HashMap<String, Image>();
    }

    public void addAnimationSheet(String sheetName, Image image) {
        animationSheets.put(sheetName, image);
    }
    public Image getAnimationSheet(String sheetName) {
        return animationSheets.get(sheetName);
    }
}
