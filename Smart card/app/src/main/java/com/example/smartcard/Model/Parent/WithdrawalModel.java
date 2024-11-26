
package com.example.smartcard.Model.Parent;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithdrawalModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Withdrawal_items> data = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WithdrawalModel() {
    }

    /**
     * 
     * @param data
     * @param message
     */
    public WithdrawalModel(String message, List<Withdrawal_items> data) {
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

    public List<Withdrawal_items> getData() {
        return data;
    }

    public void setData(List<Withdrawal_items> data) {
        this.data = data;
    }

}
