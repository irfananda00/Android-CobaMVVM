package project.irfananda.cobamvvm.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import project.irfananda.cobamvvm.R;

import project.irfananda.cobamvvm.databinding.ItemMovieBinding;
import project.irfananda.cobamvvm.model.Film;
import project.irfananda.cobamvvm.viewModel.MovieViewModel;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.BindingHolder>  {

    private List<Film> filmList;
    private Context context;

    public GridAdapter(Context context, List<Film> filmList) {
        this.context = context;
        this.filmList = filmList;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding movieBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_movie,
                parent,
                false
        );
        return new BindingHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemMovieBinding itemMovieBinding = holder.binding;
        itemMovieBinding.setViewModel(new MovieViewModel(context,filmList.get(position)));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public void setItems(List<Film> filmList){
        this.filmList = filmList;
        notifyDataSetChanged();
    }

    public void addItems(Film film){
        if (!filmList.contains(film)) {
            filmList.add(film);
            notifyItemInserted(filmList.size() - 1);
        } else {
            filmList.set(filmList.indexOf(film), film);
            notifyItemChanged(filmList.indexOf(film));
        }
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemMovieBinding binding;

        public BindingHolder(ItemMovieBinding binding) {
            super(binding.cardView);
            this.binding = binding;
        }
    }
}
