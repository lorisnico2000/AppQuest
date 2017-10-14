package ch.appquest.bict.appquest;

import android.app.Fragment;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by loris on 13.10.2017.
 */

public class Metalldetektor extends Fragment{

    View myView;

    SensorManager sm;
    Sensor s;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.metalldetektor_layout, container, false);
        return myView;
    }
}
