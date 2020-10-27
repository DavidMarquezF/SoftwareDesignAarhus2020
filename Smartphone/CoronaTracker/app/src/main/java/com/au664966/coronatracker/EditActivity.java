package com.au664966.coronatracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.utility.Constants;
import com.au664966.coronatracker.utility.CountryCodeToUrl;
import com.au664966.coronatracker.viewmodel.EditViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;

public class EditActivity extends AppCompatActivity {
    private ImageView flagImg;
    private TextView nameTxt, ratingTxt;
    private EditText notesEdt;
    private Slider ratingSlider;

    private EditViewModel vm;

    private Button cancelBtn, saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().setTitle(R.string.title_edit);

        nameTxt = findViewById(R.id.txt_country_name);
        ratingTxt = findViewById(R.id.txt_rating);
        flagImg = findViewById(R.id.img_flag);

        cancelBtn = findViewById(R.id.btn_back);
        saveBtn = findViewById(R.id.btn_exit);
        ratingSlider = findViewById(R.id.seek_rate);
        notesEdt = findViewById(R.id.edt_user_notes);

        ratingSlider.setValueTo(10);
        ratingSlider.setStepSize(0.5f);

        ratingSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                ratingTxt.setText(String.valueOf(value));
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        vm = new ViewModelProvider(this).get(EditViewModel.class);
        vm.getCountry().observe(this, new Observer<Country>() {
            @Override
            public void onChanged(Country country) {
                updateUI(country);
            }
        });

    }

    private void saveData() {
        vm.saveCountry(notesEdt.getText().toString(), ratingSlider.getValue());
        finish();
    }

    private void updateUI(Country country) {
        nameTxt.setText(country.getName());
        ratingSlider.setValue(country.getRating() == null ? 5 : country.getRating());
        notesEdt.setText(country.getNotes());
        Glide.with(this).load(CountryCodeToUrl.convert(country.getCode())).apply(Constants.getFlagDefualtOptions()).into(flagImg);
    }

}