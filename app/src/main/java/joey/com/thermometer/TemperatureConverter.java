package joey.com.thermometer;

/**
 * TemperatureConverter with two native functions
 * convert(): convert an array of float numbers (Up to 256 numbers)
 * convertSingle(): convert a single float number
 */
public class TemperatureConverter {

    /**
     * convert an array of floats
     * @param arr input arr
     * @param isCTof true if we are convert Celsius to Fahrenheit, false if we perform the opposite action
     * @return converted array or null if something bad occurs
     */
    public native static float[] convert(float[] arr, boolean isCTof);

    /**
     * convert a single float
     * @param input input float
     * @param isCTof true if we are convert Celsius to Fahrenheit, false if we perform the opposite action
     * @return converted float
     */
    public native static float convertSingle(float input, boolean isCTof);

}
