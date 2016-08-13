package project.irfananda.cobamvvm.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import project.irfananda.cobamvvm.R;
import project.irfananda.cobamvvm.model.Film;
import project.irfananda.cobamvvm.network.DataService;

/**
 * Created by irfananda on 09/05/16.
 */
public class MovieViewModel extends BaseObservable {

    private Context context;
    private Film film;

    public MovieViewModel(Context context, Film film) {
        this.context = context;
        this.film = film;
    }

    @Bindable
    public String getReleaseDate(){
        return film.getRelease_date();
    }

    @Bindable
    public String getFilmTitle(){
        return film.getTitle();
    }

    @Bindable
    public String getImageURL(){
        return DataService.IMG_URL+film.getPoster_path();
    }

    @BindingAdapter({"bind:imageURL"})
    public static void loadImage(ImageView view, String imageURL) {
        Picasso.with(view.getContext())
                .load(imageURL)
                .resize(300,600)
                .placeholder(R.drawable.img_fail)
                .error(R.drawable.img_fail)
                .into(view);
    }
}
