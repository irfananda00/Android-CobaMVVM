package project.irfananda.cobamvvm.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import project.irfananda.cobamvvm.model.ApiResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by irfananda on 09/05/16.
 */
public class DataService {

    public static final String API_KEY = "136451254291f50e7661446b9450ede6";
    public static final String API_URL = "http://api.themoviedb.org/3/";
    public static final String IMG_URL = "http://image.tmdb.org/t/p/w500/";

    private Retrofit mRetrofit;

    public DataService() {
        Gson mGson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    public MovieDBApi serviceMovieDBApi(){
        return mRetrofit.create(MovieDBApi.class);
    }

    public interface MovieDBApi {
        @GET("movie/{sort_by}?")
        Call<ApiResponse> getMovie(
                @Path("sort_by") String sort,
                @Query("api_key") String appid,
                @Query("page") int page
        );
    }

}
