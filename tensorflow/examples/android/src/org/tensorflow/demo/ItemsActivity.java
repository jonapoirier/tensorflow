package org.tensorflow.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.compute.ComputeQuestion;
import org.tensorflow.demo.inferenceMotor.model.Characteristic;
import org.tensorflow.demo.inferenceMotor.model.Config;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

        // We set elements
        mRecyclerView = (RecyclerView) findViewById(R.id.itemsRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // We get the results
        List<ItemClassifier> results =
                (List<ItemClassifier>) getIntent().getSerializableExtra("results");
        Map<String, ItemClassifier> mapAggregatedResults = new HashMap<>();
        List<ItemClassifier> tenFirstResults;
        List<ItemClassifier> allResults;

        // We compute the results (never null)
        for (ItemClassifier result: results) {

            // We consider 20 pictures
            if(mapAggregatedResults.get(result.getId()) != null) {

                ItemClassifier classifier = mapAggregatedResults.get(result.getId());
                classifier.setConfidence(classifier.getConfidence() + (result.getConfidence() / 20));

            } else {
                ItemClassifier classifier = new ItemClassifier(
                        result.getId(),
                        result.getTitle(),
                        Float.valueOf(0),
                        null);

                classifier.setConfidence(result.getConfidence() / 20);

                mapAggregatedResults.put(result.getId(), classifier);
            }
        }
        nbItemsFound = mapAggregatedResults.size();

        // Essai avec moteur d'inférence
        Gson gsonMaterials = new Gson();
        List<Material> allMaterials = null;
        ArrayList<String> listID = new ArrayList<String>();
        Question question = null;
        Config config = null;
        List<Characteristic> characteristics = null;

        if(nbItemsFound > 1) {

            try {

                InputStream insMat = getResources().openRawResource(R.raw.materials);
                InputStreamReader isrMat = new InputStreamReader(insMat);
                allMaterials = gsonMaterials.fromJson(isrMat, new TypeToken<List<Material>>() {}.getType());



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

                question = ComputeQuestion.getInterestingQuestion(listID, true, allMaterials, config, characteristics);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //tenFirstResults = new ArrayList<>(mapAggregatedResults.values());

        // Data Json to tenFirstResults
        tenFirstResults = new ArrayList<>();
        int i = 0;
        for(Material material: allMaterials) {
            ItemClassifier item = new ItemClassifier(
                    material.getId(),
                    material.getTitle(),
                    50.0f,
                    material.getCharacteristics()
            );

            if(i < 10) {
                tenFirstResults.add(item);
            }
        }

        // We set the cards lists, order by confidence, 10 items max
        Collections.sort(tenFirstResults, new Comparator<ItemClassifier>() {
            @Override
            public int compare(ItemClassifier o1, ItemClassifier o2) {
                return o2.getConfidence().compareTo(o1.getConfidence());
            }
        });

        /*if(tenFirstResults.size() > 10) {
            tenFirstResults = tenFirstResults.subList(0, 9);
        }*/


        // We display the list of items
        // specify an adapter (see also next example)
        mAdapter = new ItemAdapter(tenFirstResults, mRecyclerView, allMaterials, question, config, characteristics);
        mRecyclerView.setAdapter(mAdapter);

    }


}
