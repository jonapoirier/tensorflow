package org.tensorflow.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.compute.ComputeQuestion;
import org.tensorflow.demo.inferenceMotor.model.Characteristic;
import org.tensorflow.demo.inferenceMotor.model.Config;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.MaterialCharacteristic;
import org.tensorflow.demo.inferenceMotor.model.Question;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

    private static final Logger LOGGER = new Logger();
    private int nbItemsFound = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // We change the color of the action bar
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1666bf")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>OMNI</font>"));

        // We change the color of the status bar
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0e3e75"));

        // We set elements
        mRecyclerView = (RecyclerView) findViewById(R.id.itemsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // We get the results
        List<ItemClassifier> results =
                (List<ItemClassifier>) getIntent().getSerializableExtra("results");
        Map<String, ItemClassifier> mapAggregatedResults = new HashMap<>();
        List<ItemClassifier> allResults;

        // We load materials and config
        Gson gsonMaterials = new Gson();
        List<Material> allMaterials = null;
        List<String> listID = new ArrayList<String>();
        Question question = null;
        Config config = null;
        List<Characteristic> characteristics = null;

        try {

            InputStream insMat = getResources().openRawResource(R.raw.materials_02102017);
            InputStreamReader isrMat = new InputStreamReader(insMat);
            allMaterials = gsonMaterials.fromJson(isrMat, new TypeToken<List<Material>>() {
            }.getType());

            InputStream insConfig = getResources().openRawResource(R.raw.config);
            InputStreamReader isrConfig = new InputStreamReader(insConfig);
            Gson gsonConfig = new Gson();
            config = gsonConfig.fromJson(isrConfig, Config.class);

            InputStream insChar = getResources().openRawResource(R.raw.charateristics);
            InputStreamReader isrChar = new InputStreamReader(insChar);
            Gson gsonCharacteristics = new Gson();
            characteristics = gsonCharacteristics.fromJson(isrChar,
                    new TypeToken<List<Characteristic>>() {
                    }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }


            // We compute the results (never null)
        for (ItemClassifier result: results) {

            // We consider 5 pictures
            if(mapAggregatedResults.get(result.getId()) != null) {

                ItemClassifier classifier = mapAggregatedResults.get(result.getId());
                classifier.setConfidence(classifier.getConfidence() + (result.getConfidence() / 5));

            } else {

                String title = null;
                List<MaterialCharacteristic> materialCharacteristics = null;
                Material materialFound = null;

                // We find characteristics
                for(Material material: allMaterials) {
                    if(material.getId().equals(result.getId())) {
                        title = material.getTitle();
                        materialCharacteristics = material.getCharacteristics();
                        materialFound = material;
                    }
                }

                ItemClassifier classifier = new ItemClassifier(
                        result.getId(),
                        title,
                        Float.valueOf(0),
                        materialCharacteristics,
                        materialFound);

                classifier.setConfidence(result.getConfidence() / 5);

                mapAggregatedResults.put(result.getId(), classifier);
            }
        }
        nbItemsFound = mapAggregatedResults.size();
        allResults = new ArrayList<>(mapAggregatedResults.values());


        for (ItemClassifier item : allResults) {
            // We build the listID parameter
            listID.add(item.getId());
        }

        // Essai avec moteur d'inférence
        if(nbItemsFound >= 1) {
            try {
                question = ComputeQuestion.getInterestingQuestion(listID, false, allMaterials, config, characteristics);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // We set the cards lists, order by confidence, 10 items max
        Collections.sort(allResults, new Comparator<ItemClassifier>() {
            @Override
            public int compare(ItemClassifier o1, ItemClassifier o2) {
                return o2.getConfidence().compareTo(o1.getConfidence());
            }
        });


        // We display the list of items
        mAdapter = new ItemAdapter(allResults, mRecyclerView, allMaterials, question, config, characteristics);
        mRecyclerView.setAdapter(mAdapter);
    }


}
