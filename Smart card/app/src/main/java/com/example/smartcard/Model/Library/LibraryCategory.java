
package com.example.smartcard.Model.Library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibraryCategory {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Library_Items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LibraryCategory() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public LibraryCategory(String message, List<Library_Items> data) {
        super();
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Library_Items> getData() {
        return data;
    }

    public void setData(List<Library_Items> data) {
        this.data = data;
    }

}
