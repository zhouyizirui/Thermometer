package joey.com.thermometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mAmbientTemperatureValue;

    private AmbientTemperature mAmbientTemperatureManager;

    private WeekTemperatureLayout mWeekLayout;

    private Button mConvert;

    private float mCurrentTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAmbientTemperatureManager.release();
    }

    private void initViews() {
        mAmbientTemperatureValue = (TextView) findViewById(R.id.ambient_temperature);
        mAmbientTemperatureManager = new AmbientTemperature(this);
        mWeekLayout = (WeekTemperatureLayout) findViewById(R.id.week_temperature);
        mWeekLayout.setViews(Constants.days, RandomGenerator.generateDatas(5));
        mConvert = (Button) findViewById(R.id.convert_temperature);


        mConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekLayout.convertTemperatures();
                if (mWeekLayout.getIsCelsius()) {
                    mConvert.setText("Celsius");
                } else {
                    mConvert.setText("Fahrenheit");
                }
            }
        });
        mAmbientTemperatureManager.setListener(new IAmbientTemperatureListener() {
            @Override
            public void onTemperatureChanged(float temperature) {
                mCurrentTemperature = temperature;
                mAmbientTemperatureValue.setText(mCurrentTemperature + Constants.CELSIUSSUFFIX);
            }
        });
    }

    static {
        System.loadLibrary("convert");
    }
}
