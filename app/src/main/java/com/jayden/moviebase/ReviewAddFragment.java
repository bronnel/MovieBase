package com.jayden.moviebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jayden on 7/06/2016.
 */
public class ReviewAddFragment extends Fragment implements SearchSetHolder {

    private Intent intent;
    public static MovieTitle movieToPost;
    public static EditText reviewText;
    public static RatingBar reviewScore;
    public static boolean ready = false;

    public ReviewAddFragment() {
        //Empty Constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_review_add, container, false);

        intent = getActivity().getIntent();

        //get review and score on fragment
        reviewText = (EditText) fragmentLayout.findViewById(R.id.reviewText);
        reviewScore = (RatingBar) fragmentLayout.findViewById(R.id.ratingBar);

        //get information to store to later store to database about the title
        getImdbResult(intent.getExtras().getString(MainActivity.MOVIE_IMDB_EXTRA));

        //get frament views items ready to set
        TextView title = (TextView) fragmentLayout.findViewById(R.id.movieTitle);

        //set review view display template
        title.setText(intent.getExtras().getString(MainActivity.MOVIE_TITLE_EXTRA));

        return fragmentLayout;
    }

    private void getImdbResult(String imdbID) {
        //perform search query, if details have been received
        OMDBTasker tasker = new OMDBTasker(this, "IMDB");
        tasker.execute("http://www.omdbapi.com/?i=" + imdbID + "&plot=short&r=json");
    }

    @Override
    public void getSearchFinished(ArrayList<MovieTitle> movieDetails) {

        //set the MovieTitle object to the the public object to then later save to the database
        if (movieDetails != null) {
            movieToPost = movieDetails.get(0);
            //be able to post review
            ready = true;
        } else {
            Toast.makeText(getActivity(), "Failed to get movie details", Toast.LENGTH_SHORT).show();
            ready = false;
        }
    }
}
