
package com.example.smartcard.Model.Library;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryLibrary {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<History_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public HistoryLibrary() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public HistoryLibrary(String message, List<History_items> data) {
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

    public List<History_items> getData() {
        return data;
    }

    public void setData(List<History_items> data) {
        this.data = data;
    }

}
