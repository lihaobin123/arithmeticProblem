import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        int a = 10;
        for (int i = 0; i < 20; i++) {
            System.out.println(random.nextInt(a-1)+1);
        }

    }
}
