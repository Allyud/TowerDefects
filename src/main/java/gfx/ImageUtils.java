package gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static Image loadImage(String filePath) {
        try (InputStream input = ImageUtils.class.getClassLoader().getResourceAsStream(filePath.substring(1))){

            if (input == null) {
                System.err.println("Image not found: " + filePath);
                return null;
            }
            return ImageIO.read(input);
            //return ImageIO.read(new File(ImageUtils.class.getResource("") + filePath));
        }
        catch(IOException e){
            System.out.println("Error loading image " + ImageUtils.class.getResource("") + filePath);
        }

        return null;
    }
}
