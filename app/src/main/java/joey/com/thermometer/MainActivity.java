package joey.com.thermometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The MainActivity to display temperatures
 */
public class MainActivity extends AppCompatActivity {
    /** TextView to show the current temperature */
    private TextView mAmbientTemperatureValue;
    /** AmbientTemperature manager */
    private AmbientTemperature mAmbientTemperatureManager;
    /** Bottom layout */
    private WeekTemperatureLayout mWeekLayout;
    /** Button */
    private Button mConvert;
    /** Current default temperature */
    private float mCurrentTemperature = 25.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAmbientTemperatureManager.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAmbientTemperatureManager.release();
    }

    /**
     * Init the views in activity
     */
    private void initViews() {
        // Change the number here to generate more days
        int numOfDays = 5;

        mAmbientTemperatureValue = (TextView) findViewById(R.id.ambient_temperature);
        mAmbientTemperatureManager = new AmbientTemperature(this);
        mWeekLayout = (WeekTemperatureLayout) findViewById(R.id.week_temperature);
        mWeekLayout.setViews(RandomGenerator.generateDates(numOfDays), RandomGenerator.generateDatas(numOfDays));
        mConvert = (Button) findViewById(R.id.convert_temperature);


        mConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekLayout.convertTemperatures();
                setAmbientTemperature();
                if (mWeekLayout.getIsCelsius()) {
                    mConvert.setText(R.string.celsius);
                } else {
                    mConvert.setText(R.string.fahrenheit);
                }
            }
        });
        mAmbientTemperatureManager.setListener(new IAmbientTemperatureListener() {
            @Override
            public void onTemperatureChanged(float temperature) {
                mCurrentTemperature = temperature;
                setAmbientTemperature();
            }
        });

        setAmbientTemperature();
    }

    /**
     * Set the ambient temperature value
     */
    private void setAmbientTemperature() {
        if (mWeekLayout.getIsCelsius()) {
            mAmbientTemperatureValue.setText(mCurrentTemperature + Constants.CELSIUSSUFFIX);
        } else {
            mAmbientTemperatureValue.setText(RandomGenerator.roundFloat(TemperatureConverter
                    .convertSingle(mCurrentTemperature, true))
                    + Constants.FAHRENHEITSUFFIX);
        }
    }

    static {
        System.loadLibrary("convert");
    }
}
