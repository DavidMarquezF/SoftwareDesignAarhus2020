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
import com.au664966.coronatracker.utility.CountryCodeToUrl;
import com.au664966.coronatracker.utility.ErrorCodeToResourceId;
import com.au664966.coronatracker.utility.ErrorCodes;
import com.au664966.coronatracker.utility.StatusCallback;
import com.au664966.coronatracker.viewmodel.DetailsViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

public class DetailsActivity extends AppCompatActivity {

    public static final int NAV_EDIT_ACT = 200;
    private ImageView flagImg;
    private TextView nameTxt, casesTxt, deathsTxt, notesTxt, ratingTxt, newCasesTxt, newDeathsTxt;
    private Button editBtn, deleteBtn;

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
        deleteBtn = findViewById(R.id.btn_delete);
        editBtn = findViewById(R.id.btn_exit);
        newCasesTxt = findViewById(R.id.txt_new_cases);
        newDeathsTxt = findViewById(R.id.txt_new_deaths);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditActivity();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.deleteCountry(new StatusCallback() {
                    @Override
                    public void success() {
                        finish();
                    }

                    @Override
                    public void error(ErrorCodes code) {
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                ErrorCodeToResourceId.convert(code),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
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
        startActivityForResult(nav, NAV_EDIT_ACT);
    }

    private void updateUI(Country country) {
        if(country == null){
            finish();
            return;
        }
        nameTxt.setText(country.getName());
        casesTxt.setText("" + country.getCases());
        deathsTxt.setText("" + country.getDeaths());
        ratingTxt.setText(country.getRating() == null ? "-" : country.getRating().toString());
        newCasesTxt.setText(""+country.getNewCases());
        newDeathsTxt.setText(""+country.getNewDeaths());
        if (country.getNotes() == null  || country.getNotes().isEmpty()) {
            notesTxt.setText(R.string.label_no_notes);
        } else {
            notesTxt.setText(country.getNotes());
        }

        Glide.with(this).load(CountryCodeToUrl.convert(country.getCode()))
                .apply(Constants.getFlagDefualtOptions()).into(flagImg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == NAV_EDIT_ACT && resultCode == RESULT_OK){
            finish();
        }
        super.onActivityResult(requestCode,resultCode, data);
    }
}