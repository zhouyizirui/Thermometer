package joey.com.thermometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

public class AmbientTemperature {

    private Context mContext;

    private SensorManager mSensorManager;

    private Sensor mTempSensor;

    private SensorEventListener mTempListener;

    private IAmbientTemperatureListener mAmbientListener;

    public AmbientTemperature(Context context) {
        mContext = context;
        init();
    }

    public void setListener(IAmbientTemperatureListener listener) {
        mAmbientListener = listener;
    }

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

    public void release() {
        mSensorManager.unregisterListener(mTempListener);
    }

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
