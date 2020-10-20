package com.au664966.coronatracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.utility.Constants;
import com.au664966.coronatracker.utility.CountryCodeToResourceId;
import com.au664966.coronatracker.viewmodel.DetailsViewModel;

public class DetailsActivity extends AppCompatActivity {

    private ImageView flagImg;
    private TextView nameTxt, casesTxt, deathsTxt, notesTxt, ratingTxt;
    private Button editBtn;

    private DetailsViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setTitle(R.string.title_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameTxt = findViewById(R.id.txt_country_name);
        casesTxt = findViewById(R.id.txt_cases);
        deathsTxt = findViewById(R.id.txt_deaths);
        notesTxt = findViewById(R.id.txt_user_notes);
        ratingTxt = findViewById(R.id.txt_rating);
        flagImg = findViewById(R.id.img_flag);

        editBtn = findViewById(R.id.btn_exit);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditActivity();
            }
        });

        vm = new ViewModelProvider(this).get(DetailsViewModel.class);
        vm.getCountry().observe(this, new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                updateUI(country);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void showEditActivity() {
        Intent nav = new Intent(this, EditActivity.class);
        nav.putExtra(Constants.EXTRA_COUNTRY_CODE, vm.getCountry().getValue().getCode());
        startActivity(nav);
    }

    private void updateUI(Country country) {
        nameTxt.setText(country.getName());
        casesTxt.setText("" + country.getCases());
        deathsTxt.setText("" + country.getDeaths());
        ratingTxt.setText(country.getRating() == null ? "-" : country.getRating().toString());
        if (country.getNotes() == null  || country.getNotes().isEmpty()) {
            notesTxt.setText(R.string.label_no_notes);
        } else {
            notesTxt.setText(country.getNotes());
        }

        flagImg.setImageResource(CountryCodeToResourceId.convert(this, country.getCode()));
    }


}