
package com.example.smartcard.Model.Library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Library_Items {

    @SerializedName("book_cat_id")
    @Expose
    private String bookCatId;
    @SerializedName("book_cat_name")
    @Expose
    private String bookCatName;
    @SerializedName("book_cat_image")
    @Expose
    private String bookCatImage;
    @SerializedName("book_cat_desc")
    @Expose
    private String bookCatDesc;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Library_Items() {
    }

    /**
     * 
     * @param bookCatId
     * @param bookCatName
     * @param bookCatDesc
     * @param bookCatImage
     */
    public Library_Items(String bookCatId, String bookCatName, String bookCatImage, String bookCatDesc) {
        super();
        this.bookCatId = bookCatId;
        this.bookCatName = bookCatName;
        this.bookCatImage = bookCatImage;
        this.bookCatDesc = bookCatDesc;
    }

    public String getBookCatId() {
        return bookCatId;
    }

    public void setBookCatId(String bookCatId) {
        this.bookCatId = bookCatId;
    }

    public String getBookCatName() {
        return bookCatName;
    }

    public void setBookCatName(String bookCatName) {
        this.bookCatName = bookCatName;
    }

    public String getBookCatImage() {
        return bookCatImage;
    }

    public void setBookCatImage(String bookCatImage) {
        this.bookCatImage = bookCatImage;
    }

    public String getBookCatDesc() {
        return bookCatDesc;
    }

    public void setBookCatDesc(String bookCatDesc) {
        this.bookCatDesc = bookCatDesc;
    }

}
