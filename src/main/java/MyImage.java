import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MyImage {
    private String path = "problems.bmp";
    private BufferedImage img;

    public MyImage(String path) throws IOException {
        this.path = path;
        img = ImageIO.read(new File(path));
    }


    public int[][][] getrawPixels() {
        int w = img.getWidth();
        int h = img.getHeight();
        int[][][] pixels = new int[w][h][3];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int pixel = img.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                pixels[i][j][0] = red;
                pixels[i][j][1] = blue;
                pixels[i][j][2] = green;
            }
        }
        return pixels;
    }

    public void save(String path) throws IOException {
        ImageIO.write(img, "png", new File(path));
    }

    public BufferedImage encode(String msg) {
        int w = img.getWidth();
        int h = img.getHeight();
        int count = 0;
        byte[] encoding_array = msg.getBytes();
        BufferedImage encodedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        encodedImg.setRGB(0, 0, msg.length());
        int symbol_index = 0;
        for (int i = 1; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int pixel = img.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                int lambda = (pixel >> 24) & 0xff;
                if (symbol_index < msg.length()) {
                    byte a = encoding_array[symbol_index];
                    byte encoding_for_red = (byte) (a >> 5);
                    byte encoding_for_green = (byte) ((byte) (a >>> 2) & 7);
                    byte encoding_for_blue = (byte) (a & 3);
                    red = (encoding_for_red | (red & 120));
                    green = (encoding_for_green | (green & 120));
                    blue = (encoding_for_blue | (blue & 124));
                    symbol_index++;
                }
                int p = (lambda << 24) | (red << 16) | (green << 8) | blue;
                if (symbol_index <= msg.length()) {
                    System.out.println();
                    System.out.println(Integer.toBinaryString(pixel));
                    System.out.println(Integer.toBinaryString(p));
                    System.out.println();
                }
                encodedImg.setRGB(i, j, p);

            }
        }
        try {
            ImageIO.write(encodedImg, "bmp", new File("encoded.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedImg;
    }


    public String  decode(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            byte len = (byte) image.getRGB(0,0);
            int h = image.getHeight();
            int w = image.getWidth();
            StringBuffer msg = new StringBuffer();
            int count = 0;
            for (int i = 1; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int pixel = img.getRGB(i, j);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    byte part1 = (byte) (red & 7);
                    byte part2 = (byte) (green & 7);
                    byte part3 = (byte) (blue & 3);
                    System.out.println(Integer.toBinaryString(part1));
                    System.out.println(Integer.toBinaryString(part2));
                    System.out.println(Integer.toBinaryString(part3));
                    byte p = (byte) ((byte)(part1 << 4) + (part2 << 2) + (part3));
                    System.out.println("integers:");
                    System.out.println(part1 <<4);
                    System.out.println((part2 <<2));
                    System.out.println(part3);
                    System.out.println(p);
                    System.out.println((char) p);
                    msg.append((char) p);
                    count++;
                    System.out.println(len);
                    System.out.println(count);
                    if (count == len){
                        System.out.println(msg);
                        return msg.toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


        public static void main (String[]args){
            try {
                MyImage image = new MyImage("problems.bmp");
                System.out.println(image.decode("encoded.bmp"));
                System.out.println(Arrays.toString("ﾒﾷEﾳ ﾄﾩ￵".getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
