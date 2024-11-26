
package com.example.smartcard.Model.Library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibrarySub {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<LibrarysubItem> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LibrarySub() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public LibrarySub(String message, List<LibrarysubItem> data) {
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

    public List<LibrarysubItem> getData() {
        return data;
    }

    public void setData(List<LibrarysubItem> data) {
        this.data = data;
    }

}
