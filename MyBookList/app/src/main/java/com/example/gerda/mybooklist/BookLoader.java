package com.example.gerda.mybooklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of Books by using AsyncTask to perform the
 * network request to the given URL
 */

public class BookLoader extends AsyncTaskLoader<List<Book>>{

    private static final String LOG_TAG = BookLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /** Constructs a new {@link BookLoader}
     *
     * @param context of the activity
     * @param url to load data form
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform on the network request, parse the response, and extract a list of books.
        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
