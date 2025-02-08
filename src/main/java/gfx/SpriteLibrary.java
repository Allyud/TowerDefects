package gfx;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final static String PATH_TO_UNITS = "/sprites/units";
    private Map<String, SpriteSet> units;

    public SpriteLibrary() {
        units = new HashMap<String, SpriteSet>();
        loadSpritesFromDisk();
    }

    private void loadSpritesFromDisk() {
        String[] folderNames = getFolderNames(PATH_TO_UNITS);

        for (String folderName : folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = PATH_TO_UNITS + '/' + folderName;
            String[] sheetsInFolder = getSheetsInFolder(pathToFolder);
            for (String sheetName : sheetsInFolder) {
                spriteSet.addAnimationSheet(
                        sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + '/' + sheetName));

            }
            units.put(folderName, spriteSet);
        }


    }

    private String[] getSheetsInFolder(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);;
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }
    private String[] getFolderNames(String basePath)  {
        URL resource = SpriteLibrary.class.getResource("/sprites/units");
        try{
        File file = new File(resource.toURI());
            return file.list((current, name) -> new File(current, name).isDirectory());}
        catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SpriteSet getUnit(String name) {
        return units.get(name);
    }
}
