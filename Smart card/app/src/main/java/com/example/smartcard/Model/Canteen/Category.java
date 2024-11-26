
package com.example.smartcard.Model.Canteen;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Category_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Category() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public Category(String message, List<Category_items> data) {
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

    public List<Category_items> getData() {
        return data;
    }

    public void setData(List<Category_items> data) {
        this.data = data;
    }

}
