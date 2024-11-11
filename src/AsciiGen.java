import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AsciiGen {
    String asciiChars;
    StringBuilder asciiArt = new StringBuilder();
    boolean inverted;
    int buffer;
    String filePath;

    public AsciiGen(String asciiChars, boolean inverted, int buffer, String filePath) {
        this.asciiChars = asciiChars;
        this.inverted = inverted;
        this.buffer = buffer;
        this.filePath = filePath;
    }

    public static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public Color[][] getRGBs () throws IOException {
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);



        int width = image.getWidth();
        int height = image.getHeight();

        Color[][] ans = new Color[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                ans[y][x] = new Color (r, g, b);

            }
        }
        return ans;
    }

    public String[][] makeArtMonochromic() throws IOException {
        if (inverted) {
            asciiChars = reverseString(asciiChars);
        }
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);



        int width = image.getWidth();
        int height = image.getHeight();
        int[][] brightness = new int[height][width];
        String[][] ans = new String[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                brightness[y][x] = (int) (0.3 * r + 0.6 * g + 0.1 * b);

            }
        }

        for (int y = 0; y < height; y += buffer * 2) {
            for (int x = 0; x < width; x += buffer) {

                int index = (brightness[y][x] * (asciiChars.length() - 1)) / 255;
                asciiArt.append(asciiChars.charAt(index));
                ans[y][x] = String.valueOf(asciiChars.charAt(index));
            }
            asciiArt.append("\n");
        }
        System.out.println(asciiArt);
        return ans;
    }


    public void makeArtMultichromic() throws IOException {
        if (inverted) {
            asciiChars = reverseString(asciiChars);
        }
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();
        int[][] brightness = new int[height][width];

        for (int y = 0; y < height; y += buffer * 2) {
            for (int x = 0; x < width; x += buffer) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                brightness[y][x] = (int) (0.3 * r + 0.6 * g + 0.1 * b);
                int index = (brightness[y][x] * (asciiChars.length() - 1)) / 255;
                System.out.print("\u001B[38;2;" + r + ";" + g + ";" + b + "m" + asciiChars.charAt(index));
            }
            System.out.println();
        }

    }

}
