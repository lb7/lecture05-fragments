package edu.uw.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements
        MoviesFragment.OnMovieSelectedListener, SearchFragment.OnSearchListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    //respond to search button clicking
    public void handleSearchClick(View v){
        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        //add a new results fragment to the page
        MoviesFragment fragment = MoviesFragment.newInstance(searchTerm);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, "MoviesFragment");
        ft.addToBackStack(null); //remember for the back button
        ft.commit();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        DetailFragment detailFragment = DetailFragment.newInstance(movie.toString(), movie.imdbId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSearchSubmitted(String searchTerm) {

    }

    class MoviePagerAdapter extends FragmentStatePagerAdapter {

        SearchFragment searchFragment;
        MoviesFragment moviesFragment;
        DetailFragment detailFragment;

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);

            searchFragment = SearchFragment.newInstance();
            moviesFragment = new MoviesFragment();
            detailFragment = new DetailFragment();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return searchFragment;
                case 1:
                    return moviesFragment;
                case 2:
                    return detailFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
