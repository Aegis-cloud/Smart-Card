
package com.example.smartcard.Model.Office;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Office {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Office_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Office() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public Office(String message, List<Office_items> data) {
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

    public List<Office_items> getData() {
        return data;
    }

    public void setData(List<Office_items> data) {
        this.data = data;
    }

}
