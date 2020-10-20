package com.au664966.coronatracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.au664966.coronatracker.R;
import com.au664966.coronatracker.model.Country;
import com.au664966.coronatracker.utility.CountryCodeToResourceId;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {

    private ICountryClickListener listener;
    private List<Country> countries;

    private Context context;

    public CountriesAdapter(ICountryClickListener listener) {
        this.listener = listener;
    }

    public void updateCountriesList(List<Country> list) {
        countries = list;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // We obtain the context here to use it in the onBindViewHolder
        // Even though it's possible to get it from the item views in the onBindViewHolder function
        // This solution seems to be better
        // Source: https://stackoverflow.com/a/48660869
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_summary, parent, false);
        CountriesViewHolder vh = new CountriesViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.name.setText(country.getName());
        holder.description.setText(country.getCases()+" / "+country.getDeaths());
        holder.rating.setText(country.getRating() == null ? "-" : country.getRating().toString());
        holder.flag.setImageResource(CountryCodeToResourceId.convert(context, country.getCode()));
    }


    @Override
    public int getItemCount() {
        return countries.size();
    }

    public interface ICountryClickListener {
        void onCountryClick(int index);
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView flag;
        TextView name;
        TextView description;
        TextView rating;

        ICountryClickListener listener;

        public CountriesViewHolder(@NonNull View itemView, ICountryClickListener listener){
            super(itemView);
            this.listener = listener;
            flag = itemView.findViewById(R.id.img_flag);
            name = itemView.findViewById(R.id.txt_title);
            description = itemView.findViewById(R.id.txt_description);
            rating = itemView.findViewById(R.id.txt_rating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onCountryClick(getAdapterPosition());
        }
    }
}
