package wxk;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

/**
 * Interpreter for the Wxk (wiks) programming language
 * 
 * @author phase
 */
public class Wxk {
    private Color[] program;
    private Stack stack;

    public static void main(String[] args) throws IOException {
        new Wxk();
    }

    private Wxk() throws IOException {
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
        stack = new Stack(1024);
        parseColors();
    }

    public Color getColor(BufferedImage img, int x, int y) {
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
    public char getLetterFromColor(Color c) {
        return (char) c.getRed();
    }

    public String getStringFromColors(Color[] colors) {
        String f = "";
        for (Color c : colors)
            f += getLetterFromColor(c);
        return f;
    }

    public void parseColors() {
        for (Color c : program) {
            parse(c);
        }
    }

    // flags
    boolean string = false;
    StringBuilder stringBuffer;

    public void parse(Color c) {
        //System.out.println(c);
        if (string && c.getBlue() != 1) stringBuffer.append(getLetterFromColor(c));
        switch (c.getBlue()) {
        case 10:
            if (!string) {
                stringBuffer = new StringBuilder();
                string = true;
            }
            else if (string) {
                string = false;
                stack.push(stringBuffer.toString());
            }
            break;
        case 140:
            System.out.println(stack.pop().toString());
            break;
        default:
            break;
        }
    }

    class Stack {
        Object[] stack;
        int i = -1;

        public Stack(int size) {
            stack = new Object[size];
        }

        public void push(Object x) {
            if (i >= stack.length - 1)
                throw new ArrayIndexOutOfBoundsException("Can't push to full stack! " + x.toString());
            stack[++i] = x;
        }

        public Object pop() {
            if (i <= -1) throw new ArrayIndexOutOfBoundsException("Can't pop from empty stack!");
            Object x = stack[i];
            stack[i] = null;
            i--;
            return x;
        }

        public Object peek() {
            return stack[i];
        }

        public void reverse() {
            for (int left = 0, right = i; left < right; left++, right--) {
                Object x = stack[left];
                stack[left] = stack[right];
                stack[right] = x;
            }
        }

        public double length() {
            return i + 1d;
        }

        public String toString() {
            return Arrays.asList(stack).toString().replace(", null", "");
        }
    }
}