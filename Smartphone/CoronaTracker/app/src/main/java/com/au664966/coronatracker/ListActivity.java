package com.au664966.coronatracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.au664966.coronatracker.adapter.CountriesAdapter;
import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.utility.Constants;
import com.au664966.coronatracker.viewmodel.ListViewModel;

import java.util.List;

public class ListActivity extends AppCompatActivity implements CountriesAdapter.ICountryClickListener {
    private RecyclerView rv;
    private Button exitBtn;

    private CountriesAdapter adapter;

    private ListViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setTitle(R.string.title_main);

        exitBtn = findViewById(R.id.btn_exit);
        adapter = new CountriesAdapter(this);
        rv = findViewById(R.id.list_country);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rv.setAdapter(adapter);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vm = new ViewModelProvider(this).get(ListViewModel.class);
        vm.getCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                adapter.updateCountriesList(countries);
            }
        });
    }

    @Override
    public void onCountryClick(int index) {
        Intent nav = new Intent(this, DetailsActivity.class);
        nav.putExtra(Constants.EXTRA_COUNTRY_CODE,vm.getCountries().getValue().get(index).getCode());

        startActivity(nav);
    }
}