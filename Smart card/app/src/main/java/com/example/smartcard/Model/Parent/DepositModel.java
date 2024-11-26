
package com.example.smartcard.Model.Parent;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepositModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Deposit_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DepositModel() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public DepositModel(String message, List<Deposit_items> data) {
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

    public List<Deposit_items> getData() {
        return data;
    }

    public void setData(List<Deposit_items> data) {
        this.data = data;
    }

}
