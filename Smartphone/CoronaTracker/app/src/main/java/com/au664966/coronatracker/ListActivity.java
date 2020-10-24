package com.au664966.coronatracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.au664966.coronatracker.adapter.CountriesAdapter;
import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.model.Repository;
import com.au664966.coronatracker.utility.Constants;
import com.au664966.coronatracker.utility.ErrorCodeToResourceId;
import com.au664966.coronatracker.utility.ErrorCodes;
import com.au664966.coronatracker.viewmodel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ListActivity extends AppCompatActivity implements CountriesAdapter.ICountryClickListener {
    private RecyclerView rv;
    private Button exitBtn;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    private CountriesAdapter adapter;

    private ListViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setTitle(R.string.title_main);

        exitBtn = findViewById(R.id.btn_exit);
        fab = findViewById(R.id.fab_add);
        coordinatorLayout = findViewById(R.id.coordinator);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCountryFinder();
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

    private void showCountryFinder() {
        //https://stackoverflow.com/questions/10903754/input-text-dialog-android
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_add_country);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.partial_single_input,null);
// Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                vm.addCountry(input.getText().toString(), new Repository.LoadingStatusCallback() {
                    @Override
                    public void loading() {

                    }

                    @Override
                    public void success() {
                        Snackbar.make(coordinatorLayout, "Country added successfully", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error(ErrorCodes code) {
                        Snackbar.make(coordinatorLayout, ErrorCodeToResourceId.convert(code), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onCountryClick(int index) {
        Intent nav = new Intent(this, DetailsActivity.class);
        nav.putExtra(Constants.EXTRA_COUNTRY_CODE, vm.getCountries().getValue().get(index).getCode());

        startActivity(nav);
    }
}