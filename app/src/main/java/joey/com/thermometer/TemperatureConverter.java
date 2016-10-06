package joey.com.thermometer;

/**
 * TemperatureConverter with two native functions
 * convert(): convert an array of float numbers (five limited)
 * convertSingle(): convert a single float number
 */
public class TemperatureConverter {

    public native static float[] convert(float[] arr, boolean isCTof);

    public native static float convertSingle(float input, boolean isCTof);

}
