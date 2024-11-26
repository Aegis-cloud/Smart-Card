
package com.example.smartcard.Model.Parent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Withdrawal_items {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Withdrawal_items() {
    }

    /**
     * 
     * @param date
     * @param amount
     * @param name
     * @param type
     */
    public Withdrawal_items(String name, String amount, String type, String date) {
        super();
        this.name = name;
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
