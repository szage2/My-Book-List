package com.example.gerda.mybooklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    // This will get the value of the google books api + the searching query
    private String mNewQueryUrl = "";

    // URL for the books to be searched (google books api)
    private String mQueryUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    // Adapter for the list of books
    private BookAdapter mAdapter;

    // Constant value for the book loader ID.
    private static final int BOOK_LOADER_ID = 1;

    // TextView that is displayed when the the list is empty
    private TextView myEmptyStateTextView;

    // Query for the book to be searched
    private String mSearchQuery ="";

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, mNewQueryUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // Clear the adapter of previous book data
        mAdapter.clear();

        // Hide the loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No books found"
        myEmptyStateTextView.setText(R.string.no_books);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        // Find reference to the TextView in the layout
        myEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(myEmptyStateTextView);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // So the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Get reference to the EditText
        final EditText searchBarText = (EditText) findViewById(R.id.search_bar);

        // Get reference to the Button
        View searchButton = findViewById(R.id.search_button);

        // EditText becomes a query which will be added to the QueryUrl
        // when the user clicks the button
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Converting the text from EditText into a String excluded with spaces and "+"
                mSearchQuery = searchBarText.getText().toString().replaceAll(" ", "+");
                // If the EditText was empty (user did not enter anything) a message pops up
                if (mSearchQuery.isEmpty()) {
                    Toast.makeText(BookActivity.this, "Enter valid query", Toast.LENGTH_SHORT).show();
                }
                // Create a new variable to unify the GoogleBooks link with the search query
                mNewQueryUrl = mQueryUrl + mSearchQuery + "&maxResults=20";
                // Restart LoaderManager
                getLoaderManager().restartLoader(1, null, BookActivity.this);
                // Get reference to the Progress bar
                View progressBar = (View) findViewById(R.id.loading_indicator);
                // Make the Progress bar visible
                progressBar.setVisibility(View.VISIBLE);
                // Set back the variable to be ready for new searches
                mNewQueryUrl = "";
            }
        });

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current book that was clicked on
                Book currentBook = mAdapter.getItem(position);
                // Convert the String URL into a Uri object (to pass into the intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());
                // Create a new intent to launch a new activity
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr =(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get the reference to the LoadManager, in order to interact with orders
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader, Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the Loader  Callbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface.
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {
            // Otherwise, display error.
            //First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            myEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
}
