package joey.com.thermometer;

/**
 * An interface to pass the temperature from sensor to the view in real time
 */
public interface IAmbientTemperatureListener {

    void onTemperatureChanged(float temperature);
    
}
