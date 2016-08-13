package project.irfananda.cobamvvm.view.fragment;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import project.irfananda.cobamvvm.R;
import project.irfananda.cobamvvm.databinding.FragmentFilmBinding;
import project.irfananda.cobamvvm.model.ApiResponse;
import project.irfananda.cobamvvm.model.Film;
import project.irfananda.cobamvvm.network.DataService;
import project.irfananda.cobamvvm.view.adapter.GridAdapter;
import project.irfananda.cobamvvm.viewModel.FilmFragmentViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class FilmFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FilmFragmentViewModel mViewModel;
    private FragmentFilmBinding mBinding;
    private List<Film> mData;
    private ApiResponse apiResponse;
    private DataService mService;
    private Call<ApiResponse> mCall;
    private GridAdapter mGridAdapter;
    private int pageAPI;

    public FilmFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = new ArrayList<>();
        mService = new DataService();
        mGridAdapter = new GridAdapter(getActivity(),mData);
        mViewModel = new FilmFragmentViewModel();
        pageAPI = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_film, container, false);
        mBinding.setViewModel(mViewModel);
        View fragmentView = mBinding.getRoot();
        mBinding.swipeContainer.setOnRefreshListener(this);
        mBinding.swipeContainer.setColorSchemeResources(R.color.hn_orange);
        setupToolbar();
        setupRecyclerView();
        loadData();
        return fragmentView;
    }

    @Override
    public void onRefresh() {
        pageAPI = 1;
        loadData();
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void setupRecyclerView() {
        mBinding.recyclerFilm.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mBinding.recyclerFilm.setHasFixedSize(true);
        mGridAdapter.setItems(mData);
        mBinding.recyclerFilm.setAdapter(mGridAdapter);
    }

    private void loadData() {

        DataService.MovieDBApi movieDBApi = mService.serviceMovieDBApi();
        mCall = movieDBApi.getMovie(
                "popular", DataService.API_KEY, pageAPI
        );

        mCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    apiResponse = response.body();
                    for (int i = 0; i < apiResponse.getResults().size(); i++) {
                        Film film = apiResponse.getResults().get(i);
                        mData.add(film);
                    }
                    Log.i("infoirfan","success response");
                    hideLoadingViews();
                    mGridAdapter.setItems(mData);
                } else {
                    Log.i("infoirfan","Fail response");
                    hideLoadingViews();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("infoirfan", "Message : "+t.getMessage());
                hideLoadingViews();
            }
        });
    }

    private void hideLoadingViews() {
        mBinding.progressIndicator.setVisibility(View.GONE);
        mBinding.swipeContainer.setRefreshing(false);
    }
}
