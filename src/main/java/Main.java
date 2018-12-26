import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("problems.bmp"));
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage grayImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            int[][] GrayScaleImg = new int[w][h];
            System.out.println(w + " " + h);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int pixel = img.getRGB(i, j);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    System.out.println(red + " " + green + " " + blue);
                    int avg = (int) (0.2126 * red + 0.7152 + 0.0722 * blue);
                    GrayScaleImg[i][j] = avg;
                    int p = (avg << 24) | (avg << 16) | (avg << 8) | avg;
                    System.out.println(p);
                    grayImg.setRGB(i, j, p);
                }
            }
                ImageIO.write(grayImg, "bmp", new File("output.bmp"));
        } catch (IOException e) {
        }
    }
}
