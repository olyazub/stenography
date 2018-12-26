import java.lang.reflect.Array;
import java.util.Arrays;

public class Testing {

    public static void main(String[] args) {
        String s = "Zlesyaz - is , a ";
        byte [] x = s.getBytes();
        System.out.println(Arrays.toString(x));
//        for (byte el: x) {
//            System.out.println(Integer.toBinaryString(el));
//        }
    byte a = (byte) 41;
    byte b = (byte) ((a >> 2) & 7); // get only last 3
    byte c = (byte) 69;
    byte d = (byte) 120; // change last  3 to 0
    System.out.println(Integer.toBinaryString(c));
    System.out.println("a:");
    System.out.println(Integer.toBinaryString(a));
    byte encoding_for_red = (byte) ((byte) a >> 5);
    byte encoding_for_green = (byte) ((byte) (a >>> 2) & 7);
    byte encoding_for_blue = (byte) (a  & 7);
//    System.out.println(encoding_for_red); // first 3
//    System.out.println(encoding_for_green); // second 3
//    System.out.println(encoding_for_blue);
    System.out.println("changed c:");
    System.out.println(Integer.toBinaryString((a | (c & 120))));

    }
}
