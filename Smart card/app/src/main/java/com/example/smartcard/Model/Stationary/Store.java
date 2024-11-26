
package com.example.smartcard.Model.Stationary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Store_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Store() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public Store(String message, List<Store_items> data) {
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

    public List<Store_items> getData() {
        return data;
    }

    public void setData(List<Store_items> data) {
        this.data = data;
    }

}
