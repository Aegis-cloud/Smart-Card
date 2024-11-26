
package com.example.smartcard.Model.Parent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deposit_items {

    @SerializedName("h_id")
    @Expose
    private String hId;
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Deposit_items() {
    }

    /**
     * 
     * @param date
     * @param hId
     * @param amount
     * @param cardId
     */
    public Deposit_items(String hId, String cardId, String amount, String date) {
        super();
        this.hId = hId;
        this.cardId = cardId;
        this.amount = amount;
        this.date = date;
    }

    public String getHId() {
        return hId;
    }

    public void setHId(String hId) {
        this.hId = hId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
