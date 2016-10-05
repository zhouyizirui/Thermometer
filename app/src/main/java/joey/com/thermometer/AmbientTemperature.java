package joey.com.thermometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

/**
 * A helper to acquire ambient temperature
 * Need to work on phones with temperature sensor
 */
public class AmbientTemperature {
    /** Context */
    private Context mContext;
    /** SensorManager */
    private SensorManager mSensorManager;
    /** Sensor */
    private Sensor mTempSensor;
    /** SensorEventListener */
    private SensorEventListener mTempListener;
    /** Interface to pass result to outside */
    private IAmbientTemperatureListener mAmbientListener;

    /**
     * Constructor with one parameter
     * @param context context
     */
    public AmbientTemperature(Context context) {
        mContext = context;
        init();
    }

    /**
     * Init the sensor and listeners
     */
    private void init() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : deviceSensors) {
            Log.i("sensor", "------------------");
            Log.i("sensor", sensor.getName());
            Log.i("sensor", sensor.getVendor());
            Log.i("sensor", Integer.toString(sensor.getType()));
            Log.i("sensor", "------------------");
        }
        mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mTempListener = new TemperatureListener();
        mSensorManager.registerListener(mTempListener, mTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Set the outside listener
     * @param listener listener
     */
    public void setListener(IAmbientTemperatureListener listener) {
        mAmbientListener = listener;
    }

    /**
     * Release the listener in case of memory leak
     */
    public void release() {
        mSensorManager.unregisterListener(mTempListener);
    }

    /**
     * TemperatureListener to acquire the temperature in real time
     */
    private class TemperatureListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float temperatureValue = event.values[0];
            if (mAmbientListener != null) {
                mAmbientListener.onTemperatureChanged(temperatureValue);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }

}
