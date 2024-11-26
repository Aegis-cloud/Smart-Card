
package com.example.smartcard.Model.Canteen;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<SubCategory> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Food() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public Food(String message, List<SubCategory> data) {
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

    public List<SubCategory> getData() {
        return data;
    }

    public void setData(List<SubCategory> data) {
        this.data = data;
    }

}
