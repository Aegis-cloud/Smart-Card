
package com.example.smartcard.Model.Stationary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Product_item> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Products() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public Products(String message, List<Product_item> data) {
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

    public List<Product_item> getData() {
        return data;
    }

    public void setData(List<Product_item> data) {
        this.data = data;
    }

}
