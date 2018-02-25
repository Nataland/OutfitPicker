package hackthevalley.outfitpicker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;

/**
 * Created by natalie on 2018-02-24.
 */
public class MyAndroidAppActivity extends Activity {

    private Spinner spinner1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addListenerOnSpinnerItemSelection();
    }
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }
}
