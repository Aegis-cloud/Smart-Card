
package com.example.smartcard.Model.Library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibrarysubItem {

    @SerializedName("book_id")
    @Expose
    private String bookId;
    @SerializedName("book_name")
    @Expose
    private String bookName;
    @SerializedName("book_amount")
    @Expose
    private String bookAmount;
    @SerializedName("book_category")
    @Expose
    private String bookCategory;
    @SerializedName("book_quantity")
    @Expose
    private String bookQuantity;
    @SerializedName("book_image")
    @Expose
    private String bookImage;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LibrarysubItem() {
    }

    /**
     * 
     * @param bookAmount
     * @param bookQuantity
     * @param bookImage
     * @param bookName
     * @param bookCategory
     * @param bookId
     */
    public LibrarysubItem(String bookId, String bookName, String bookAmount, String bookCategory, String bookQuantity, String bookImage) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAmount = bookAmount;
        this.bookCategory = bookCategory;
        this.bookQuantity = bookQuantity;
        this.bookImage = bookImage;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(String bookAmount) {
        this.bookAmount = bookAmount;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(String bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

}
