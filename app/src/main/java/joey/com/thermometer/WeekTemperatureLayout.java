package joey.com.thermometer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A horizontal linearlayout to hold temperatures of the week
 */
public class WeekTemperatureLayout extends LinearLayout {
    /** Views to hold and manage */
    private List<ViewHolder> mViews = new ArrayList<>();
    /** Random temperatures */
    private float[] mTemperatures;
    /** Is in celsius mode */
    private boolean mIsCelsius;
    /** Context */
    private Context mContext;

    /**
     * Constructor with one parameter
     * @param context context
     */
    public WeekTemperatureLayout(Context context) {
        super(context);
        init(context);
    }

    /**
     * Constructor with two parameters
     * @param context context
     * @param attrs attrs
     */
    public WeekTemperatureLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Constructor with three parameters
     * @param context context
     * @param attrs attrs
     * @param defStyleAttr defStyleAttr
     */
    public WeekTemperatureLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Init the layout
     * @param context
     */
    private void init(Context context) {
        mContext = context;
        mIsCelsius = true;
        setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * Set the days and the temperatures to the views
     * @param days days
     * @param tems tems
     */
    public void setViews(List<String> days, List<Float> tems) {
        if (days.size() != tems.size()) {
            throw new IllegalArgumentException("Size of days and tems should be the same");
        }
        int length = days.size();
        mTemperatures = new float[length];
        for (int i = 0; i < length; i++) {
            LinearLayout dayTem = new LinearLayout(mContext);
            dayTem.setOrientation(VERTICAL);
            TextView dayView = new TextView(mContext);
            dayView.setText(days.get(i));
            dayView.setTextColor(Color.parseColor("#333333"));
            dayView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView temView = new TextView(mContext);
            temView.setText(tems.get(i) + Constants.CELSIUSSUFFIX);
            temView.setTextColor(Color.parseColor("#333333"));
            temView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            mTemperatures[i] = tems.get(i);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.day = dayView;
            viewHolder.tem = temView;
            mViews.add(viewHolder);

            LayoutParams dayLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dayLayout.gravity = Gravity.CENTER_HORIZONTAL;
            dayTem.addView(dayView, dayLayout);
            LayoutParams temLayout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            temLayout.gravity = Gravity.CENTER_HORIZONTAL;
            dayTem.addView(temView, temLayout);

            LayoutParams dayTemLayout = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            dayTemLayout.weight = 1;
            dayTemLayout.gravity = Gravity.CENTER_VERTICAL;
            addView(dayTem, dayTemLayout);
        }
    }

    /**
     * Convert temperature function
     * call this method to convert from Celsius to Fahrenheit, vice versa.
     */
    public void convertTemperatures() {
        mTemperatures = TemperatureConverter.convert(mTemperatures, mIsCelsius);
        if (mTemperatures != null) {
            RandomGenerator.roundFloats(mTemperatures);
            mIsCelsius = !mIsCelsius;
            notifyDataChanged();
        }
    }

    /**
     * Change the corresponding views according the temperature conversion
     */
    private void notifyDataChanged() {
        String suffix = mIsCelsius ? Constants.CELSIUSSUFFIX : Constants.FAHRENHEITSUFFIX;
        for (int i = 0; i < mTemperatures.length; i++) {
            mViews.get(i).tem.setText(mTemperatures[i] + suffix);
        }
    }

    /**
     * Get if is current in Celsius display mode
     * @return mIsCelsius
     */
    public boolean getIsCelsius() {
        return mIsCelsius;
    }

    /**
     * ViewHolder to hold the day and temperature
     */
    class ViewHolder {
        TextView day;
        TextView tem;
    }
}
