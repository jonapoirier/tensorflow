package org.tensorflow.demo;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.tensorflow.demo.env.Logger;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Styles programmatically !

        // We change the color of the action bar
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1666bf")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>OMNI</font>"));

        // We change the color of the status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0e3e75"));

        // Color button
        Button buttonScan = (Button) findViewById(R.id.button_scan);
        buttonScan.getBackground().setColorFilter(0xFF1666BF, PorterDuff.Mode.MULTIPLY);

        TextView textViewOMNI = (TextView) findViewById(R.id.textViewOMNI);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/army.ttf");
        textViewOMNI.setTypeface(typeface);

        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, -50); // llp.setMargins(left, top, right, bottom);
        textViewOMNI.setLayoutParams(llp);

        TextView textViewOMNISub = (TextView) findViewById(R.id.tewtViewOMNISub);
        textViewOMNISub.setText(Html.fromHtml("<b>O</b>bjets <b>M</b>ilitaires <b>N</b>on <b>I</b>dentifiés"));

        TextView tewtViewOMNILegende = (TextView) findViewById(R.id.tewtViewOMNILegende);
        tewtViewOMNILegende.setText(Html.fromHtml("Bienvenue !<br />Appuyer sur le bouton pour démarrer l'identification"));


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
