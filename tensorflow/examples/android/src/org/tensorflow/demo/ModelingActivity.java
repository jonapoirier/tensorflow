package org.tensorflow.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.tensorflow.demo.env.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModelingActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();

    public static List<Bitmap> listCroppedBitmap = new ArrayList<>();
    List<ItemClassifier> results = new ArrayList<>();

    private Classifier classifier;
    ProgressBar spinner = null;

    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/optimized_graph_02102017.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/retrained_labels_02102017.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeling);

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

        spinner = (ProgressBar) findViewById(R.id.loadingSpinner);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#1666bf"), PorterDuff.Mode.SRC_IN);

        classifier =
                TensorFlowImageClassifier.create(
                        getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME);

        new LongOperation().execute("");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private class LongOperation extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            for (Bitmap bitmap: listCroppedBitmap) {
                for (final Classifier.Recognition result : classifier.recognizeImage(bitmap)) {
                    results.add(new ItemClassifier(result.getTitle(), null, result.getConfidence(), null, null));
                    bitmap.recycle();
                }
            }

            ClassifierActivity.nbImagesStored = 0;
            listCroppedBitmap.clear();

            return "executed";
        }

        @Override
        protected void onPostExecute(String s) {

            ProgressBar spinner = (ProgressBar) findViewById(R.id.loadingSpinner);

            Intent itemsIntent = new Intent(spinner.getContext(), ItemsActivity.class);
            itemsIntent.putExtra("results", (ArrayList<ItemClassifier>) results);
            spinner.getContext().startActivity(itemsIntent);

        }
    }

}
