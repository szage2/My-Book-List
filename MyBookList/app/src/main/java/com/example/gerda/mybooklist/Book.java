package com.example.gerda.mybooklist;

/**
 * @{link Book} represents a list of books found after a the search initiated by the user
 */

public class Book {

    /** Title of the book*/
    private String mTitle;

    /** Authors of the book*/
    private String mAuthors;

    /** Date when the book has been published*/
    private String mPublishedDate;

    /** Category of the book*/
    private String mCategories;

    /** Cover image URL of the book*/
    private String mImageURL;

    /** Description of the book*/
    private String mUrl;


    /** create a new Book object
     *
     * @param title is the title of the book
     * @param authors is the author(s) of the book
     * @param publishedDate is the date when the book has been published
     * @param categories is the category type of the book
     * @param imageURL is a url link of the book cover
     * @param url is the a url link containing information about the book
     */
    public Book(String title, String authors, String publishedDate, String categories, String imageURL, String url) {
        mTitle = title;
        mAuthors = authors;
        mPublishedDate = publishedDate;
        mCategories = categories;
        mImageURL = imageURL;
        mUrl = url;
    }

    // Return the title of the book
    public String getTitle() { return mTitle; }

    // Return the authors of the book
    public String getAuthors() { return mAuthors; }

    // Return the date when the book has been published
    public String getPublishedDate() { return  mPublishedDate; }

    // Return the categories of the book
    public String getCategories() { return mCategories; }

    // Return the image URL of the book
    public String getImageURL() { return mImageURL; }

    // Return the url of the book
    public String getUrl() { return mUrl; }

}
