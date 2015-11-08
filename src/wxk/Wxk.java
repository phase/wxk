package wxk;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * Interpreter for the Wxk (wiks) programming language
 * @author phase
 */
public class Wxk {
    static Color[] program;

    public static void main(String[] args) throws IOException {
        BufferedImage img;
        img = ImageIO.read(new File("res/test.png"));
        program = new Color[img.getHeight() * img.getWidth()];
        int count = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = getColor(img, x, y);
                program[count++] = c;
            }
        }
        parseColors();
    }

    public static Color getColor(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);
        return new Color((argb >> 16) & 0xff, (argb >> 8) & 0xff, (argb) & 0xff);
    }

    /**
     * The 'letter' of a color is used when printing the color to the screen, or
     * storing it as a string. It is designated by the 'red' value of the color.
     * 
     * @param c
     *            Source color
     * @return Letter extracted from color
     */
    public static char getLetterFromColor(Color c) {
        return (char) c.getRed();
    }

    public static String getStringFromColors(Color[] colors) {
        String f = "";
        for (Color c : colors)
            f += getLetterFromColor(c);
        return f;
    }

    public static void parseColors() {
        for (Color c : program) {
            parse(c);
        }
    }

    public static void parse(Color c) {
        
    }
}