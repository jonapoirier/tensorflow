package org.tensorflow.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.tensorflow.demo.env.Logger;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Go to the {@link CameraActivity}.
     */
    public void scanObject(View view) {
        // Go to the Camera Activity
        LOGGER.i("Opening Classifier Activity");

        Intent intentClassifierActivity = new Intent(view.getContext(), ClassifierActivity.class);
        view.getContext().startActivity(intentClassifierActivity);
    }
}
