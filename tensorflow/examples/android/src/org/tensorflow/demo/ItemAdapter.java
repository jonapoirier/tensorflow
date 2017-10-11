package org.tensorflow.demo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.tensorflow.demo.env.Logger;
import org.tensorflow.demo.inferenceMotor.compute.ComputeList;
import org.tensorflow.demo.inferenceMotor.compute.ComputeQuestion;
import org.tensorflow.demo.inferenceMotor.model.Characteristic;
import org.tensorflow.demo.inferenceMotor.model.Config;
import org.tensorflow.demo.inferenceMotor.model.Material;
import org.tensorflow.demo.inferenceMotor.model.MaterialCharacteristic;
import org.tensorflow.demo.inferenceMotor.model.Question;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jonathan.poirier on 29/08/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private static final Logger LOGGER = new Logger();
    private List<ItemClassifier> allResults;
    private List<ItemClassifier> mDataSet;
    private RecyclerView recyclerView;
    private Question question;
    Config config;
    List<Characteristic> characteristics;
    List<Material> allMaterials;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewItemsFound;
        private final TextView textViewItemTitle;
        private final TextView textComputeQuestionView;
        private final TextView textCardQuestionView;

        private final TextView textViewItemColor;
        //private final TextView textViewItemWeight;
        private final TextView textViewItemHeight;
        private final TextView textViewItemMaterial;

        private final TextView textViewRespReparation;

        private final ImageView imageViewItem;
        private final CardView cardItemView;
        private final CardView cardQuestionView;
        private final DonutProgress donutProgress;

        private final Context context;

        private LinearLayout cardQuestionLinearLayoutH;

        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);

            textViewItemsFound = (TextView) v.findViewById(R.id.textViewItemsFound);
            textViewItemTitle = (TextView) v.findViewById(R.id.textViewItemTitle);
            textComputeQuestionView = (TextView) v.findViewById(R.id.textComputeQuestionView);
            textCardQuestionView = (TextView) v.findViewById(R.id.textCardQuestionView);
            textViewItemColor = (TextView) v.findViewById(R.id.textViewItemColor);
            //textViewItemWeight = (TextView) v.findViewById(R.id.textViewItemWeight);
            textViewItemHeight = (TextView) v.findViewById(R.id.textViewItemHeight);
            textViewItemMaterial = (TextView) v.findViewById(R.id.textViewItemMaterial);
            textViewRespReparation = (TextView) v.findViewById(R.id.textViewRespReparation);
            imageViewItem = (ImageView) v.findViewById(R.id.imageViewItem);
            cardItemView = (CardView) v.findViewById(R.id.cardItemView);
            cardQuestionView = (CardView) v.findViewById(R.id.cardQuestionView);
            donutProgress = (DonutProgress) v.findViewById(R.id.donutProgress);
            cardQuestionLinearLayoutH = (LinearLayout) v.findViewById(R.id.cardQuestionLinearLayoutH);

            context = v.getContext();

        }

        public TextView getTextViewItemsFound() { return textViewItemsFound; }
        public TextView getTextViewItemTitle() {
            return textViewItemTitle;
        }
        public TextView getTextCardQuestionView() {
            return textCardQuestionView;
        }
        public TextView getTextComputeQuestionView() {
            return textComputeQuestionView;
        }
        public TextView getTextViewItemColor() {
            return textViewItemColor;
        }
        //public TextView getTextViewItemWeight() {return textViewItemWeight; }
        public TextView getTextViewItemHeight() {
            return textViewItemHeight;
        }
        public TextView getTextViewItemMaterial() {
            return textViewItemMaterial;
        }
        public TextView getTextViewRespReparation() {
            return textViewRespReparation;
        }
        public ImageView getImageViewItem() {
            return imageViewItem;
        }
        public CardView getCardItemView() { return cardItemView; }
        public CardView getCardQuestionView() { return cardQuestionView; }
        public DonutProgress getDonutProgress() { return donutProgress; }
        public LinearLayout getCardQuestionLinearLayoutH() { return cardQuestionLinearLayoutH; }

        public Context getContext() { return context; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemAdapter(List<ItemClassifier> myDataset, RecyclerView recyclerView, List<Material> allMaterials, Question question,
                       Config config, List<Characteristic> characteristics) {

        mDataSet = new ArrayList<>(myDataset.size());
        this.recyclerView = recyclerView;
        this.question = question;
        this.config = config;
        this.characteristics = characteristics;
        this.allMaterials = allMaterials;

        for (int i = 0; i < myDataset.size(); i++) {
            mDataSet.add(myDataset.get(i));
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        //Create viewholder for your default cell
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_cards_layout, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

        if (position != 0) {
            holder.getTextViewItemsFound().setVisibility(View.GONE);
            holder.getTextCardQuestionView().setVisibility(View.GONE);
            holder.getCardQuestionView().setVisibility(View.GONE);

        } else {

            holder.getTextViewItemsFound().setVisibility(View.VISIBLE);
            holder.getTextCardQuestionView().setVisibility(View.VISIBLE);
            holder.getCardQuestionView().setVisibility(View.VISIBLE);


            // On indique le nombre d'items trouvés
            String article_str = "article";
            String trouve_str = "trouvé";

            if(mDataSet.size() > 1) {
                article_str = "articles";
                trouve_str = "trouvés";

                holder.getTextViewItemsFound().setText(mDataSet.size() + " " + article_str + " " + trouve_str);

                holder.getTextCardQuestionView()
                        .setText("Vous pouvez répondre à la question suivante pour filtrer les résultats :");

                holder.getTextComputeQuestionView().setText(question.getQuestion());

                if(question.getProposals() != null && !question.getProposals().isEmpty()) {

                    // Closed question
                    if (question.getProposals().size() == 1) {

                        Button buttonYes = new Button(holder.getContext());
                        buttonYes.setText("oui");
                        buttonYes.setOnClickListener(new MyLovelyOnClickListener(this, recyclerView, mDataSet));
                        buttonYes.getBackground().setColorFilter(0xFF1666BF, PorterDuff.Mode.MULTIPLY);
                        buttonYes.setTextColor(Color.WHITE);

                        holder.getCardQuestionLinearLayoutH().addView(buttonYes);

                        Button buttonNo = new Button(holder.getContext());
                        buttonNo.setText("non");
                        buttonNo.setOnClickListener(new MyLovelyOnClickListener(this, recyclerView, mDataSet));
                        buttonNo.getBackground().setColorFilter(0xFF1666BF, PorterDuff.Mode.MULTIPLY);
                        buttonNo.setTextColor(Color.WHITE);

                        holder.getCardQuestionLinearLayoutH().addView(buttonNo);
                    } else {
                        // Opened question
                        for (String proposal : question.getProposals()) {
                            Button button = new Button(holder.getContext());
                            button.setText(proposal);
                            button.setSingleLine(true);
                            button.setOnClickListener(new MyLovelyOnClickListener(this, recyclerView, mDataSet));
                            button.getBackground().setColorFilter(0xFF1666BF, PorterDuff.Mode.MULTIPLY);
                            button.setTextColor(Color.WHITE);

                            holder.getCardQuestionLinearLayoutH().addView(button);
                        }
                    }
                }

                holder.getCardQuestionLinearLayoutH().invalidate();
            } else if(mDataSet.size() == 1) {

                holder.getTextViewItemsFound().setText(mDataSet.size() + " " + article_str + " " + trouve_str);

                holder.getTextCardQuestionView().setVisibility(View.GONE);
                holder.getCardQuestionView().setVisibility(View.GONE);

            }
        }

        // Title
        String title = mDataSet.get(position).getMaterial().getCode()
                + " - " + mDataSet.get(position).getTitle();
        holder.getTextViewItemTitle().setText(title);

        // Image
        //holder.getImageViewItem().setImageResource(R.drawable.vis);

        String mDrawableName = "p" + mDataSet.get(position).getId();
        LOGGER.i("mDrawableName : " + mDrawableName);
        int resID = holder.getContext().getResources().getIdentifier(mDrawableName , "drawable", holder.getContext().getPackageName());
        holder.getImageViewItem().setImageResource(resID);

        float number = mDataSet.get(position).getConfidence() * 100.0f;
        number= (short)(100*number);
        number=(float)(number/100);
        holder.getDonutProgress().setProgress(number);

        // Characteristics
        LOGGER.i("Characteristics !");
        for (MaterialCharacteristic materialCharacteristic: mDataSet.get(position).getCharacteristics()) {

            LOGGER.i("getName : " + materialCharacteristic.getName());
            if("color".equals(materialCharacteristic.getName())) {
                holder.getTextViewItemColor().setText((String) materialCharacteristic.getValue());
            }

            if("height".equals(materialCharacteristic.getName())) {
                holder.getTextViewItemHeight().setText(String.valueOf((Double) materialCharacteristic.getValue()) + " mm");
            }

            if("material".equals(materialCharacteristic.getName())) {
                holder.getTextViewItemMaterial().setText((String) materialCharacteristic.getValue());
            }

        }

        // Onclick image
        holder.getCardItemView().setClickable(true);
        holder.getCardItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cardView = (CardView) v;
                AlertDialog.Builder imageDialog = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.custom_fullimage_dialog,
                        (ViewGroup) v.findViewById(R.id.layout_root));
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage);
                //image.setImageDrawable(holder.getImageViewItem().getDrawable());

                String mDrawableName = "p" + mDataSet.get(position).getId();
                int resID = holder.getContext().getResources().getIdentifier(mDrawableName , "drawable", holder.getContext().getPackageName());
                image.setImageResource(resID);

                imageDialog.setView(layout);
                imageDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

                TextView textViewTitle = (TextView) layout.findViewById(R.id.titleDialogView);
                TextView descriptionViewTitle = (TextView) layout.findViewById(R.id.descriptionDialogView);

                String title = mDataSet.get(position).getMaterial().getCode()
                        + " - " + mDataSet.get(position).getTitle();
                textViewTitle.setText(title);
                descriptionViewTitle.setText(mDataSet.get(position).getMaterial().getDescription());

                TextView textViewRespReparation = (TextView) layout.findViewById(R.id.textViewRespReparation);
                textViewRespReparation.setText(mDataSet.get(position).getMaterial().getResponsableReparation());

                TextView textViewOperateur = (TextView) layout.findViewById(R.id.textViewOperateur);
                textViewOperateur.setText(mDataSet.get(position).getMaterial().getOperateur());

                LOGGER.i(mDataSet.get(position).getMaterial().toString());

                imageDialog.create();
                imageDialog.show();

            }
        });
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if(mDataSet.size() > 10) {
            return 10;
        } else {
            return mDataSet.size();
        }
    }


    public class MyLovelyOnClickListener implements View.OnClickListener
    {

        ItemAdapter itemAdapter;
        RecyclerView recyclerView;
        List<ItemClassifier> mDataSet;

        public MyLovelyOnClickListener(ItemAdapter itemAdapter, RecyclerView recyclerView, List<ItemClassifier> mDataSet) {
            this.itemAdapter = itemAdapter;
            this.recyclerView = recyclerView;
            this.mDataSet = mDataSet;
        }

        @Override
        public void onClick(View v)
        {
            LOGGER.i("réponse : " + String.valueOf(((Button) v).getText()));

            String buttonTextClicked = String.valueOf(((Button) v).getText());

            if(buttonTextClicked.equals("oui")) {
                question.setResult("true");
            } else if (buttonTextClicked.equals("non")) {
                question.setResult("false");
            } else {
                question.setResult(String.valueOf(((Button) v).getText()));
            }

            // We ask for the next question
            List<String> listID = ComputeList.updateList(question);

            LOGGER.i("taille mDataset : " + mDataSet.size());
            LOGGER.i("listID : " + listID);


            Iterator<ItemClassifier> itemClassifierIterator = mDataSet.iterator();
            while(itemClassifierIterator.hasNext()) {
                ItemClassifier itemClassifier = itemClassifierIterator.next();
                boolean trouve = false;

                for (String id: listID) {
                    if(itemClassifier.getId().equals(id)) {
                        trouve = true;
                        break;
                    }
                }

                if(!trouve) {
                    itemClassifierIterator.remove();
                }
            }

            LOGGER.i("taille mDataset : " + mDataSet.size());

            try {
                if(mDataSet.size() > 1) {
                    question = ComputeQuestion.getInterestingQuestion(listID, false, allMaterials, config, characteristics);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            recyclerView.setAdapter(new ItemAdapter(mDataSet, recyclerView, allMaterials, question, config, characteristics));
            recyclerView.invalidate();

        }
    };
}
