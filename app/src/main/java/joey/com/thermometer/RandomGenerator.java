package joey.com.thermometer;


import java.util.ArrayList;
import java.util.Random;

/**
 * RandomGenerator to generate random temperatures
 */
public class RandomGenerator {
    /** Random generator */
    private static Random random = new Random();
    /** Generated data list */
    private static ArrayList<Float> dataList = new ArrayList<>();
    /** Min of temperature in CELSIUS */
    public static final float MIN = -40f;
    /** Max of temperature in CELSIUS */
    public static final float MAX = 50f;

    /**
     * Generate random temperatures
     * @param n number of temperatures
     * @return result
     */
    public static ArrayList<Float> generateDatas(int n) {
        dataList.clear();
        for (int i = 0; i < n; i++) {
            float candidate = random.nextFloat() * (MAX - MIN) + MIN;
            candidate = roundFloat(candidate);
            dataList.add(candidate);
        }
        return dataList;
    }

    /**
     * Round a float
     * Only one digit after the point left
     * @param input number
     * @return result
     */
    public static float roundFloat(float input) {
        return (float) Math.floor((double) (input * 10)) / 10;
    }

    /**
     * Round a float array
     * @param input float array
     */
    public static void roundFloats(float[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = (float) Math.floor((double) (input[i] * 10)) / 10;
        }
    }

}
