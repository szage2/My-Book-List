package com.example.gerda.mybooklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * {@link BookAdapter} is an {@link ArrayAdapter} which is capable to provide the layout for
 * each list item according to the data source, what is the source of {@link Book} objects.
 */

public class BookAdapter extends ArrayAdapter<Book>{

    /** Create a new {@link BookAdapter} object
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param books is the list of {@link Book}s to be displayed.
     */
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        // Get the {@link Book} object located at this position in the list
        Book currentBook = getItem(position);

        // Find the Text View with the id title
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // Get the value into the string variable
        String title = new String(currentBook.getTitle());
        // Display the title of the current book in that TextView
        titleTextView.setText(title);

        // Find the Text View with the id authors
        TextView authorsTextView = (TextView) listItemView.findViewById(R.id.authors);
        // Get the value into the string variable
        String authors = new String (currentBook.getAuthors());
        // separate the authors if there are more
        String separateAuthors = authors.split(",")[0];
        // replace the unnecessary characters from the string
        String separatedAuthors = separateAuthors.replace("[", " ").replace("]", " ").replace('"', ' ');
        // Display the author(s) of the current book in that TextView
        authorsTextView.setText(separatedAuthors);

        // Find the Text View with the id published date
        TextView publishedDateTextView = (TextView) listItemView.findViewById(R.id.published_date);
        // Get the value into the string variable
        String publishedOn = new String (currentBook.getPublishedDate());
        // Display the date of the current book when is's been published in that TextView
        publishedDateTextView.setText(publishedOn);


        // Find the Text View with the id  categories
        TextView categoryTextView = (TextView) listItemView.findViewById(R.id.categories);
        // Get the value into the string variable
        String categories = new String (currentBook.getCategories());
        // separate the categories if there are more
        String separateCategories = categories.split("&")[0];
        // replace the unnecessary characters from the string
        String separatedCategories = separateCategories.replace("[", "").replace("]", "").replace('"', ' ');
        // Display the category of the current book in that TextView
        categoryTextView.setText(separatedCategories);

        // Find the Text View with the id image URL
        ImageView imageUrlView = (ImageView) listItemView.findViewById(R.id.image_url);
        // Get the value into the string variable
        String imageUrl = new String(currentBook.getImageURL());
        // load the image with Glide based on the image url
        Glide.with(getContext()).load(imageUrl).into(imageUrlView);

        return listItemView;
    }


}
