package joey.com.thermometer;


import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator {

    private static Random random = new Random();

    private static ArrayList<Float> dataList = new ArrayList<>();

    public static final float MIN = -40f;

    public static final float MAX = 50f;

    public static ArrayList<Float> generateDatas(int n) {
        dataList.clear();
        for (int i = 0; i < n; i++) {
            float candidate = random.nextFloat() * (MAX - MIN) + MIN;
            candidate = roundFloat(candidate);
            dataList.add(candidate);
        }
        return dataList;
    }

    public static float roundFloat(float input) {
        return (float) Math.floor((double) (input * 10)) / 10;
    }

    public static void roundFloats(float[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = (float) Math.floor((double) (input[i] * 10)) / 10;
        }
    }

}
