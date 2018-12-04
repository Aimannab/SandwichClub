package com.udacity.sandwichclub;

import android.animation.StateListAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.image_iv)
    ImageView ingredientsImageView;

    @BindView(R.id.origin_tv)
    TextView originTextView;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTextView;

    @BindView(R.id.description_tv)
    TextView descriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Setting text for alsoKnownAs
        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() >0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                stringBuilder.append(", ");
                stringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            alsoKnownAsTextView.setText(stringBuilder.toString());
        } else {
            //alsoKnownAsTextView.setVisibility(View.GONE);
            alsoKnownAsTextView.setText("Unknown right now.");

        }

        //Setting text for placeOfOrigin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            //originTextView.setVisibility(View.GONE);
            //originTextView.setVisibility(View.GONE);
            originTextView.setText("Unknown right now.");
        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }

        //Setting text for description
        descriptionTextView.setText(sandwich.getDescription());

        //Setting text for ingredients
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u2022");
            stringBuilder.append(sandwich.getIngredients().get(0));

            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                stringBuilder.append("\n");
                stringBuilder.append("\u2022");
                stringBuilder.append(sandwich.getIngredients().get(i));
            }
            ingredientsTextView.setText(stringBuilder.toString());
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        setTitle(sandwich.getMainName());
        originTextView.setText(sandwich.getPlaceOfOrigin());
        //alsoKnownAsTextView.setText(sandwich.getAlsoKnownAs());
        descriptionTextView.setText(sandwich.getDescription());

    }
}
