
package com.example.smartcard.Model.Library;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History_items {
    @SerializedName("lib_id")
    @Expose
    private String libId;
    @SerializedName("book_id")
    @Expose
    private String bookId;

    @SerializedName("book_name")
    @Expose
    private String bookName;
    @SerializedName("book_image")
    @Expose
    private String bookImage;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_last")
    @Expose
    private String dateLast;
    @SerializedName("status")
    @Expose
    private String status;

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    @SerializedName("fine")
    @Expose
    private String fine;

    /**
     * No args constructor for use in serialization
     * 
     */
    public History_items() {
    }

    /**
     * 
     * @param date
     * @param dateLast
     * @param bookImage
     * @param fine
     * @param bookName
     * @param bookId
     * @param status
     */
    public History_items(String libId,String bookId, String bookName, String bookImage, String date, String dateLast, String status, String fine) {
        super();
        this.bookId = bookId;
        this.libId = libId;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.date = date;
        this.dateLast = dateLast;
        this.status = status;
        this.fine = fine;
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

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateLast() {
        return dateLast;
    }

    public void setDateLast(String dateLast) {
        this.dateLast = dateLast;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

}
